package com.africa.springboot.repository;

import com.africa.springboot.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: StefanChoo
 * Date: 2018/5/10
 */
@Repository
public interface UserRepository  extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
