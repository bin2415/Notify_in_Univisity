package com.app.notify.ui;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.notify.base.BaseAuth;
import com.app.notify.base.BaseHandler;
import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseTask;
import com.app.notify.base.BaseUi;
import com.app.notify.base.BaseUiAuth;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.app.notify.model.CustomerClub;
import com.app.notify.model.CustomerPerson;
import com.app.notify.util.AppCache;
import com.app.notify.util.AppUtil;
import com.example.notify.R;

public class UiPerson extends BaseUiAuth{
	
	private ImageView image_face = null;
	private TextView text_name = null;
	private Button change_face_button = null;
	private Button party_button = null;
	private Button my_parties_button = null;
	private Button logout_button = null;
	//private Button my_parties_per_button = null;
	
	private Bitmap bm = null;
	private String filename = getPhotoFileName();
	private String faceurl = null;
	private String customerid = null;
	private String personal = null;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	// 创建一个以当前时间为名称的文件
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
				filename);
    private String app_write_img_path = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this,"个人信息");
		setContentView(R.layout.personnal_info);
		
		ImageButton button_back = (ImageButton)findViewById(R.id.head_TitleBackBtn);
		button_back.setVisibility(View.GONE);
		this.setHandler(new PersonHandler(this));
		
		
		ImageButton ib = (ImageButton)findViewById(R.id.main_tab_person);
		ib.setImageResource(R.drawable.icon_person2);
	    image_face = (ImageView)this.findViewById(R.id.image_face);
	    text_name = (TextView)this.findViewById(R.id.personal_info_text_person);
	    change_face_button = (Button)this.findViewById(R.id.personal_info_changeFace_button);
	    party_button = (Button)this.findViewById(R.id.personnal_info_activity_par_button);
	    my_parties_button = (Button)this.findViewById(R.id.personnal_info_my_activity_button);
	    logout_button = (Button)this.findViewById(R.id.button_logout);
	    //my_parties_per_button = (Button)this.findViewById(R.id.personnal_info_activity_par_button_per);
	    OnClickListener mOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.personal_info_changeFace_button:
					showChoiceDialog();
					break;
				case R.id.personnal_info_activity_par_button:
					overlay(UiMyPartedActivity.class);
					break;
				case R.id.personnal_info_my_activity_button:
					Bundle bundd = new Bundle();
					bundd.putString("personal", personal);
					overlay(UiMyActivity.class,bundd);
					break;
				case R.id.button_logout:
					showLogoutDialog();
					break;
				default:
					break;
				}
			}
		};
		change_face_button.setOnClickListener(mOnClickListener);
		party_button.setOnClickListener(mOnClickListener);
		my_parties_button.setOnClickListener(mOnClickListener);
		logout_button.setOnClickListener(mOnClickListener);
		
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		String url = null;
		if(BaseAuth.isClubLogin())
			{url = C.api.indexClub;
			personal = "1";
			}
		else {
			if (BaseAuth.isPersonLogin()) {
				personal = "0";
				url = C.api.indexPerson;
			}
		}
		doTaskAsync(C.task.index, url);
	}
	private void showLogoutDialog()
	{
		new AlertDialog.Builder(this)
		.setTitle("确定退出？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				doLogout();
				forward(UILogup.class);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).show();
		
	}
	private void showChoiceDialog()
	{
		new AlertDialog.Builder(this)
		.setTitle("更改头像")
		.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startCamearPicCut(dialog);
			}
		}
		)
		.setNegativeButton("相册", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startImageCapture(dialog);
			}
		}).show();
	}
	
	private void startCamearPicCut(DialogInterface dialog) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		//intent.putExtra("camerasensortype", 2);// 调用前置摄像头
		intent.putExtra("autofocus", true);// 自动对焦
		intent.putExtra("fullScreen", false);// 全屏
		intent.putExtra("showActionIcons", false);
		// 指定调用相机拍照后照片的储存路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
	}
	
	private void startImageCapture(DialogInterface dialog) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
        case PHOTO_REQUEST_TAKEPHOTO:
        	try {
        	if(tempFile != null)
        	{
        		Uri uri = Uri.fromFile(tempFile);
        		app_write_img_path = tempFile.getAbsolutePath();
        		bm = AppUtil.createBitmap(app_write_img_path, 1, 1);
        		image_face.setImageBitmap(bm);
        		if (bm == null) {
					this.toast("can not find img");
				} else {
					String url = null;
					if(BaseAuth.isClubLogin())
					{
						url = C.api.customerClubEdit;
						
					}
					else {
						if(BaseAuth.isPersonLogin())
						{
							url = C.api.customerPersonEdit;
						}
					}
					HashMap<String, String> urlParams = new HashMap<String, String>();
					urlParams.put("key", "face");
					urlParams.put("val", "face");
					List<NameValuePair> files = new ArrayList<NameValuePair>();
					files.add(new BasicNameValuePair("file0", app_write_img_path));
					doTaskAsync(C.task.uploadFace, url,urlParams,files);
					//image_face.setImageBitmap(bm);
					
				}
        	}
			} catch (Exception e) {
				// TODO: handle exception
			}
        	
            break;

        case PHOTO_REQUEST_GALLERY:
            if (data != null) {
            	try {
            		
            		if (data != null) {
        				Uri uri = data.getData();
        				if (uri != null) {
        					try {
        						// get image path
        						String[] filePathColumn = { MediaStore.Images.Media.DATA };
        						Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        						cursor.moveToFirst();
        						String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        						cursor.close();
        						// debug path
        						this.toast(path);
        						// create bitmap
        						bm = AppUtil.createBitmap(path, 1, 1);
        						image_face.setImageBitmap(bm);
        						if (bm == null) {
        							this.toast("can not find img");
        						} else {
        							String url = null;
        							if(BaseAuth.isClubLogin())
        							{
        								url = C.api.customerClubEdit;
        								
        							}
        							else {
										if(BaseAuth.isPersonLogin())
										{
											url = C.api.customerPersonEdit;
										}
									}
        							HashMap<String, String> urlParams = new HashMap<String, String>();
        							urlParams.put("key", "face");
        							urlParams.put("val", "face");
        							List<NameValuePair> files = new ArrayList<NameValuePair>();
        							files.add(new BasicNameValuePair("file0", path));
        							doTaskAsync(C.task.uploadFace, url,urlParams,files);
        							//image_face.setImageBitmap(bm);
        							//app_write_img_path = path;
        						}
        					} catch (Exception e) {
        						e.printStackTrace();
        					}
        				}
        			}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
            	
			} 
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		
		switch (taskId) {
		case C.task.index:
			if(BaseAuth.isClubLogin())
			{
				try {
					CustomerClub customerClub = (CustomerClub)message.getResult("CustomerClub");
					personal = "1";
					customerid = customerClub.getId();
					text_name.setText(customerClub.getName());
					faceurl = customerClub.getFaceUrl();
					if(faceurl.length()>0)
					{
						loadImage(faceurl);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
			else {
				if(BaseAuth.isPersonLogin())
				{
					try {
						CustomerPerson customerPerson = (CustomerPerson)message.getResult("CustomerPerson");
						personal = "0";
						customerid = customerPerson.getId();
						text_name.setText(customerPerson.getName());
						faceurl = customerPerson.getFaceUrl();
						if(faceurl.length()>0)
						{
							 loadImage(faceurl);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
			break;
		case C.task.uploadFace:
			String code = message.getCode();
			if(code.equals("10000"))
			{
				//image_face.setImageBitmap(bm);
				toast("更换头像成功");
			}
			break;
		default:
			break;
		}
		
	}
	// 使用系统当前日期加以调整作为照片的名称
			private String getPhotoFileName() {
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"'IMG'_yyyyMMdd_HHmmss");
				return dateFormat.format(date) + ".jpg";
			}
			
			private class PersonHandler extends BaseHandler {
				public PersonHandler(BaseUi ui) {
					super(ui);
				}
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					try {
						switch (msg.what) {
							case BaseTask.LOAD_IMAGE:
								Bitmap face = AppCache.getImage(faceurl);
								
								image_face.setImageBitmap(face);
								break;
							default:
								break;
						}
					} catch (Exception e) {
						e.printStackTrace();
						ui.toast(e.getMessage());
					}
				}
			}
}
