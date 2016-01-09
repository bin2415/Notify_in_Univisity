package com.app.notify.base;

import java.security.PublicKey;

import android.graphics.Path.Direction;


//���Ż����ı���
public class C {
	public static final class dir{
		public static final String  base 		= "/sdcard/notify";
		public static final String faces 		= base + "/faces";
		public static final String images		= base + "/images";
	}
	
	public static final class api
	{
		public static final String base		= "http://115.28.66.72:9001"; //�˵�ַ��Ϊ��������ip��ַ
		public static final String indexClub	= "/index/indexClub"; //����Club�û��Ƿ��½
		public static final String indexPerson 	= "/index/indexPerson"; //����Person�û��Ƿ��½
		public static final String loginClub = "/index/loginClub";   //Club�û���½
		public static final String loginPerson = "/index/loginPerson"; //�����û���½
		public static final String logout = "/index/logout";	//�û��ǳ�
		public static final String faceView = "/image/faceView"; //�鿴�û��ӿ�
		//public static final String faceList = "/image/faceList"; 
		public static final String uploadPicture = "/image/uploadPicture"; //�ϴ��ͼƬ
		public static final String uploadFace = "/image/uploadFace"; //�ϴ�ͷ��ͼƬ
		public static final String activityList = "/activity/activityList"; //���activity�б�
		public static final String activityView = "/activity/activityView"; //��þ����activity����
		public static final String activityCreate = "/activity/activityCreate"; //�½�activity
		public static final String activityListPersonal = "/activity/activityListPersonal";//�Լ��������Ļ�ӿ�
		public static final String commentList = "/comment/commentList"; //���һ��activity�������б�
		public static final String commentCreate = "/comment/commentCreate"; //д����
		public static final String 	customerClubList = "/customerClub/customerClubList"; //Club�û��б�
		public static final String 	customerClubView = "/customerClub/customerClubView"; //�鿴Club�û�����Ϣ
		public static final String 	customerClubEdit = "/customerClub/customerClubEdit"; //�޸�Club�û�����Ϣ
		public static final String 	customerClubCreate = "/customerClub/customerClubCreate";//�½�һ��Club�û�
		public static final String 	customerPersonList = "/customerPerson/customerPersonList";//Person�û��б�
		public static final String 	customerPersonView = "/customerPerson/customerPersonView";//�鿴Person����Ϣ
		public static final String 	customerPersonEdit = "/customerPerson/customerPersonEdit";//д��Person����Ϣ
		public static final String 	customerPersonCreate = "/customerPerson/customerPersonCreate";//�½�һ��Person���û�
		public static final String  likeList = "/like/likeList";//�鿴һ��activity���޵��б�
		public static final String likeCreate = "/like/likeCreate";//��һ��
		public static final String  partList = "/part/partList";//�鿴һ��activity�Ĳ����б�
		public static final String partPersonal = "/part/partPersonal";//�鿴�Լ����μӵ�activity���б�
		public static final String partCreate = "/part/partCreate";//����
		
	}
	
	//task��ţ�����Ļ�û���
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
		public static final String network			= "�������";
		public static final String message			= "��Ϣ����";
		public static final String jsonFormat		= "��Ϣ��ʽ����";
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
