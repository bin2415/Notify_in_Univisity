package com.app.notify.model;

import com.app.notify.base.BaseModel;

public class Part extends BaseModel{

	
	public final static String COL_ID = "id";
	public final static String COL_CUSTOMERNAME = "customername";
	public final static String COL_UPTIME = "uptime";
	
	
	private String id;
	private String customername;
	private String uptime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public Part() {
		// TODO Auto-generated constructor stub
	}

}
