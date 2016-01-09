package com.app.notify.model;

import com.app.notify.base.BaseModel;
public class CustomerPerson extends BaseModel{
	public final static String COL_ID = "id";
	public final static String COL_SID = "sid";
	public final static String COL_NAME = "name";
	public final static String COL_SCHOOL = "school";
	public final static String COL_CONTACT = "contact";
	public final static String COL_ACTIVITYCOUNT = "activitycount";
	public final static String COL_FACE = "face";
	public final static String COL_FACEURL = "faceUrl";
	public final static String COL_UPTIME = "uptime";
	
	private String id;
	private String sid;
	private String name;
	private String school;
	private String contact;
	private String activitycount;
	private String face;
	private String faceUrl;
	private String uptime;
	
	// default is no login
		private boolean isLogin = false;
		
		// single instance for login
		static private CustomerPerson customer = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getActivitycount() {
		return activitycount;
	}
	public void setActivitycount(String activitycount) {
		this.activitycount = activitycount;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getFaceUrl() {
		return faceUrl;
	}
	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	
	static public CustomerPerson getInstance () {
		if (CustomerPerson.customer == null) {
			CustomerPerson.customer = new CustomerPerson();
		}
		return CustomerPerson.customer;
	}
	
	public Boolean getLogin () {
		return this.isLogin;
	}
	
	public void setLogin (boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	
	
	
	

	
	
	
	
	
	
	

}
