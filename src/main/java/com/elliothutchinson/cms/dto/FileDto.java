package com.elliothutchinson.cms.dto;

import java.util.List;

import com.elliothutchinson.cms.domain.File;

public class FileDto {

    private Long id;
    private String filename;
    private Long articleId;
    private String articleTitle;

    private List<ForeignEntity> articles;

    public FileDto() {
    }

    public FileDto(File file) {
        this.id = file.getId();
        this.filename = file.getFilename();

        if (file.getArticle() != null) {
            this.articleId = file.getArticle().getId();
            this.articleTitle = file.getArticle().getTitle();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public List<ForeignEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<ForeignEntity> articles) {
        this.articles = articles;
    }
}
