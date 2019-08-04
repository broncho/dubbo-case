package com.github.broncho.dubbo.server.impl;

import com.github.broncho.dubbo.api.Message;
import com.github.broncho.dubbo.api.MessageService;
import com.github.broncho.dubbo.api.Person;
import org.apache.dubbo.config.annotation.Service;

import java.time.format.DateTimeFormatter;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
@Service(retries = 3, loadbalance = "roundrobin")
public class MessageServiceImpl implements MessageService {
    @Override
    public Person messageToPerson(Message message) {
        Person person = new Person();
        person.setId(message.getId());
        person.setName(message.getName());
        person.setBirthday(message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
        return person;
    }
}
