package com.example.demo.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.domain.entity.Todo;
import com.example.demo.domain.exception.TodoNotFoundException;
import com.example.demo.domain.repository.TodoRepository;

@ExtendWith(SpringExtension.class)
class TodoServiceImplTest {

	@Mock
	private TodoRepository mockRepository;
	@InjectMocks
	private TodoServiceImpl service;

	@Test
	void shouldReturnTodo() {
		Todo expected = this.createOne();
		when(this.mockRepository.findById(1L)).thenReturn(Optional.of(expected));
		Todo actual = this.service.findOne(1L);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void shouldReturnNull() {
		when(this.mockRepository.findById(99L)).thenReturn(Optional.empty());
		Todo actual = this.service.findOne(99L);
		assertThat(actual).isEqualTo(null);
	}

	@Test
	void shouldReturnTodos() {
		List<Todo> expected = this.createTwo();
		when(this.mockRepository.findAll()).thenReturn(expected);
		List<Todo> actual = this.service.findAll();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void shouldCreateTodo() {
		Todo expected = this.createOne();
		when(this.mockRepository.save(expected)).thenReturn(expected);
		Todo actual = this.service.create(expected);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void shouldUpdateTodo() throws TodoNotFoundException {
		Todo input = createOne();
		Todo expected = this.createOneFinished();
		when(this.mockRepository.findById(1L)).thenReturn(Optional.of(input));
		when(this.mockRepository.save(input)).thenReturn(expected);
		Todo actual = this.service.finish(1L);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void shouldFailUpdateWhenTodoNotFound() {
		assertThrows(TodoNotFoundException.class,
				() -> {
					this.service.finish(99L);
				});
	}

	@Test
	void shouldDeleteTodo() {
		when(this.mockRepository.findById(1L)).thenReturn(Optional.of(this.createOne()));
		this.service.delete(1L);
	}

	@Test
	void shouldFailDeleteWhenTodoNotFound() {
		assertThrows(TodoNotFoundException.class,
				() -> {
					this.service.delete(99L);
				});
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
