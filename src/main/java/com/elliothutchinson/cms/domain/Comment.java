package com.elliothutchinson.cms.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    
    @Email
    private String email;
    
    private boolean authorResponse;

    @NotNull
    @Column(length = 10000)
    private String content;

    @CreatedDate
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment() {
    }

    public Comment(String name, String email, boolean authorResponse, String content, Article article) {
        this.name = name;
        this.email = email;
        this.authorResponse = authorResponse;
        this.content = content;
        this.article = article;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return String.format("Comment[id=%d, name='%s', authorResponse='%s', content='%s']", id, name, authorResponse,
                content);
    }
}
