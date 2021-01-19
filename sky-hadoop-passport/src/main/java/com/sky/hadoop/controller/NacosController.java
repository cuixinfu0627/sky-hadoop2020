package com.sky.hadoop.controller;

import com.sky.hadoop.view.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("nacos")
@RestController
@RefreshScope
public class NacosController {

    private static final Logger logger = LoggerFactory.getLogger(NacosController.class);

    @Value("${config.info}")
    private String configInfo;

    @Value("${app.info}")
    private String appInfo;

    @Autowired
    DiscoveryClient discoveryClient;

    /**
     * @title: 测试nacos-config远程获取配置参数 <tb>
     * @author: cuixinfu@51play.com <tb>
     * @date: 2020/12/25 11:16<tb>
     */
    @RequestMapping("/config")
    public String config(){
        return configInfo;
    }

    @RequestMapping("/app")
    public String app(){
        return appInfo;
    }

    /**
     * discovery
     */
    @GetMapping("/discovery")
    public R discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services){
            logger. info("***** element:"+service);
        }
        List<ServiceInstance> instances= discoveryClient.getInstances("ORDER-SERVICE");
        for (ServiceInstance instance : instances){
            logger. info(instance.getInstanceId()+"\t"+instance. getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return  R.ok().put("discovery",this.discoveryClient);
    }

    /**
     * instances
     */
    @GetMapping("/instances")
    public R getInstances() {
        List<ServiceInstance> instances = discoveryClient.getServices().stream()
                .map(sid -> discoveryClient.getInstances(sid))
                .collect(Collectors.toList())
                .stream().flatMap(list -> list.stream()).collect(Collectors.toList());
        return  R.ok().put("instances",instances);
    }

}
