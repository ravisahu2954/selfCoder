package com.selfcoder.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.selfcoder.request.UpdateCommentRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "comm_seq", initialValue = 1, allocationSize = 1, sequenceName = "comm_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false, length = 1000)
	private String msg;

	@CreationTimestamp
	private LocalDate createAt;

	@UpdateTimestamp
	private LocalDate updateAt;

	public static Comment convertCreateCommentFormToComment(Long id2, UpdateCommentRequest updateCommentForm) {

		var comment = new Comment();
		comment.setId(id2);
		comment.setMsg(updateCommentForm.getMsg());
		return comment;
	
	}
}
