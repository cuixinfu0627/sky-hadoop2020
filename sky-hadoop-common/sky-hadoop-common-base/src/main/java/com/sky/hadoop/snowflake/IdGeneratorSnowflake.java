package com.sky.hadoop.snowflake;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @name: IdGeneratorSnowflakel <tb>
 * @title: 请输入类描述  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/12 11:14 <tb>
 */
@Slf4j
@Component
public class IdGeneratorSnowflake {
    //0-31
    private long workerId = 0;
    private long datacenterId = 1;
    private Snowflake snowflake= IdUtil.createSnowflake(workerId,datacenterId);

    @PostConstruct
    public void init(){
        try {
            long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的workerId:{}",workerId);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("当前机器的workerId获取失败!",e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }finally {

        }
    }

    public synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId,long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        System.out.println(new IdGeneratorSnowflake().snowflakeId());
    }
}
