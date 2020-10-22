package com.example.demo.domain.exception;

public class TodoNotFoundException extends RuntimeException {

	public TodoNotFoundException(Long id) {
		super("Could not find todo #" + id);
	}

}
