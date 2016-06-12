package com.elliothutchinson.cms.service.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Tag;
import com.elliothutchinson.cms.dto.ForeignEntity;
import com.elliothutchinson.cms.dto.TagDto;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.TagRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestTagService {
    
    private TagRepository tagRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public RestTagService(TagRepository tagRepository, ArticleRepository articleRepository) {
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
    }
    
    public String findAllTags() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("tags");

        List<TagDto> tagDtos = new ArrayList<>();
        List<Tag> tags = tagRepository.findAllByOrderByTitleAsc();
        for (Tag t : tags) {
            tagDtos.add(new TagDto(t));
        }

        return writer.writeValueAsString(tagDtos);
    }
    
    public String findTagFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Tag tag = tagRepository.findOne(id);
        TagDto tagDto;

        if (tag == null && id != -1) {
            map.put("error", "There was a problem requesting resource: tag not found");
            return mapper.writeValueAsString(map);
        }

        if (id == -1) {
            tagDto = new TagDto();
        } else {
            tagDto = new TagDto(tag);
        }

        Set<Article> articleSet;
        if (id == -1) {
            articleSet = new HashSet<>();
        } else {
            articleSet = new HashSet<>(tag.getArticles());
        }
        
        List<Article> articles = articleRepository.findAllByOrderByDateCreatedDesc();
        List<ForeignEntity> allArticles = new ArrayList<>();
        for (Article a : articles) {
            ForeignEntity fe;
            if (articleSet.contains(a)) {
                fe = new ForeignEntity(a.getId(), a.getTitle(), true);
            } else {
                fe = new ForeignEntity(a.getId(), a.getTitle(), false);
            }
            allArticles.add(fe);
        }
        tagDto.setArticles(allArticles);

        return mapper.writeValueAsString(tagDto);
    }
    
    public String createTagFromProxy(TagDto proxyTag) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        Tag tag = new Tag(proxyTag.getTitle());
        
        Set<Long> articleIdSet = new HashSet<>();
        for (ForeignEntity fe : proxyTag.getArticles()) {
            if (fe.isCurrent()) {
                articleIdSet.add(fe.getId());
            }
        }
        List<Article> articles = articleRepository.findAllByOrderByDateCreatedDesc();
        for (Article a : articles) {
            if (articleIdSet.contains(a.getId())) {
                tag.addArticle(a);
            }
        }
        
        tagRepository.save(tag);
        
        return mapper.writeValueAsString(tag);
    }
    
    public String updateTagFromProxy(long id, TagDto proxyTag) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxyTag.getId()) {
            map.put("error", "There was a problem updating resource: tag id mismatch");
            return mapper.writeValueAsString(map);
        }
        
        Tag tag = tagRepository.findOne(proxyTag.getId());
        if (tag == null) {
            map.put("error", "There was an error updating resource: tag not found");
            return mapper.writeValueAsString(map);
        }

        tag.setTitle(proxyTag.getTitle());
        
        Set<Long> articleIdSet = new HashSet<>();
        for (ForeignEntity fe : proxyTag.getArticles()) {
            if (fe.isCurrent()) {
                articleIdSet.add(fe.getId());
            }
        }
        List<Article> articles = articleRepository.findAllByOrderByDateCreatedDesc();
        for (Article a : articles) {
            if (articleIdSet.contains(a.getId())) {
                tag.addArticle(a);
            }
        }
        
        tagRepository.save(tag);
        
        return mapper.writeValueAsString(tag);
    }
    
    public String deleteTagFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Tag tag = tagRepository.findOne(id);

        if (tag == null) {
            map.put("error", "There was an error deleting resource: tag not found");
            return mapper.writeValueAsString(map);
        }

        tagRepository.delete(tag);

        map.put("success", "Tag resource successfully deleted");
        return mapper.writeValueAsString(map);
    }

}
