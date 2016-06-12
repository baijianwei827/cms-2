package com.elliothutchinson.cms.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.elliothutchinson.cms.domain.Comment;

public class CommentDto {
    
    private Long id;
    private String name;
    private String email;
    private boolean authorResponse;
    private String content;
    private LocalDateTime dateCreated;
    private Long articleId;
    private String articleTitle;

    private List<ForeignEntity> articles;
    
    public CommentDto() {
    }
    
    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.name = comment.getName();
        this.email = comment.getEmail();
        this.authorResponse = comment.isAuthorResponse();
        this.content = comment.getContent();
        this.dateCreated = comment.getDateCreated();
        this.articleId = comment.getArticle().getId();
        this.articleTitle = comment.getArticle().getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthorResponse() {
        return authorResponse;
    }

    public void setAuthorResponse(boolean authorResponse) {
        this.authorResponse = authorResponse;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<ForeignEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<ForeignEntity> articles) {
        this.articles = articles;
    }
}
