package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
