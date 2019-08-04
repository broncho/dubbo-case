package com.github.broncho.dubbo.api;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
public class Message implements Serializable {
    
    private String id;
    private String name;
    private LocalDateTime dateTime;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
