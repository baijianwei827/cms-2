package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	List<Author> findByUsername(String username);
	List<Author> findByEmail(String email);
	List<Author> findAllByOrderByUsernameAsc();
}
