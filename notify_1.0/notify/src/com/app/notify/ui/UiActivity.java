package com.app.notify.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.notify.base.BaseHandler;
import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseTask;
import com.app.notify.base.BaseUi;
import com.app.notify.base.BaseUiAuth;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.app.notify.list.CommentList;
import com.app.notify.model.Activity;
import com.app.notify.model.Comment;
import com.app.notify.model.CustomerClub;
import com.app.notify.model.CustomerPerson;
import com.app.notify.util.AppCache;
import com.example.notify.R;

public class UiActivity extends BaseUi{
	
	private String activityId = null;
	private String contact = null;
	private String personal = null;
	private ImageView image_face = null;
	private TextView text_person = null;
	private TextView text_uptime = null;
	private TextView textContent = null;
	private ImageView image_picture = null;
	private ListView comment_list = null;
	private Button button_comment = null;
	private Button button_part = null;
	private String faceImageUrl = null;
	private String pictureImageUrl = null;
	private String content = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "活动具体内容");
		this.setContentView(R.layout.tpl_list_activity);
		
		this.setHandler(new ActivityHandler(this));
		
 		comment_list = (ListView)findViewById(R.id.tpl_list_activity_list_view);
		ImageButton button_back = (ImageButton)this.findViewById(R.id.head_TitleBackBtn);
		button_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFinish();
			}
		});
		Bundle params = this.getIntent().getExtras();
		activityId = params.getString("activityId");
		personal = params.getString("personal");
		
		button_comment = (Button)this.findViewById(R.id.tpl_list_activity_comment);
		button_part = (Button)this.findViewById(R.id.tpl_list_activity_part);
		
			OnClickListener mClickListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switch (v.getId()) {
					case R.id.tpl_list_activity_comment:
						Bundle p1 = new Bundle();
						p1.putString("activityId", activityId);
						overlay(UiComment.class,p1);
						break;
					case R.id.tpl_list_activity_part:
						HashMap<String, String> urlParams = new HashMap<String, String>();
						urlParams.put("activityId", activityId);
						doTaskAsync(C.task.createPart, C.api.partCreate,urlParams);
					default:
						break;
					}
				}
			};
			
			button_comment.setOnClickListener(mClickListener);
			button_part.setOnClickListener(mClickListener);
			
			//Activity的信息
			
			HashMap<String, String> activityParams = new HashMap<String, String>();
			activityParams.put("activityId",activityId);
			activityParams.put("personal", personal);
			this.doTaskAsync(C.task.activityView, C.api.activityView,activityParams);
		
	}

	@Override
	public void onStart()
	{
		super.onStart();
		HashMap<String, String> commentParams = new HashMap<String, String>();
		commentParams.put("activityId", activityId);
		commentParams.put("pageId", "0");
		this.doTaskAsync(C.task.commentList, C.api.commentList,commentParams);	
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case C.task.activityView:
			try {
				Activity activity = (Activity)message.getResult("Activity");
				image_face = (ImageView)findViewById(R.id.tpl_list_activity_face);
				image_picture = (ImageView)findViewById(R.id.tpl_list_activity_item_image);
				textContent = (TextView)findViewById(R.id.tpl_list_activity_text_content);
			    text_person = (TextView)findViewById(R.id.tpl_list_activity_person);
				text_uptime = (TextView)findViewById(R.id.tpl_list_activity_uptime);
				pictureImageUrl = activity.getPicture();
				button_comment.setText(activity.getComment());
				button_part.setText(activity.getPart());
				int length = pictureImageUrl.length();
				if(pictureImageUrl.length()==0)
				{
					image_picture.setVisibility(View.GONE);
				}
				else {
					loadPicture(pictureImageUrl);
				}
				textContent.setText(activity.getContent());
				text_uptime.setText(activity.getUptime());
				content = activity.getContent();
				if(personal.equals("0"))
				{
					CustomerPerson customerPerson = (CustomerPerson)message.getResult("CustomerPerson");
					//String name = customerPerson.getName();
					contact = customerPerson.getContact();
					text_person.setText(customerPerson.getName());
					String face = customerPerson.getFace();
					faceImageUrl = customerPerson.getFace();
					loadImage(faceImageUrl);
				}
				else
				{
					CustomerClub customerClub = (CustomerClub)message.getResult("CustomerClub");
					text_person.setText(customerClub.getName());
					contact = customerClub.getContact();
					String face = customerClub.getFaceUrl();
					faceImageUrl = customerClub.getFaceUrl();
					loadImage(faceImageUrl);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//toast(e.getMessage());
			}
			break;
		case C.task.commentList:
			try {
				@SuppressWarnings("unchecked")
				final ArrayList<Comment> commentList = (ArrayList<Comment>)message.getResultList("Comment");
				CommentList commentListAdapter = new CommentList(this, commentList);
				comment_list.setAdapter(commentListAdapter);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//toast(e.getMessage());
			}
			break;
		case C.task.createPart:
			String code = message.getCode();
			if(code.equals("10000"))
			{
				Bundle params = new Bundle();
				params.putString("contact", contact);
				params.putString("activityId", activityId);
				params.putString("content", content);
				overlay(UiPartiSucce.class,params);
			}
			else {
				if(code.equals("14005"))
				{
					toast("你已经报过名了");
				}
			}
		default:  
			break;
		}
	}
	private class ActivityHandler extends BaseHandler {
		public ActivityHandler(BaseUi ui) {
			super(ui);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
					case BaseTask.LOAD_IMAGE:
						Bitmap face = AppCache.getImage(faceImageUrl);
						image_face.setImageBitmap(face);
						break;
					case BaseTask.LOAD_PICTURE:
						Bitmap picture = AppCache.getImage(pictureImageUrl);
						image_picture.setImageBitmap(picture);
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
