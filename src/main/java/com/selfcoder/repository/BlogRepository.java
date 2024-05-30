package com.selfcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfcoder.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>{

}
