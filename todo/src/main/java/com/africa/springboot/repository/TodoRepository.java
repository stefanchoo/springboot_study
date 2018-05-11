package com.africa.springboot.repository;

import com.africa.springboot.domain.Todo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: StefanChoo
 * Date: 2018/5/10
 */
//@RepositoryRestResource(collectionResourceRel = "todo", path = "todos")
@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

    List<Todo> findByDescLike(String desc);

    List<Todo> findByUserEmail(String userEmail);

    // 注意 MongoDB 中的id 为 ObjectId
    List<Todo> findByUserId(ObjectId userId);

    List<Todo> findByUserIdAndDescLike(ObjectId userId, String desc);

    // 注意 Spring.data 会把 id 转化为 _id
    @Query("{'user._id': ?0, 'desc': {'$regex': ?1}}")
    List<Todo> searchTodos(ObjectId userId, String desc);

}
