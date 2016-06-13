package com.elliothutchinson.cms.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.elliothutchinson.cms.domain.Feature;

public class FeatureDto {
    
    private Long id;
    private String cover;
    private boolean publish;
    private LocalDateTime dateCreated;
    private Long articleId;
    private String articleTitle;
    
    private List<ForeignEntity> filenames;
    private List<ForeignEntity> articles;
    
    public FeatureDto() {
    }
    
    public FeatureDto(Feature feature) {
        this.id = feature.getId();
        this.cover = feature.getCover();
        this.publish = feature.isPublish();
        this.dateCreated = feature.getDateCreated();
        this.articleId = feature.getArticle().getId();
        this.articleTitle = feature.getArticle().getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public List<ForeignEntity> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<ForeignEntity> filenames) {
        this.filenames = filenames;
    }

    public List<ForeignEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<ForeignEntity> articles) {
        this.articles = articles;
    }
}
