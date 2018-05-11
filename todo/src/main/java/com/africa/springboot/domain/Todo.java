package com.africa.springboot.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Author: StefanChoo
 * Date: 2018/5/10
 */
@Data
public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String desc;
    private boolean completed;

    private User user;

    public Todo id(String id) {
        this.setId(id);
        return this;
    }

    public Todo desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public Todo completed(boolean completed) {
        this.setCompleted(completed);
        this.completed = completed;
        return this;
    }

    public Todo user(User user) {
        this.setUser(user);
        return this;
    }
}
