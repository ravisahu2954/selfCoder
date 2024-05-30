package com.selfcoder.entity;

import java.io.Serializable;
import java.util.Set;

import com.selfcoder.request.UpdateCourseRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "courses")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "course_seq", allocationSize = 1, initialValue = 1, sequenceName = "course_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Course name is mandatory")
    @Size(max = 100, message = "Course name must be less than 100 characters")
    @Column(nullable = false, length = 100)
    private String courseName;

    @Column(updatable = false)
    private Long createdAt;

    @Column
    private Long updatedAt;

    @PrePersist
    private void onCreate() {
        createdAt = System.currentTimeMillis();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = System.currentTimeMillis();
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users;

    public static Course fromUpdateRequest(Long id, UpdateCourseRequest updateCourseForm, User creator) {
        return Course.builder()
                .id(id)
                .courseName(updateCourseForm.getCourseName())
                .creator(creator)
                .build();
    }
}
