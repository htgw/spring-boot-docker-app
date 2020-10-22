package com.example.demo.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.domain.exception.TodoNotFoundException;
import com.example.demo.web.response.ErrorResponse;

@RestControllerAdvice
public class TodoControllerAdvice {

	@ExceptionHandler(TodoNotFoundException.class)
	public ResponseEntity<ErrorResponse> todoNotFoundHandler(TodoNotFoundException ex) {
		return new ResponseEntity<ErrorResponse>(
				new ErrorResponse(ex.getMessage())
				, HttpStatus.NOT_FOUND);
	}

}
