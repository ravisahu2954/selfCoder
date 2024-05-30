package com.selfcoder.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tags")
public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(sequenceName = "tag_seq", allocationSize = 1, initialValue = 1, name = "user_seq")
	@GeneratedValue(generator = "tag_seq", strategy = GenerationType.SEQUENCE)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Size(max = 50)
	@Column(length = 50)
	private String tagName;
}
