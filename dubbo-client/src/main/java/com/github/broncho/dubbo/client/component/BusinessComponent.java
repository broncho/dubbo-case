package com.github.broncho.dubbo.client.component;

import com.github.broncho.dubbo.api.HelloService;
import com.github.broncho.dubbo.api.Message;
import com.github.broncho.dubbo.api.MessageService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
@Component
public class BusinessComponent {
    
    @Reference
    private HelloService helloService;
    
    @Reference
    private MessageService messageService;
    
    public void greeting(String name) {
        System.out.println(helloService.sayHello(name));
    }
    
    public void convert(Message message) {
        System.out.println(messageService.messageToPerson(message));
    }
}
