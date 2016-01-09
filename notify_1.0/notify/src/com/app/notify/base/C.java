package com.app.notify.base;

import java.security.PublicKey;

import android.graphics.Path.Direction;


//存着基本的变量
public class C {
	public static final class dir{
		public static final String  base 		= "/sdcard/notify";
		public static final String faces 		= base + "/faces";
		public static final String images		= base + "/images";
	}
	
	public static final class api
	{
		public static final String base		= "http://115.28.66.72:9001"; //此地址改为服务器的ip地址
		public static final String indexClub	= "/index/indexClub"; //测试Club用户是否登陆
		public static final String indexPerson 	= "/index/indexPerson"; //测试Person用户是否登陆
		public static final String loginClub = "/index/loginClub";   //Club用户登陆
		public static final String loginPerson = "/index/loginPerson"; //个人用户登陆
		public static final String logout = "/index/logout";	//用户登出
		public static final String faceView = "/image/faceView"; //查看用户接口
		//public static final String faceList = "/image/faceList"; 
		public static final String uploadPicture = "/image/uploadPicture"; //上传活动图片
		public static final String uploadFace = "/image/uploadFace"; //上传头像图片
		public static final String activityList = "/activity/activityList"; //获得activity列表
		public static final String activityView = "/activity/activityView"; //获得具体的activity内容
		public static final String activityCreate = "/activity/activityCreate"; //新建activity
		public static final String activityListPersonal = "/activity/activityListPersonal";//自己所发布的活动接口
		public static final String commentList = "/comment/commentList"; //获得一个activity的评论列表
		public static final String commentCreate = "/comment/commentCreate"; //写评论
		public static final String 	customerClubList = "/customerClub/customerClubList"; //Club用户列表
		public static final String 	customerClubView = "/customerClub/customerClubView"; //查看Club用户的信息
		public static final String 	customerClubEdit = "/customerClub/customerClubEdit"; //修改Club用户的信息
		public static final String 	customerClubCreate = "/customerClub/customerClubCreate";//新建一个Club用户
		public static final String 	customerPersonList = "/customerPerson/customerPersonList";//Person用户列表
		public static final String 	customerPersonView = "/customerPerson/customerPersonView";//查看Person的信息
		public static final String 	customerPersonEdit = "/customerPerson/customerPersonEdit";//写个Person的信息
		public static final String 	customerPersonCreate = "/customerPerson/customerPersonCreate";//新建一个Person的用户
		public static final String  likeList = "/like/likeList";//查看一个activity的赞的列表
		public static final String likeCreate = "/like/likeCreate";//赞一个
		public static final String  partList = "/part/partList";//查看一个activity的参与列表
		public static final String partPersonal = "/part/partPersonal";//查看自己所参加的activity，列表
		public static final String partCreate = "/part/partCreate";//参与活动
		
	}
	
	//task编号，具体的还没想好
	public static final class task {
		public static final int loginPerson = 1001;
		public static final int loginClub = 1002;
		public static final int logupPerson = 1003;
		public static final int logupClub = 1004;
		public static final int activityListPersonal = 1005;
		public static final int activityListClub = 1006;
		public static final int createPart = 1007;
		public static final int activityView = 1008;
		public static final int commentList = 1009;
		public static final int partList = 1010;
		public static final int createComment = 1011;
		public static final int uploadPicture = 1012;
		public static final int index = 1013;
		public static final int uploadFace = 1014;
		public static final int myPartedActivity = 1015;
		public static final int myActivity = 1016;
		public static final int activityListClubAdd = 1017;
		public static final int activityListPersonAdd = 1018;
		public static final int myPartedActivityAdd = 1019;
		public static final int myActivityAdd = 1020;
	}
	
	public static final class err {
		public static final String network			= "网络错误";
		public static final String message			= "消息错误";
		public static final String jsonFormat		= "消息格式错误";
	}
	

	public static final class intent {
		public static final class action {
			public static final String EDITTEXT		= "com.app.notify.EDITTEXT";
			public static final String EDITBLOG		= "com.app.notify.EDITBLOG";
		}
	}
	
	public static final class action {
		public static final class edittext {
			public static final int CONFIG			= 2001;
			public static final int COMMENT			= 2002;
		}
	}
}
