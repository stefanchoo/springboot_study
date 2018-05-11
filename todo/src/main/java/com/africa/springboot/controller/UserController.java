package com.africa.springboot.controller;

import com.africa.springboot.domain.User;
import com.africa.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 *
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostAuthorize("returnObject.getBody().username == principal.username or hasRole('ROLE_ADMIN')")
    @GetMapping("/users/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return repository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User addedUser) {
        return ResponseEntity.ok(repository.insert(addedUser));
    }

    @PostAuthorize("returnObject.getBody().username == principal.username or hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(repository.findOne(id));
    }

    @PostAuthorize("returnObject.getBody().username == principal.username or hasRole('ROLE_ADMIN')")
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        if(updatedUser.getId() == null) {
            return createUser(updatedUser);
        }
        return ResponseEntity.ok(repository.save(updatedUser));
    }

    @PostAuthorize("returnObject.getBody().username == principal.username or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        User deletedUser = repository.findOne(id);
        repository.delete(id);
        return ResponseEntity.ok(deletedUser);
    }
}
