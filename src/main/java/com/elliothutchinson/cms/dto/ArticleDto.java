package com.elliothutchinson.cms.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.elliothutchinson.cms.domain.Article;

public class ArticleDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private Long authorId;
    private String authorUsername;
    private Long sectionId;
    private String sectionTitle;

    private List<ForeignEntity> authors;
    private List<ForeignEntity> sections;

    public ArticleDto() {
    }

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.dateCreated = article.getDateCreated();
        this.dateModified = article.getDateModified();
        this.authorId = article.getAuthor().getId();
        this.authorUsername = article.getAuthor().getUsername();

        if (article.getSection() != null) {
            this.sectionId = article.getSection().getId();
            this.sectionTitle = article.getSection().getTitle();
        }
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

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<ForeignEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(List<ForeignEntity> authors) {
        this.authors = authors;
    }

    public List<ForeignEntity> getSections() {
        return sections;
    }

    public void setSections(List<ForeignEntity> sections) {
        this.sections = sections;
    }
}
