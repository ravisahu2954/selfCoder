package com.selfcoder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfcoder.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	Optional<Tag> findByTagName(String tagName);

}
