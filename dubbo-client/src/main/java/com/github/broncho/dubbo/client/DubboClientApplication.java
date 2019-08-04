package com.github.broncho.dubbo.client;

import com.github.broncho.dubbo.api.Message;
import com.github.broncho.dubbo.client.component.BusinessComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
@SpringBootApplication
public class DubboClientApplication {
    
    
    public static void main(String[] args) {
        
        ConfigurableApplicationContext context = SpringApplication.run(DubboClientApplication.class, args);
        
        BusinessComponent component = context.getBean(BusinessComponent.class);
        
        //RPC1
        component.greeting("Dubbo RPC");
        
        //RPC2
        Message message = new Message();
        message.setId("101");
        message.setName("Dubbo RPC");
        message.setDateTime(LocalDateTime.now());
        component.convert(message);
    }
}
