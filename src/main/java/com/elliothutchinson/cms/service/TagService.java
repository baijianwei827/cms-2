package com.elliothutchinson.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Tag;
import com.elliothutchinson.cms.repository.TagRepository;

@Service
public class TagService {

    private TagRepository tagRepository;

    protected TagService() {
    }

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAllByOrderByTitleAsc();
    }

    public List<Article> findArticlesFromTag(Long id) {
        Tag tag = tagRepository.findOne(id);
        List<Article> articles;
        if (tag == null) {
            articles = new ArrayList<>();
        } else {
            articles = tag.getArticles();
        }
        return articles;
    }

    public String findContentTitle(Long id) {
        String msg = "Articles Tagged: ";
        Tag tag = tagRepository.findOne(id);
        if (tag == null) {
            return "Tag Not Found";
        } else {
            return msg + tag.getTitle();
        }
    }
}
