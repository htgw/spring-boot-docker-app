package com.example.demo.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.domain.entity.Todo;
import com.example.demo.domain.service.TodoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService service;

	@Test
	void shouldGetTodo() throws Exception {
		when(this.service.findOne(1L)).thenReturn(this.createOne());
		this.mockMvc.perform(get("/todos/{id}", 1L))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", is(1)))
			.andExpect(jsonPath("title", is("foo")))
			.andExpect(jsonPath("isDone", is(false)));
	}

	@Test
	void shouldGetTodos() throws Exception {
		when(this.service.findAll()).thenReturn(this.createTwo());
		this.mockMvc.perform(get("/todos"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id", is(1)))
			.andExpect(jsonPath("$.[0].title", is("foo")))
			.andExpect(jsonPath("$.[0].isDone", is(false)))
			.andExpect(jsonPath("$.[1].id", is(2)))
			.andExpect(jsonPath("$.[1].title", is("bar")))
			.andExpect(jsonPath("$.[1].isDone", is(false)));
	}

	@Test
	void shouldPostTodo() throws Exception {
		Todo todo = new Todo();
		todo.setTitle("foo");
		when(this.service.create(todo)).thenReturn(createOne());
		this.mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\": \"foo\"}"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void shouldUpdateTodo() throws Exception {
		when(this.service.finish(1L)).thenReturn(createOneFinished());
		this.mockMvc.perform(put("/todos/{id}", 1L))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", is(1)))
			.andExpect(jsonPath("title", is("foo")))
			.andExpect(jsonPath("isDone", is(true)));
	}

	@Test
	void shouldDeleteTodo() throws Exception {
		this.mockMvc.perform(delete("/todos/{id}", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	private Todo createOne() {
		Todo todo = new Todo();
		todo.setId(1L);
		todo.setTitle("foo");
		todo.setIsDone(false);
		LocalDateTime timestamp = LocalDateTime.now();
		todo.setCreatedAt(timestamp);
		todo.setUpdatedAt(timestamp);
		return todo;
	}

	private Todo createOneFinished() {
		Todo todo = new Todo();
		todo.setId(1L);
		todo.setTitle("foo");
		todo.setIsDone(true);
		LocalDateTime timestamp = LocalDateTime.now();
		todo.setCreatedAt(timestamp);
		todo.setUpdatedAt(timestamp);
		return todo;
	}

	private List<Todo> createTwo() {
		LocalDateTime timestamp = LocalDateTime.now();
		Todo foo = new Todo();
		foo.setId(1L);
		foo.setTitle("foo");
		foo.setIsDone(false);
		foo.setCreatedAt(timestamp);
		foo.setUpdatedAt(timestamp);
		Todo baz = new Todo();
		baz.setId(2L);
		baz.setTitle("bar");
		baz.setIsDone(false);
		baz.setCreatedAt(timestamp);
		baz.setUpdatedAt(timestamp);
		List<Todo> todos = Arrays.asList(foo, baz);
		return todos;
	}
}
