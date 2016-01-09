package com.app.notify.model;

import java.net.ContentHandler;

import com.app.notify.base.BaseModel;
public class Comment extends BaseModel{
	
	// model columns
		public final static String COL_ID = "id";
		public final static String COL_CONTENT = "content";
		public final static String COL_CUSTOMERNAME = "customername";
		public final static String COL_UPTIME = "uptime";
		
		private  String id;
		private  String content;
		private String customername;
		private String uptime;
		public Comment() {
			// TODO Auto-generated constructor stub
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCustomername() {
			return customername;
		}
		public void setCustomername(String customerName) {
			this.customername = customerName;
		}
		public String getUptime() {
			return uptime;
		}
		public void setUptime(String uptime) {
			this.uptime = uptime;
		}

}
