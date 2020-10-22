package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.entity.Todo;

public interface TodoService {
	public List<Todo> findAll();
	public Todo findOne(Long id);
	public Todo create(Todo todo);
	public Todo finish (Long id);
	public void delete(Long id);
}
