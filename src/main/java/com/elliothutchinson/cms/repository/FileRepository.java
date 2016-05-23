package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.File;

public interface FileRepository extends CrudRepository<File, Long> {
	
	List<File> findByFilename(String filename);
	List<File> findAllByOrderByFilename();
	List<File> findAllByArticleIsNullOrderByFilename();
}
