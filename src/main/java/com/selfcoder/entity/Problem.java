package com.selfcoder.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "problems")
public class Problem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pro_seq", initialValue = 1, allocationSize = 1, sequenceName = "pro_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pro_seq")
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(nullable = false, unique = true, length = 200)
	private String title;

	@Column(nullable = false, length = 500)
	private String link;

	@Column(nullable = false, length = 20)
	private String acceptance;

	@Column(nullable = false, length = 20)
	private String difficulty;

	@Column(nullable = false, length = 1000)
	private String solution;

	@Column(updatable = false)
	private Long createdAt;

	private Long updatedAt;

	private Boolean isDeleted = false;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "problem_tags", joinColumns = @JoinColumn(name = "problem_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tag;

	@PrePersist
	private void onCreate() {
		createdAt = System.currentTimeMillis();
	}

	@PreUpdate
	private void onUpdate() {
		updatedAt = System.currentTimeMillis();
	}
}
