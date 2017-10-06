package com.hy.ly.po;

import java.sql.Blob;
import java.sql.Date;

public class News {

	private Integer id;
	private String title;
	private String author;
	private Date publishDate;
	private String desc;

	// 大文本
	private String content;
	
	//图片
	private Blob image;

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

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
	

	public News(Integer id, String title, String author, Date publishDate, String desc, String content, Blob image) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishDate = publishDate;
		this.desc = desc;
		this.content = content;
		this.image = image;
	}

	public News() {
		super();
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", author=" + author + ", publishDate=" + publishDate + "]";
	}

}
