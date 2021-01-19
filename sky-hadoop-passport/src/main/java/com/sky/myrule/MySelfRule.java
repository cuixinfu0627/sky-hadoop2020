package com.sky.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Configuration;

/**
 * @name: MySelfRule <tb>
 * @title: 请输入类描述  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/21 15:10 <tb>
 */
@Configuration
public class MySelfRule {
    public IRule myRule(){
        return new RandomRule(); //ribbon 负载定义随机,默认是随机
    }
}
