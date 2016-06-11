package com.elliothutchinson.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private String filename;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private static final String ROOT = "src/main/resources/media/";

    protected File() {
    }

    public File(String filename, Article article) {
        this.filename = filename;
        this.article = article;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public static String getRoot() {
        return ROOT;
    }

    @Override
    public String toString() {
        return String.format("File[id=%d, filename='%s']", id, filename);
    }
}
