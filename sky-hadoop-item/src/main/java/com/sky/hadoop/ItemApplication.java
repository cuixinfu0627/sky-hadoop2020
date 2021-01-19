package com.sky.hadoop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @name: PassportApplication <tb>
 * @title: 用户服务 - consumer <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/18 16:21 <tb>
 */
@SpringBootApplication(scanBasePackages = {"com.sky.hadoop"})
@EnableDiscoveryClient
public class ItemApplication {
    private static final Logger logger = LoggerFactory.getLogger(ItemApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(ItemApplication.class);
        Map<String, Object> defProperties =  new HashMap<String, Object>();
        defProperties.put("spring.profiles.default", "local");
        app.setDefaultProperties(defProperties);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getActiveProfiles());
    }
}
