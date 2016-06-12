package com.elliothutchinson.cms.dto;

import com.elliothutchinson.cms.domain.Archive;

public class ArchiveDto {
    
    private Long id;
    private Integer month;
    private Integer year;
    private Long articleId;
    private String articleTitle;
    
    public ArchiveDto() {
    }
    
    public ArchiveDto(Archive archive) {
        this.id = archive.getId();
        this.month = archive.getMonth();
        this.year = archive.getYear();
        this.articleId = archive.getArticle().getId();
        this.articleTitle = archive.getArticle().getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
}
