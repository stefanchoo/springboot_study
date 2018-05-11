package com.africa.springboot.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Author: StefanChoo
 * Date: 2018/5/10
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String username;

    private String password;
    private String email;
    private Date lastPasswordResetDate;
    private List<String> roles;
}
