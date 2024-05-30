package com.selfcoder.entity;

import java.io.Serializable;
import java.util.Set;

import com.selfcoder.request.CreateUserRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(sequenceName = "user_seq", allocationSize = 1, initialValue = 1, name = "user_seq")
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(length = 50)
    private String firstName;

    @Size(max = 15)
    @Column(length = 15)
    private String phoneNumber;

    @Email
    @NotBlank(message = "Email is mandatory")
    @Size(max = 100)
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password; // Ensure to hash the password before storing

    @Size(max = 50)
    @Column(length = 50)
    private String country;

    @Size(max = 100)
    @Column(length = 100)
    private String linkedin;

    @Column(updatable = false)
    private Long createdAt;

    @Column
    private Long updatedAt;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_problem",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "id"))
    private Set<Problem> problems	;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_blog",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"))
    private Set<Blog> blogs;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_course",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> courses;

    @OneToMany(mappedBy = "creator", cascade = { CascadeType.MERGE })
    private Set<Course> createdCourses;

    @OneToMany(cascade = { CascadeType.MERGE })
    private Set<Role> roles;

    @PrePersist
    private void onCreate() {
        createdAt = System.currentTimeMillis();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = System.currentTimeMillis();
    }

    public static User fromCreateUserRequest(Long id, CreateUserRequest createUserForm) {
        return User.builder()
                .id(id)
                .firstName(createUserForm.getFirstName())
                .phoneNumber(createUserForm.getPhoneNumber())
                .email(createUserForm.getEmail())
                .password(createUserForm.getPassword()) // Remember to hash the password before saving
                .country(createUserForm.getCountry())
                .linkedin(createUserForm.getLinkedin())
                .build();
    }

    public static User fromCreateUserRequest(CreateUserRequest createUserForm) {
        return User.builder()
                .firstName(createUserForm.getFirstName())
                .phoneNumber(createUserForm.getPhoneNumber())
                .email(createUserForm.getEmail())
                .password(createUserForm.getPassword()) // Remember to hash the password before saving
                .country(createUserForm.getCountry())
                .linkedin(createUserForm.getLinkedin())
                .build();
    }
}
