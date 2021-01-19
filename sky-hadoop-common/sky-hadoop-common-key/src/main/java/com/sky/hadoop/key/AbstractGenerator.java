package com.sky.hadoop.key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

public abstract class AbstractGenerator implements PrimarykeyGenerator {
    private Logger log = LoggerFactory.getLogger(AbstractGenerator.class);
    private String prefix = "";
    private boolean upercase = false;
    private static final Map<String, LeafSegmentPair> leafSegmentMap = new ConcurrentHashMap() ;
    private static final ExecutorService executorService = new ThreadPoolExecutor(5, 10, 5000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                executorService.shutdownNow() ;
            }
        }));
    }
    public Long generateId(String dbName, String tableName) {
        String key = formKey(dbName, tableName) ;
        synchronized(key.intern()) {
            // 根据键获取值
            LeafSegmentPair leafSegmentPair = leafSegmentMap.get(key) ;
            if(leafSegmentPair == null){
                // 如果不存在则重新获取
                leafSegmentPair = getLeafSegmentPair(dbName, tableName) ;
                // 设置到缓存
                leafSegmentMap.put(key,leafSegmentPair) ;
            }
            // 获取当前缓存节点
            LeafSegment leafSegment = leafSegmentPair.getCurrent() ;

            // 如果当前缓存节点不可用
            if(leafSegment == null || !leafSegment.isAvailable()){
                // 切换并获取另一个缓存节点
                leafSegment = leafSegmentPair.changeCurrent() ;
                // 如果另一个缓存节点也不可用
                if(leafSegment == null || !leafSegment.isAvailable()){
//                    log.info("缓存1和2都不可用 2{}",leafSegment);
                    // 重新获取其中一个缓存
                    leafSegment = getLeafSegment(dbName, tableName) ;
                    // 设置当前缓存
                    leafSegmentPair.setCurrent(leafSegment);
                }
            }

            // 判断缓存是否消耗了10%
            if(leafSegmentPair.isPercent()){
                executorService.submit(new LoadOtherTask(leafSegmentPair,this,dbName,tableName)) ;
            }
            long id = leafSegment.getCurrent().getAndIncrement() ;
            return id ;
        }
    }

    /**
     * 获取双缓存
     * @param dbName
     * @param tableName
     * @return
     */
    private LeafSegmentPair getLeafSegmentPair(String dbName, String tableName){
        LeafSegmentPair leafSegmentPair = new LeafSegmentPair() ;
        leafSegmentPair.setCurrent(getLeafSegment(dbName,tableName));
        return leafSegmentPair ;
    }

    /**
     * 获取单缓存
     * @param dbName
     * @param tableName
     * @return
     */
    public LeafSegment getLeafSegment(String dbName, String tableName){
        int getIdsCount = 0 ;
        LeafSegment leafSegment = null ;
        do{
            getIdsCount++ ;
            leafSegment = getIds(dbName,tableName,false) ;
            if(leafSegment == null){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (leafSegment == null && getIdsCount < 5) ;
        if(leafSegment == null){
            log.info("循环次数{} leafSegment: {}",getIdsCount,leafSegment);
            leafSegment = getIds(dbName,tableName,true) ;
        }
        // 5次获取失败用行级锁强行获取
        return leafSegment ;
    }

    public abstract LeafSegment getIds(String dbName, String tableName, boolean must) ;

    public String formKey(String dbName, String tableName) {
        String key = this.getPrefix() + "_".concat(dbName).concat("_db_").concat(tableName);
        return isUpercase() ? key.toUpperCase() : key.toLowerCase() ;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isUpercase() {
        return upercase;
    }

    public void setUpercase(boolean upercase) {
        this.upercase = upercase;
    }
}
