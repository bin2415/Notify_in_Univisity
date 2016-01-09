package com.app.notify.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.PublicKey;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.R.integer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.app.notify.base.BaseAuth;
import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseUi;
import com.app.notify.base.BaseUiAuth;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.app.notify.util.AppUtil;
import com.app.notify.util.IOUtil;
import com.example.notify.R;

public class UiAddActivity extends BaseUiAuth implements Handler.Callback{
	
	private EditText activityContent = null;
	private ImageView activityAddImage = null;
	private Button button_submit = null;
	private ImageView image_picture = null;
	private String filename = getPhotoFileName();
    private int width;
	private static int SCALE = 1;
	
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
	// ����һ���Ե�ǰʱ��Ϊ���Ƶ��ļ�
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
				filename);
    private String app_write_img_path = null;
	
	public boolean handleMessage(Message msg)
	{
		if(msg.what == 1)
			
			{toast("�ϴ�ͼƬ�ɹ�");
			}
		else {
			toast("�ϴ�ͼƬʧ��");
			
		}
		return false;
		
	}
	
	OnClickListener onDialogClick = new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			switch (which) {
			case 0:
				startCamearPicCut(dialog);//��������
				break;
			case 1: 
				startImageCapture(dialog);//����ͼ��
			default:
				break;
			}
		}
	};
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "��ӻ");
		this.setContentView(R.layout.new_activity);
		ImageButton button_back = (ImageButton)this.findViewById(R.id.head_TitleBackBtn);
		button_back.setVisibility(View.GONE);
		
		DisplayMetrics dm = new DisplayMetrics();getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;

		
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		ImageButton button_new_activity = (ImageButton)findViewById(R.id.main_tab_add);
		button_new_activity.setImageResource(R.drawable.icon_add2);
		activityContent = (EditText)findViewById(R.id.new_activity_editText);
		activityAddImage = (ImageView)findViewById(R.id.new_activity_image_add);
		image_picture = (ImageView)findViewById(R.id.new_activity_image_picture);
		button_submit = (Button)findViewById(R.id.new_activity_button_submit);
		
		activityAddImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.new_activity_image_add:
					showDialog();
					break;

				default:
					break;
				}
			}
		});
		
		button_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.new_activity_button_submit:
					if(activityContent.getText().toString().length()>0)
					{
						
						
							HashMap<String, String> urlParams = new HashMap<String, String>();
							urlParams.put("content", activityContent.getText().toString());
							if(app_write_img_path != null)
								{
								List<NameValuePair> files = new ArrayList<NameValuePair>();
								files.add(new BasicNameValuePair("file0", app_write_img_path));
								doTaskAsync(C.task.uploadPicture, C.api.activityCreate, urlParams,files );
								}
							else {
								doTaskAsync(C.task.uploadPicture, C.api.activityCreate,urlParams);
							}
						
					}
					else{
						toast("��д�»����");
					}
					
					break;

				default:
					break;
				}
			}
		});
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage baseMessage)
	{
		super.onTaskComplete(taskId, baseMessage);
		String code = baseMessage.getCode();
		if(code.equals("10000"))
		{
			toast("�ϴ��ɹ�");
		}
		else {
			toast("�ϴ�ʧ��");
		}
		
	}
	 //��ʾ�Ի��򷽷�
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("ѡ���ϴ�ͼƬ")
                .setPositiveButton("����", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	 startCamearPicCut(dialog);
                    }
                })
                .setNegativeButton("���", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    startImageCapture(dialog);}
                }).show();
    }

	private void startCamearPicCut(DialogInterface dialog) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		// ����ϵͳ�����չ���
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		//intent.putExtra("camerasensortype", 2);// ����ǰ������ͷ
		intent.putExtra("autofocus", true);// �Զ��Խ�
		intent.putExtra("fullScreen", false);// ȫ��
		intent.putExtra("showActionIcons", false);
		// ָ������������պ���Ƭ�Ĵ���·��
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
	}
	
	private void startImageCapture(DialogInterface dialog) {
		// TODO Auto-generated method stub
		
		
		dialog.dismiss();
		/*Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);*/
		
		Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		//activity.startActivityForResult(intent, START_ALBUM_CODE);
		 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

             intent.setAction(Intent.ACTION_OPEN_DOCUMENT);

         } else {

             intent.setAction(Intent.ACTION_GET_CONTENT);
         }
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	
	// ʹ��ϵͳ��ǰ���ڼ��Ե�����Ϊ��Ƭ������
		private String getPhotoFileName() {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"'IMG'_yyyyMMdd_HHmmss");
			return dateFormat.format(date) + ".jpg";
		}

		@Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // TODO Auto-generated method stub
			 super.onActivityResult(requestCode, resultCode, data);
	        switch (requestCode) {
	        case PHOTO_REQUEST_TAKEPHOTO:
	        	try {
	        	if(tempFile != null)
	        	{
	        		Uri uri = Uri.fromFile(tempFile);
	        		if(uri!=null)
	        		{
	        			try {
							// get image path
							String path = AppUtil.getImageAbsolutePath(UiAddActivity.this, uri);
							// create bitmap
							Bitmap bm = AppUtil.createBitmap(path, 100, 100);
							if (bm == null) {
								this.toast("can not find img");
							} else {
								image_picture.setImageBitmap(bm);
								app_write_img_path = path;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
	        		}}}
	        	catch (Exception e) {
					// TODO: handle exception
	        		e.printStackTrace();
				}
	        		
	        	
	            break;

	        case PHOTO_REQUEST_GALLERY:
	        	if (data != null) {
	        		Uri uri = data.getData();
					if (uri != null) {
						try {
							// get image path
							String path = AppUtil.getImageAbsolutePath(UiAddActivity.this, uri);
							// create bitmap
							Bitmap bm = AppUtil.createBitmap(path, 100, 100);
							image_picture.setImageBitmap(bm);
							if (bm == null) {
								this.toast("can not find img");
							} else {
								
								app_write_img_path = path;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					
				}
	            break;
	        }
	      

	    }}

		public String getFileName(String pathandname){
			int start=pathandname.lastIndexOf("/");
		    //int end=pathandname.lastIndexOf(".");
		    if (start!=-1) {
		        return pathandname.substring(start+1);	
		    }
		    else {
		        return null;
		    }
		}
		
		/** ����BitmapͼƬ **/
	    public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();
	        Matrix matrix = new Matrix();
	        float scaleWidth = ((float) width / w);
	        float scaleHeight = ((float) height / h);
	        matrix.postScale(scaleWidth, scaleHeight);// ���þ���������Ų�������ڴ����
	        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	        return newbmp;
	    }
	    
	    private Bitmap compressImage(Bitmap image) {  
	    	  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
	        int options = 100;  
	        while ( baos.toByteArray().length / 1024>100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
	            baos.reset();//����baos�����baos  
	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
	            options -= 10;//ÿ�ζ�����10  
	        }  
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
	        return bitmap;  
	    }  
		
	    
	   
}
