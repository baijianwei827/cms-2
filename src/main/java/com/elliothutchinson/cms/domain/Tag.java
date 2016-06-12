package com.elliothutchinson.cms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags", cascade = CascadeType.MERGE)
    List<Article> articles = new ArrayList<>();

    protected Tag() {
    }

    public Tag(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        article.getTags().add(this);
        getArticles().add(article);
    }

    public void removeArticle(Article article) {
        article.getTags().remove(this);
        getArticles().remove(article);
    }

    public void removeArticles() {
        for (Article a : new ArrayList<>(articles)) {
            removeArticle(a);
        }
    }

    @Override
    public String toString() {
        return String.format("Tag[id=%d, title='%s']", id, title);
    }
}
