package com.sky.hadoop.key;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadOtherTask implements Runnable {
    private Logger log = LoggerFactory.getLogger(LoadOtherTask.class);
    private LeafSegmentPair leafSegmentPair ;
    private AbstractGenerator abstractGenerator ;
    private String dbName ;
    private String tableName ;

    public LoadOtherTask(LeafSegmentPair leafSegmentPair, AbstractGenerator abstractGenerator, String dbName, String tableName) {
        this.leafSegmentPair = leafSegmentPair;
        this.abstractGenerator = abstractGenerator;
        this.dbName = dbName;
        this.tableName = tableName;
    }

    public void run() {
// 如果消耗了则获取另外一个缓存
        LeafSegment other = leafSegmentPair.getNotCurrent() ;
        // 如果另外一个缓存是不可用的
        if(other == null || !other.isAvailable()){
            // 刷新另外一个缓存
            LeafSegment leafSegment = abstractGenerator.getLeafSegment(dbName, tableName) ;
//            log.info("多线程重新加载缓存other {}",leafSegment);
            other = leafSegmentPair.getNotCurrent() ;
            if(other == null || !other.isAvailable()) {
                leafSegmentPair.setNotCurrent(leafSegment);
            }

        }
    }
}
