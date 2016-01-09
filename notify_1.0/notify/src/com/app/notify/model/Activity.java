package com.app.notify.model;

import com.app.notify.base.BaseModel;
public class Activity extends BaseModel {
	
	public final static String COL_ID = "id";
	public final static String COL_PICTURE = "picture";
	public final static String COL_TITLE = "title";
	public final static String COL_FACE = "face";
	public final static String COL_CONTENT = "content";
	public final static String COL_PERSON = "person";
	public final static String COL_COMMENT = "comment";
	public final static String COL_LIKE = "like";
	public final static String COL_PART = "part";
	public final static String COL_UPTIME = "uptime";
	public final static String COL_PERSONAL = "personal";
	
	private String id;
	private String picture;
	private String title;
	private String face;
	private String content;
	private String person;
	private String comment;
	private String like;
	private String part;
	private String uptime;
	private String personal;

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFace() {
		return face;
	}


	public void setFace(String face) {
		this.face = face;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getPerson() {
		return person;
	}


	public void setPerson(String person) {
		this.person = person;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getLike() {
		return like;
	}


	public void setLike(String like) {
		this.like = like;
	}


	public String getPart() {
		return part;
	}


	public void setPart(String part) {
		this.part = part;
	}


	public String getUptime() {
		return uptime;
	}


	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getPersonal()
	{
		return personal;
	}

	public void setPersonal(String p)
	{
		personal = p;
	}
	public Activity() {
		// TODO Auto-generated constructor stub
	}

}
