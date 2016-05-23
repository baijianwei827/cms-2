package com.elliothutchinson.cms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Section;
import com.elliothutchinson.cms.repository.ArticleRepository;

@Service
public class ArticleService {
	
	private ArticleRepository articleRepository;
	
	protected ArticleService() {}
	
	@Autowired
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	public List<Article> findAllArticles() {
		return articleRepository.findAllByOrderByDateCreatedDesc();
	}
	
	public List<Article> findRecentArticles() {
		return articleRepository.findTop2ByOrderByDateCreatedDesc();
	}
	
	public List<Article> findArticlesWithoutSection() {
		return articleRepository.findAllBySectionIsNullOrderByDateCreatedDesc();
	}
	
	public Article findArticleFromId(Long id) {
		return articleRepository.findOne(id);
	}
	
	public String findContentTitle() {
		return "Articles";
	}
	
	public String findContentTitle(Long id) {
		String msg;
		Article article = articleRepository.findOne(id);
		
		if (article == null) {
			msg = "Article Not Found";
		} else {
			msg = article.getTitle();
		}
		
		return msg;
	}
	
	public Long findSectionId(Long articleId) {
		Long sectionId;
		Article article = articleRepository.findOne(articleId);
		
		if (article == null) {
			sectionId = -1L;
		} else {
			Section section = article.getSection();
			
			if (section == null) {
				sectionId = -1L;
			} else {
				sectionId = section.getId();
			}
		}
		
		return sectionId;
	}
}
