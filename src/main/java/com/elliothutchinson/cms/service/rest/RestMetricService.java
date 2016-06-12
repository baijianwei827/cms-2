package com.elliothutchinson.cms.service.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.repository.ArchiveRepository;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.AuthorRepository;
import com.elliothutchinson.cms.repository.CommentRepository;
import com.elliothutchinson.cms.repository.FeatureRepository;
import com.elliothutchinson.cms.repository.FileRepository;
import com.elliothutchinson.cms.repository.SectionRepository;
import com.elliothutchinson.cms.repository.SiteDetailRepository;
import com.elliothutchinson.cms.repository.TagRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestMetricService {

    private AuthorRepository authorRepository;
    private SectionRepository sectionRepository;
    private ArticleRepository articleRepository;
    private FileRepository fileRepository;
    private ArchiveRepository archiveRepository;
    private FeatureRepository featureRepository;
    private TagRepository tagRepository;
    private CommentRepository commentRepository;
    private SiteDetailRepository siteDetailRepository;

    @Autowired
    public RestMetricService(AuthorRepository authorRepository, SectionRepository sectionRepository,
            ArticleRepository articleRepository, FileRepository fileRepository, ArchiveRepository archiveRepository,
            FeatureRepository featureRepository, TagRepository tagRepository, CommentRepository commentRepository,
            SiteDetailRepository siteDetailRepository) {
        this.authorRepository = authorRepository;
        this.sectionRepository = sectionRepository;
        this.articleRepository = articleRepository;
        this.fileRepository = fileRepository;
        this.archiveRepository = archiveRepository;
        this.featureRepository = featureRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
        this.siteDetailRepository = siteDetailRepository;
    }

    public String findAllMetrics() throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        map.put("title", "Metrics");
        map.put("authors", String.valueOf(authorRepository.count()));
        map.put("admins", String.valueOf(authorRepository.countByAdminIsTrue()));
        map.put("sections", String.valueOf(sectionRepository.count()));
        map.put("articles", String.valueOf(articleRepository.count()));
        map.put("files", String.valueOf(fileRepository.count()));
        map.put("archives", String.valueOf(archiveRepository.count()));
        map.put("features", String.valueOf(featureRepository.count()));
        map.put("tags", String.valueOf(tagRepository.count()));
        map.put("comments", String.valueOf(commentRepository.count()));
        map.put("siteDetails", String.valueOf(siteDetailRepository.count()));

        return mapper.writeValueAsString(map);
    }

    public String findAuthorMetricsById(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        map.put("title", "Author Metrics");
        map.put("articles", String.valueOf(articleRepository.countByAuthorId(id)));
        map.put("comments", String.valueOf(commentRepository.countAuthorCommentsById(id)));

        return mapper.writeValueAsString(map);
    }

    public String findSectionMetricsById(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        map.put("title", "Section Metrics");
        map.put("articles", String.valueOf(articleRepository.countBySectionId(id)));

        return mapper.writeValueAsString(map);
    }

    public String findArticleMetricsById(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        map.put("title", "Article Metrics");
        map.put("files", String.valueOf(fileRepository.countByArticleId(id)));
        map.put("archives", String.valueOf(archiveRepository.countByArticleId(id)));
        map.put("features", String.valueOf(featureRepository.countByArticleId(id)));
        map.put("comments", String.valueOf(commentRepository.countByArticleId(id)));
        map.put("tags", String.valueOf(articleRepository.findOne(id).getTags().size()));

        return mapper.writeValueAsString(map);
    }
    
    public String findTagMetricsById(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        map.put("title", "Tag Metrics");
        map.put("articles", String.valueOf(tagRepository.findOne(id).getArticles().size()));

        return mapper.writeValueAsString(map);
    }
}
