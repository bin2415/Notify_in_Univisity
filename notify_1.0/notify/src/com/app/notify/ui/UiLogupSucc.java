package com.app.notify.ui;

import java.util.HashMap;

import com.app.notify.base.BaseAuth;
import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.app.notify.model.CustomerClub;
import com.app.notify.model.CustomerPerson;
import com.example.notify.R;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class UiLogupSucc extends BaseUi{

	private Button button_enter = null;
	private int personal = 0;
	private String name = null;
	private String school = null;
	private String pass = null;
	Bundle params = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "×¢²á³É¹¦");
		setContentView(R.layout.sign_up_succ);
		
		ImageButton button_back = (ImageButton)this.findViewById(R.id.head_TitleBackBtn);
		button_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFinish();
			}
		});
		params = this.getIntent().getExtras();
		personal = params.getInt("personal");
		
		button_enter = (Button)this.findViewById(R.id.sign_up_succ_enter);
		
		OnClickListener mOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(personal == 0)
				{
					doTaskLoginPerson();
				}
				else
				{
					doTaskLoginClub();
				}
				
			}
		};
		
		button_enter.setOnClickListener(mOnClickListener);
		
		
	}
	private void doTaskLoginPerson()
	{
		app.setLong(System.currentTimeMillis());
		name = params.getString("sid");
		school = params.getString("school");
		pass = params.getString("pass");
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("studentid", name);
		urlParams.put("pass", pass);
		urlParams.put("school", school);
		doTaskAsync(C.task.loginPerson, C.api.loginPerson,urlParams);
	}
	private void doTaskLoginClub()
	{
		app.setLong(System.currentTimeMillis());
		name = params.getString("name");
		school = params.getString("school");
		pass = params.getString("pass");
		
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("name", name);
		urlParams.put("pass", pass);
		urlParams.put("school", school);
		doTaskAsync(C.task.loginClub, C.api.loginClub,urlParams);
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case C.task.loginPerson:
			CustomerPerson customerPerson = null;
			try {
				customerPerson = (CustomerPerson) message.getResult("CustomerPerson");
				if(customerPerson.getName() != null)
				{
					BaseAuth.setCustomerPerson(customerPerson);
					BaseAuth.setPersonLogin(true);
				}
				else {
					BaseAuth.setCustomerPerson(customerPerson);
					BaseAuth.setPersonLogin(false);
					toast("µÇÂ¼Ê§°Ü£¬ÇëÖØÐÂµÇÂ¼");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			long startTime = app.getLong();
			long loginTime = System.currentTimeMillis()-startTime;
			Log.w("LoginTime", Long.toString(loginTime));
			if(BaseAuth.isPersonLogin())
			{
				forward(UiIndex.class);
			}
			break;
		case C.task.loginClub:
			CustomerClub customerClub = null;
			try {
				customerClub = (CustomerClub) message.getResult("CustomerClub");
				if(customerClub.getName() != null)
				{
					BaseAuth.setCustomerClub(customerClub);
					BaseAuth.setClubLogin(true);
				}
				else {
					BaseAuth.setCustomerClub(customerClub);
					BaseAuth.setClubLogin(false);
					toast("µÇÂ¼Ê§°Ü£¬ÇëÖØÐÂµÇÂ¼");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			long startTime2 = app.getLong();
			long loginTime2 = System.currentTimeMillis()-startTime2;
			Log.w("LoginTime", Long.toString(loginTime2));
			if(BaseAuth.isClubLogin())
			{
				forward(UiIndex.class);
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onNetworkError (int taskId) {
		super.onNetworkError(taskId);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
