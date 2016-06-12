package com.elliothutchinson.cms.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    @Column(length = 10000)
    private String content;

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL)
    private Archive archive;

    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL)
    private Feature feature;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<File> files;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = "article_id"), 
            inverseJoinColumns = @JoinColumn(name = "tag_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "article_id", "tag_id" }))
    private List<Tag> tags = new ArrayList<>();

    protected Article() {
    }

    public Article(String title, String content, Author author, Section section) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.section = section;
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

    @JsonIgnore
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @JsonIgnore
    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    @JsonIgnore
    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @JsonIgnore
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @JsonIgnore
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        tag.getArticles().add(this);
        getTags().add(tag);
    }

    public void removeTag(Tag tag) {
        tag.getArticles().remove(this);
        getTags().remove(tag);
    }

    public void removeTags() {
        for (Tag t : new ArrayList<>(tags)) {
            removeTag(t);
        }
    }

    @Override
    public String toString() {
        return String.format("Article[id=%d, title='%s', dateCreated='%s', dateModifed='%s']", id, title, dateCreated,
                dateModified);
    }

    @PostPersist
    public void createArchiveEntry() {
        archive = new Archive(dateCreated.getMonthValue(), dateCreated.getYear(), this);
    }
}
