package com.elliothutchinson.cms.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Author {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(unique=true)
	private String username;
	
	@NotNull
	private String password;
	
	private String email;
	private boolean admin;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="author", cascade=CascadeType.REMOVE)
	private List<Article> articles;
	
	protected Author() {}
	
	public Author(String username, String password, String email, boolean admin) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.admin = admin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return String.format(
				"Author[id=%d, username='%s', email='%s', admin='%s']",
				id, username, email, admin);
	}
}
