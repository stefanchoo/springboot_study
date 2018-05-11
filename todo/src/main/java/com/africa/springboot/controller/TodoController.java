package com.africa.springboot.controller;

import com.africa.springboot.domain.Todo;
import com.africa.springboot.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: StefanChoo
 * Date: 2018/5/10
 */

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
@Slf4j
public class TodoController {

    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAllTodos() {
        log.debug("REST request: getAllTodos");
        return ResponseEntity.ok(todoRepository.findAll());
    }

    @GetMapping("/todos/user/{userId}")
    public ResponseEntity<List<Todo>> getAllTodosByUserId(@PathVariable String userId) {
        log.debug("REST request: getAllTodosByUserId: " , userId);
        return ResponseEntity.ok(todoRepository.findByUserId(new ObjectId(userId)));
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        log.debug("REST request: createTodo: ", todo);
        if(todo.getId() != null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(todoRepository.insert(todo));
    }

    @PutMapping("/todos")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
        log.debug("REST request: updateTodo: ", todo);
        if(todo.getId() == null) {
            return createTodo(todo);
        }
        return ResponseEntity.ok(todoRepository.save(todo));
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable String id) {
        log.debug("REST request: getTodo: ", id);
        return ResponseEntity.ok(todoRepository.findOne(id));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        log.debug("REST request: deleteTodo: ", id);
        todoRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
