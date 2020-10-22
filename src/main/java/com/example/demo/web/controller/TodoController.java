package com.example.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.entity.Todo;
import com.example.demo.domain.exception.TodoNotFoundException;
import com.example.demo.domain.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
	@Autowired
	TodoService service;

	 @RequestMapping(method = RequestMethod.GET)
	 public List<Todo> fetchTodos() {
		 return service.findAll();
	 }

	 @RequestMapping(method = RequestMethod.GET, value="{id}")
	 public Todo fetchTodo(@PathVariable("id") Long id) {
		 return service.findOne(id);
	 }

	 @RequestMapping(method = RequestMethod.POST)
	 public Todo createTodo(@Validated @RequestBody Todo todo) {
		 todo.setIsDone(false);
		 return service.create(todo);
	 }

	 @RequestMapping(method = RequestMethod.PUT, value="{id}")
	 public Todo finishTodo(@PathVariable("id") Long id) throws TodoNotFoundException {
		 return service.finish(id);
	 }

	 @RequestMapping(method = RequestMethod.DELETE, value="{id}")
	 public void deleteTodo(@PathVariable("id") Long id) {
		 service.delete(id);
	 }
}
