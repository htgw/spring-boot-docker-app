package com.example.demo.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String title;

	@Column(columnDefinition = "tinyint(1) default 0")
	private Boolean isDone;

	@CreatedDate
	@Column(insertable = false, updatable = false, columnDefinition= "timestamp default current_timestamp")
	private LocalDateTime createdAt;

	@CreatedDate
	@Column(insertable = false, updatable = true, columnDefinition= "timestamp default current_timestamp")
	private LocalDateTime updatedAt;

	@PrePersist
	public void onPrePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.setCreatedAt(now);
		this.setUpdatedAt(now);
	}

	@PreUpdate
	public void onPreUpdate() {
		this.setUpdatedAt(LocalDateTime.now());
	}

}
