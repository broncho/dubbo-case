package com.github.broncho.dubbo.server;

import com.github.broncho.dubbo.server.ext.EmbeddedZooKeeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
@SpringBootApplication
public class DubboServerApplication {
    
    public static void main(String[] args) {
        new EmbeddedZooKeeper(2181, true).start();
        SpringApplication.run(DubboServerApplication.class, args);
    }
}
