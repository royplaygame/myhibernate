package com.hy.ly.po;

import java.sql.Date;

public class News {

	private Integer id;
	private String title;
	private String author;
	private Date publishDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public News(String title, String author, Date publishDate) {
		super();
		this.title = title;
		this.author = author;
		this.publishDate = publishDate;
	}
	public News() {
		super();
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", author=" + author + ", publishDate=" + publishDate + "]";
	}
	
	
}
