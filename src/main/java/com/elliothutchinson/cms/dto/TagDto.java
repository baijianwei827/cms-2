package com.elliothutchinson.cms.dto;

import java.util.List;

import com.elliothutchinson.cms.domain.Tag;

public class TagDto {
    
    private Long id;
    private String title;
    
    List<ForeignEntity> articles;
    
    public TagDto() {
    }
    
    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.title = tag.getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ForeignEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<ForeignEntity> articles) {
        this.articles = articles;
    }
}
