package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
	
	List<Article> findByTitle(String title);
	List<Article> findAllByOrderByDateCreatedDesc();
	List<Article> findTop2ByOrderByDateCreatedDesc();
	List<Article> findAllBySectionIsNullOrderByDateCreatedDesc();
}
