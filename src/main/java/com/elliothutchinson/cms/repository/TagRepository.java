package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {
	
	List<Tag> findByTitle(String title);
	List<Tag> findAllByOrderByTitleAsc();
}
