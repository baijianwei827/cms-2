package com.elliothutchinson.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Author;
import com.elliothutchinson.cms.domain.Section;
import com.elliothutchinson.cms.dto.ArticleDto;
import com.elliothutchinson.cms.dto.ForeignEntity;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.AuthorRepository;
import com.elliothutchinson.cms.repository.SectionRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestArticleService {

    private ArticleRepository articleRepository;
    private AuthorRepository authorRepository;
    private SectionRepository sectionRepository;

    @Autowired
    public RestArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository,
            SectionRepository sectionRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.sectionRepository = sectionRepository;
    }

    public String findAllArticles() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("articles");

        List<ArticleDto> articleDtos = new ArrayList<>();
        List<Article> articles = articleRepository.findAllByOrderByDateCreatedDesc();
        for (Article a : articles) {
            articleDtos.add(new ArticleDto(a));
        }

        return writer.writeValueAsString(articleDtos);
    }

    public String findArticleFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Article article = articleRepository.findOne(id);
        ArticleDto articleDto;

        if (article == null && id != -1) {
            map.put("error", "There was a problem requesting resource: article not found");
            return mapper.writeValueAsString(map);
        }

        if (id == -1) {
            articleDto = new ArticleDto();
        } else {
            articleDto = new ArticleDto(article);
        }

        List<Author> authors = authorRepository.findAllByOrderByUsernameAsc();
        List<ForeignEntity> allAuthors = new ArrayList<>();
        for (Author a : authors) {
            ForeignEntity fe;
            if (articleDto.getAuthorId() == null) {
                fe = new ForeignEntity(a.getId(), a.getUsername(), false);
            } else {
                fe = new ForeignEntity(a.getId(), a.getUsername(), (a.getId() == articleDto.getAuthorId()));
            }
            allAuthors.add(fe);
        }
        articleDto.setAuthors(allAuthors);

        List<Section> sections = sectionRepository.findAllByOrderByTitleAsc();
        List<ForeignEntity> allSections = new ArrayList<>();
        for (Section s : sections) {
            ForeignEntity fe;
            if (articleDto.getSectionId() == null) {
                fe = new ForeignEntity(s.getId(), s.getTitle(), false);
            } else {
                fe = new ForeignEntity(s.getId(), s.getTitle(), (s.getId() == articleDto.getSectionId()));
            }
            allSections.add(fe);
        }
        articleDto.setSections(allSections);

        return mapper.writeValueAsString(articleDto);
    }

    public String createArticleFromProxy(ArticleDto proxyArticle) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Author author = authorRepository.findOne(proxyArticle.getAuthorId());

        Section section = null;
        if (proxyArticle.getSectionId() != null) {
            section = sectionRepository.findOne(proxyArticle.getSectionId());
        }

        Article article = new Article(proxyArticle.getTitle(), proxyArticle.getContent(), author, section);

        articleRepository.save(article);

        return mapper.writeValueAsString(article);
    }

    public String updateArticleFromProxy(long id, ArticleDto proxyArticle) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxyArticle.getId()) {
            map.put("error", "There was a problem updating resource: article id mismatch");
            return mapper.writeValueAsString(map);
        }

        Article article = articleRepository.findOne(proxyArticle.getId());
        if (article == null) {
            map.put("error", "There was an error updating resource: article not found");
            return mapper.writeValueAsString(map);
        }

        article.setTitle(proxyArticle.getTitle());
        article.setContent(proxyArticle.getContent());
        article.setAuthor(authorRepository.findOne(proxyArticle.getAuthorId()));

        if (proxyArticle.getSectionId() != null) {
            article.setSection(sectionRepository.findOne(proxyArticle.getSectionId()));
        } else {
            article.setSection(null);
        }

        articleRepository.save(article);

        return mapper.writeValueAsString(article);
    }

    public String deleteArticleFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Article article = articleRepository.findOne(id);

        if (article == null) {
            map.put("error", "There was an error deleting resource: article not found");
            return mapper.writeValueAsString(map);
        }

        articleRepository.delete(article);

        map.put("success", "Article resource successfully deleted");
        return mapper.writeValueAsString(map);
    }
}
