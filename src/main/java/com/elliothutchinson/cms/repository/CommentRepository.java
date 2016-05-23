package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	List<Comment> findByName(String name);
}
