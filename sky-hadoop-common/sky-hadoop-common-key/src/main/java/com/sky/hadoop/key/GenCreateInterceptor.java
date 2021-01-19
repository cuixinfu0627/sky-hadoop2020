package com.sky.hadoop.key;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class GenCreateInterceptor implements Interceptor {
    private Logger log = LoggerFactory.getLogger(GenCreateInterceptor.class);
    private static final Pattern pattern = Pattern.compile("^(\\s*insert\\s*[all]*\\s*into\\s*)[\\s\\S]*", 2);
    private Properties properties;
    public PrimarykeyGenerator primarykeyGenerator;

    public GenCreateInterceptor(PrimarykeyGenerator primarykeyGenerator) {
        this.primarykeyGenerator = primarykeyGenerator ;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        boolean isInsert = this.isInsert(boundSql);
        if (!isInsert) {
            try {
                return invocation.proceed();
            } catch (DuplicateKeyException var7) {
                var7.printStackTrace();
                throw var7;
            }
        } else {
            return this.handleCreate(invocation, boundSql, mappedStatement);
        }
    }

    public Object handleCreate(Invocation invocation, BoundSql boundSql, MappedStatement mappedStatement) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Configuration configuration = mappedStatement.getConfiguration();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (!typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                Iterator var9 = parameterMappings.iterator();

                while(true) {
                    String propertyName;
                    do {
                        do {
                            label35:
                            do {
                                while(var9.hasNext()) {
                                    ParameterMapping parameterMapping = (ParameterMapping)var9.next();
                                    propertyName = parameterMapping.getProperty();
                                    if (metaObject.hasGetter(propertyName)) {
                                        continue label35;
                                    }

                                    if (boundSql.hasAdditionalParameter(propertyName) && !configuration.isUseGeneratedKeys() && "ID".equals(propertyName.split("\\.")[1].toUpperCase())) {
                                        boundSql.setAdditionalParameter(propertyName, this.genID(this.getDbName(mappedStatement, invocation), this.getTName(boundSql)));
                                    }
                                }

                                return this.exeInsert(invocation, mappedStatement);
                            } while(configuration.isUseGeneratedKeys());
                        } while(!"ID".equals(propertyName.toUpperCase()));
                    } while(metaObject.getValue(propertyName) != null && Long.valueOf(String.valueOf(metaObject.getValue(propertyName))) > 0L);

                    metaObject.setValue(propertyName, this.genID(this.getDbName(mappedStatement, invocation), this.getTName(boundSql)));
                }
            }
        }

        return this.exeInsert(invocation, mappedStatement);
    }

    protected Connection getConnection(Transaction transaction, Log statementLog) throws SQLException {
        Connection connection = transaction.getConnection();
        return statementLog.isDebugEnabled() ? ConnectionLogger.newInstance(connection, statementLog, 1) : connection;
    }

    public String getTName(BoundSql boundSql) {
        String sql = boundSql.getSql();
        String[] sqls = sql.split("\\(")[0].replaceAll("[\\s]+", " ").split("\\s");
        return sqls[sqls.length - 1].trim();
    }

    public String getDbName(MappedStatement mappedStatement, Invocation invocation) {
        Executor exe = (Executor)invocation.getTarget();
        Connection connection = null;
        String url = "";

        try {
            connection = this.getConnection(exe.getTransaction(), mappedStatement.getStatementLog());
            url = connection.getMetaData().getURL();
        } catch (SQLException var10) {
            var10.printStackTrace();
        }

        if (url.toLowerCase().indexOf("mysql") > -1) {
            return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?") == -1 ? url.length() : url.lastIndexOf("?")).trim();
        } else if (url.toLowerCase().indexOf("oracle") > -1) {
            int atIndex = url.indexOf("@");
            int colonAfterAtIndex = url.indexOf(58, atIndex);
            int serviceNameIndex;
            if (colonAfterAtIndex > 0) {
                serviceNameIndex = url.indexOf(47, colonAfterAtIndex);
                return serviceNameIndex < 0 ? url.substring(url.lastIndexOf(":") + 1).trim() : url.substring(url.lastIndexOf("/") + 1).trim();
            } else {
                serviceNameIndex = url.toUpperCase().indexOf("SERVICE_NAME", atIndex);
                int equalIndex = url.indexOf(61, serviceNameIndex);
                return url.substring(equalIndex + 1, url.indexOf(41, equalIndex)).trim();
            }
        } else {
            return url.trim();
        }
    }

    private synchronized long genID(String dbName, String tName) {
        Long generateId = this.primarykeyGenerator.generateId(dbName, tName);
        return generateId;
    }

    public Object exeInsert(Invocation invocation, MappedStatement insert_statement) throws IllegalAccessException, InvocationTargetException {
        Object[] args = invocation.getArgs();
        return invocation.getMethod().invoke(invocation.getTarget(), insert_statement, args[1]);
    }

    public boolean isInsert(BoundSql boundSql) {
        Matcher matcher = pattern.matcher(boundSql.getSql());
        return matcher.matches();
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(2, 2, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else if (obj != null) {
            value = obj.toString();
        } else {
            value = "";
        }

        return value;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties0) {
        this.properties = properties0;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();

        for(int i = 0; i < 1000000; ++i) {
            Matcher var3 = pattern.matcher("\n\n\n\t\t\t insert\t    \n\n\n\t\t\t \tinto \n\n \t\t  aaa\n\n\n\t\t\t (\n\n\n\t\t\t 'name'\n\n\n\t\t\t )  \t \n\n\n\t\t\t \t values \t \n\n\n\t\t\t \t(\n\n\n\t\t\t 'aaa'\n\n\n\t\t\t )\t \n\n\n\t\t\t \t");
        }

        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
