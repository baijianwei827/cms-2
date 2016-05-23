package com.elliothutchinson.cms.domain;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Archive {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private int month;
	private int year;
	
	@OneToOne
	@JoinColumn(name="article_id", unique=true, nullable=false)
	private Article article;
	
	protected Archive() {}
	
	public Archive(int month, int year, Article article) {
		this.month = month;
		this.year = year;
		this.article = article;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
	public String getMonthName() {
		return Month.of(month).getDisplayName(TextStyle.FULL, Locale.US);
	}
}
