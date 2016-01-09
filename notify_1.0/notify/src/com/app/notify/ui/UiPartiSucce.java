package com.app.notify.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.app.notify.dialog.BasicDialog;
import com.app.notify.model.Part;
import com.example.notify.R;

public class UiPartiSucce extends BaseUi {
	
	private ImageView image_share = null;
	private ImageView image_phone = null;
	private TextView text_parti = null;
	private Bundle bundle = null;
	private String contact = null;
	private String activityId = null;
	private String content = null;
	private BasicDialog dialog;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "报名成功");
		setContentView(R.layout.parti_succe);
		
		ImageButton button_back = (ImageButton)this.findViewById(R.id.head_TitleBackBtn);
		button_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFinish();
			}
		});
		bundle = this.getIntent().getExtras();
		contact = bundle.getString("contact");
		activityId = bundle.getString("activityId");
		content = bundle.getString("content");
		
		image_share = (ImageView)this.findViewById(R.id.image_share);
		image_phone = (ImageView)this.findViewById(R.id.image_phone);
		text_parti = (TextView)this.findViewById(R.id.parti_succe_text_person);
		
		Bundle params = new Bundle();
		params.putString("text", "联系方式:"+contact);
		
		dialog = new BasicDialog(this, params);
		OnClickListener mClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.image_share:
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
					intent.putExtra(Intent.EXTRA_TEXT, "我要分享一个活动："+content);
					startActivity(Intent.createChooser(intent, "请选择"));
					break;
				case R.id.image_phone:
					showDialog();
					break;
				default:
					break;
				}
			}
		};
		image_phone.setOnClickListener(mClickListener);
		image_share.setOnClickListener(mClickListener);
		
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("activityId", activityId);
		urlParams.put("pageId","0");
		doTaskAsync(C.task.partList, C.api.partList,urlParams);
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case C.task.partList:
			try {
			final ArrayList<Part> partList = (ArrayList<Part>) message.getResultList("Part");
			String partPerson = "";
			for(Part part : partList)
			{
				partPerson = partPerson + part.getCustomername() + ",";
			}
			text_parti.setText(partPerson);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;

		default:
			break;
		}
	}
	
	private void showDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("联系方式");
		builder.setMessage(contact);
		builder.setIcon(R.drawable.phone);
		builder.setPositiveButton("确定", null);
		builder.show();
	}
	@Override
	public void onNetworkError (int taskId) {
		super.onNetworkError(taskId);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.forward(UiIndex.class);
		}
		return super.onKeyDown(keyCode, event);
	}
	

}
