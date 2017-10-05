package com.emirates.todoservice.persistence;

import com.emirates.todoservice.model.TodoItem;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemMongoDBRepository extends MongoRepository<TodoItem, String> {
	List<TodoItem> findByCompleted(Boolean isCompleted);
}