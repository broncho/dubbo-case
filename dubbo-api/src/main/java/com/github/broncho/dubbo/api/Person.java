package com.github.broncho.dubbo.api;

import java.io.Serializable;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
public class Person implements Serializable {
    
    private String id;
    
    private String name;
    
    private String birthday;
    
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
    
    public String getBirthday() {
        return birthday;
    }
    
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}