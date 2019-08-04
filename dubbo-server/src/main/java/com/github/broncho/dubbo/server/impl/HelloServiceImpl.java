package com.github.broncho.dubbo.server.impl;

import com.github.broncho.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Welcome " + name + " at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
