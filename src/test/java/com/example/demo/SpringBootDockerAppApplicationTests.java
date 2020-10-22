package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.entity.Todo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SpringBootDockerAppApplicationTests {

	RestTemplate restTemplate = new RestTemplate();

	@Test
	void ShouldGetAll() {
		// All
		ResponseEntity<Todo[]> response = restTemplate.getForEntity(
				"http://localhost:8080/todos", Todo[].class);
		Todo[] todos = response.getBody();
		assertThat(todos[0].getId()).isEqualTo(1L);
		assertThat(todos[0].getTitle()).isEqualTo("foo");
		assertThat(todos[0].getIsDone()).isEqualTo(false);
		assertThat(todos[0].getCreatedAt()).isNotNull();
		assertThat(todos[0].getUpdatedAt()).isNotNull();
		assertThat(todos[1].getId()).isEqualTo(2L);
		assertThat(todos[1].getTitle()).isEqualTo("bar");
		assertThat(todos[1].getIsDone()).isEqualTo(false);
		assertThat(todos[1].getCreatedAt()).isNotNull();
		assertThat(todos[1].getUpdatedAt()).isNotNull();
		assertThat(todos[2].getId()).isEqualTo(3L);
		assertThat(todos[2].getTitle()).isEqualTo("baz");
		assertThat(todos[2].getIsDone()).isEqualTo(false);
		assertThat(todos[2].getCreatedAt()).isNotNull();
		assertThat(todos[2].getUpdatedAt()).isNotNull();

		// #1
		Todo todo1 = restTemplate.getForObject(
				"http://localhost:8080/todos/1", Todo.class);
		assertThat(todo1.getId()).isEqualTo(1L);
		assertThat(todo1.getTitle()).isEqualTo("foo");
		assertThat(todo1.getIsDone()).isEqualTo(false);
		assertThat(todo1.getCreatedAt()).isNotNull();
		assertThat(todo1.getUpdatedAt()).isNotNull();

		// #2
		Todo todo2 = restTemplate.getForObject(
				"http://localhost:8080/todos/2", Todo.class);
		assertThat(todo2.getId()).isEqualTo(2L);
		assertThat(todo2.getTitle()).isEqualTo("bar");
		assertThat(todo2.getIsDone()).isEqualTo(false);
		assertThat(todo2.getCreatedAt()).isNotNull();
		assertThat(todo2.getUpdatedAt()).isNotNull();

		// #3
		Todo todo3 = restTemplate.getForObject(
				"http://localhost:8080/todos/3", Todo.class);
		assertThat(todo3.getId()).isEqualTo(3L);
		assertThat(todo3.getTitle()).isEqualTo("baz");
		assertThat(todo3.getIsDone()).isEqualTo(false);
		assertThat(todo3.getCreatedAt()).isNotNull();
		assertThat(todo3.getUpdatedAt()).isNotNull();
	}

	@Test
	void shouldCreateFinishAndDelete() throws Exception {
		// POST #4
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject todoJson = new JSONObject();
		todoJson.put("title", "test");
		HttpEntity<String> request = new HttpEntity<String>(todoJson.toString(), headers);
		Todo todo4 = restTemplate.postForObject(
				"http://localhost:8080/todos", request, Todo.class);
		assertThat(todo4.getId()).isEqualTo(4L);
		assertThat(todo4.getTitle()).isEqualTo("test");
		assertThat(todo4.getIsDone()).isEqualTo(false);
		assertThat(todo4.getCreatedAt()).isNotNull();
		assertThat(todo4.getUpdatedAt()).isNotNull();

		// PUT #4
		restTemplate.put("http://localhost:8080/todos/4", null);
		Todo todo4Done = restTemplate.getForObject(
				"http://localhost:8080/todos/4", Todo.class);
		assertThat(todo4Done.getIsDone()).isEqualTo(true);

		// DELETE #4
		restTemplate.delete("http://localhost:8080/todos/4");
		Todo todo4Deleted = restTemplate.getForObject(
				"http://localhost:8080/todos/4", Todo.class);
		assertThat(todo4Deleted).isNull();
	}

}
