package com.example.demo.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Todo;
import com.example.demo.domain.exception.TodoNotFoundException;
import com.example.demo.domain.repository.TodoRepository;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {
	@Autowired
	TodoRepository repository;

	@Override
	public List<Todo> findAll() {
		return repository.findAll();
	}

	@Override
	public Todo findOne(Long id) {
		return repository.findById(id)
				.orElse(null);
	}

	@Override
	public Todo create(Todo todo) {
		return repository.save(todo);
	}

	@Override
	public Todo finish(Long id){
		Todo todo = repository.findById(id)
				.orElseThrow(() -> new TodoNotFoundException(id));
		if (todo.getIsDone()) {
			return todo;
		}
		todo.setIsDone(true);
		return repository.save(todo);
	}

	@Override
	public void delete(Long id) {
		repository.findById(id)
			.orElseThrow(() -> new TodoNotFoundException(id));
		repository.deleteById(id);
	}

}
