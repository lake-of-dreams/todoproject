package com.emirates.todoservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emirates.todoservice.model.TodoItem;
import com.emirates.todoservice.persistence.NextSequenceService;
import com.emirates.todoservice.persistence.TodoItemMongoDBRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoServiceController {

    @Autowired
    TodoItemMongoDBRepository todoItemsRepository;
    
    @Autowired
    NextSequenceService nextSequenceService;

    @GetMapping("/todoItems")
    public List<TodoItem> getAllTodos() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        return todoItemsRepository.findAll(sortByCreatedAtDesc);
    }
    
    @GetMapping(value="/todoItem/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable("id") String id) {
        TodoItem todo = todoItemsRepository.findOne(id);
        if(todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        }
    }
    
    @GetMapping(value="/todoItems/pending")
    public List<TodoItem> getPendingTodoItems() {
        return todoItemsRepository.findByCompleted(false);       
    }
    
    @GetMapping(value="/todoItems/done")
    public List<TodoItem> getCompletedTodoItems() {
        return todoItemsRepository.findByCompleted(true);       
    }


    @PostMapping("/todoItem")
    public TodoItem createTodo(@Valid @RequestBody TodoItem todo) {
    		todo.setId(nextSequenceService.getNextSequence("todos"));
        todo.setCompleted(false);
        return todoItemsRepository.save(todo);
    }

    @PutMapping(value="/todoItem/{id}")
    public ResponseEntity<TodoItem> updateTodo(@PathVariable("id") String id,
                                           @Valid @RequestBody TodoItem todo) {
        TodoItem todoData = todoItemsRepository.findOne(id);
        if(todoData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        todoData.setDescription(todo.getDescription());
        todoData.setCompleted(todo.getCompleted());
        TodoItem updatedTodo = todoItemsRepository.save(todoData);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @DeleteMapping(value="/todoItem/{id}")
    public void deleteTodo(@PathVariable("id") String id) {
        todoItemsRepository.delete(id);
    }
}