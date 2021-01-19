package com.sky.hadoop.key;

import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

public class DBPrimarykeyGeneratorCache extends AbstractGenerator {
    private String driverName ;
    private String dbUrl ;
    private String username ;
    private String password ;
    private static final String queryUpdateTimeSql = "select t.update_time from leaf_segment t where biz_tag = ? " ;
    private static final String queryUpdateTimeForUpdateSql = "select t.update_time from leaf_segment t where biz_tag = ? for update" ;
    private static final String queryIdAndStepSql = "select MAX_ID,STEP from leaf_segment t where biz_tag = ? " ;
    private static final String queryMaxIdSql = "select max(id) from " ;
    private static final String insertSql = "insert into leaf_segment values(?,?,?,?)" ;
    private static final String updateSql = "update leaf_segment SET max_id=max_id+step,update_time = ? WHERE biz_tag = ? and update_time = ? " ;
    private static final Long step = 1000L ;

    public DBPrimarykeyGeneratorCache(String driverName, String dbUrl, String username, String password) {
        this.driverName = driverName;
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public LeafSegment getIds(String dbName, String tableName, boolean must) {
        dbName = dbName.toLowerCase() ;
        tableName = tableName.toLowerCase() ;
        Connection connection = null ;
        try {
            String bizTag = formKey(dbName,tableName) ;
            Class.forName(driverName) ;
            connection = DriverManager.getConnection(dbUrl,username,password) ;
            connection.setAutoCommit(false);
            Long updateTime = getUpdateTime(connection,bizTag,must) ;
            int count = 0 ;
            LeafSegment result = null ;
            // 如果查不到则代表数据库没有
            if(updateTime == null){
                long id = getMaxId(connection,tableName) ;
                result = new LeafSegment(new AtomicLong(id + 1),new AtomicLong(id + step),step) ;
                count = inert(connection,bizTag,result.getEnd().get()) ;
            } else {
                count = udate(connection,bizTag,updateTime) ;
                if(count > 0){
                    result = getUpdateResult(connection,bizTag) ;
                }
            }
            connection.commit();
            // 如果有更新则返回
            if(count > 0){
                return result ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (Exception e1){
                e1.printStackTrace();
            }

        } finally {
            try{
                if(connection != null){
                    connection.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 获取最后更新时间
     * @param connection
     * @param bizTag
     * @return
     * @throws SQLException
     */
    private Long getUpdateTime(Connection connection,String bizTag,boolean must) throws SQLException {
        PreparedStatement ps = null ;
        ResultSet rs = null ;
        // 遍历将结果集 加到list中
        Long updateTime = null ;
        try {
            // 查询最后一次修改时间
            ps = connection.prepareStatement(must ? queryUpdateTimeForUpdateSql : queryUpdateTimeSql) ;
            ps.setString(1,bizTag);
            // 执行
            rs = ps.executeQuery();
            while (rs.next()) {
                return  rs.getLong(1) ;
            }
            return updateTime ;
        } finally {
            try{
                if(rs != null){
                    rs.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            try{
                if(ps != null){
                    ps.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取真实表最大id
     * @param connection
     * @param tableName
     * @return
     * @throws SQLException
     */
    private Long getMaxId(Connection connection,String tableName) throws SQLException {
        PreparedStatement ps = null ;
        ResultSet rs = null ;
        try {
            // 根据实际表查询最大主键
            ps = connection.prepareStatement(queryMaxIdSql + tableName);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;
        } catch (Exception e){
            e.printStackTrace();
            return 0L ;
        } finally {
            try{
                if(rs != null){
                    rs.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            try{
                if(ps != null){
                    ps.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    /**
     * 插入主键
     * @param connection
     * @param bizTag
     * @param maxId
     * @return
     * @throws SQLException
     */
    private int inert(Connection connection,String bizTag,long maxId) throws SQLException {
        PreparedStatement ps = null ;
        try{
            // 插入
            ps = connection.prepareStatement(insertSql) ;
            ps.setString(1,bizTag);
            ps.setLong(2,maxId);
            ps.setLong(3,step);
            ps.setLong(4,System.currentTimeMillis());
            return ps.executeUpdate() ;
        } finally {
            try{
                if(ps != null){
                    ps.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改主键
     * @param connection
     * @param bizTag
     * @param updateTime
     * @return
     * @throws SQLException
     */
    private int udate(Connection connection,String bizTag,Long updateTime) throws SQLException {
        PreparedStatement ps = null ;
        try{
            // 用乐观锁修改
            ps = connection.prepareStatement(updateSql) ;
            ps.setLong(1,System.currentTimeMillis());
            ps.setString(2,bizTag);
            ps.setLong(3,updateTime);
            return ps.executeUpdate() ;
        } finally {
            try{
                if(ps != null){
                    ps.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询修改结果
     * @param connection
     * @param bizTag
     * @return
     * @throws SQLException
     */
    private LeafSegment getUpdateResult(Connection connection, String bizTag) throws SQLException {
        PreparedStatement ps = null ;
        ResultSet rs = null ;
        try{
            ps = connection.prepareStatement(queryIdAndStepSql) ;
            // 查询修改结果
            ps.setString(1,bizTag);
            rs = ps.executeQuery();
            while (rs.next()) {
                long maxId = rs.getLong(1) ;
                long step = rs.getLong(2) ;
                return new LeafSegment(new AtomicLong(maxId - step + 1),new AtomicLong(maxId),step) ;
            }
            return null ;
        } finally {
            try{
                if(rs != null){
                    rs.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            try{
                if(ps != null){
                    ps.close() ;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
