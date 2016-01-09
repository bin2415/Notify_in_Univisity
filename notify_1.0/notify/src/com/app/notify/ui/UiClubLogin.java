package com.app.notify.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.app.notify.base.BaseAuth;
import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C;
import com.app.notify.model.CustomerClub;
import com.example.notify.R;

public class UiClubLogin extends BaseUi {
	
	private EditText text_name;
	private EditText text_pass;
	private EditText text_school;
	
	private Button button_signin;
	private Button button_signup;
	private Button button_signin_per;
	private String name,pass,school;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_org);
		
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		text_name = (EditText)findViewById(R.id.sign_up_org_name);
		text_school = (EditText)findViewById(R.id.sign_up_org_school);
		text_pass = (EditText)findViewById(R.id.sign_up_org_pass);
		
		button_signin = (Button)findViewById(R.id.sign_up_org_signin);
		button_signup = (Button)findViewById(R.id.sign_up_signup);
		button_signin_per = (Button)findViewById(R.id.sign_up_org_per_signin);
		
		OnClickListener mOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.sign_up_org_signin:
					doLoginClubTask();
					break;
				case R.id.sign_up_signup:
					forward(UILogup.class);
					break;
				case R.id.sign_up_org_per_signin:
					forward(UiPerLogin.class);
					break;
				default:
					break;
				}
			}
		};
		button_signin.setOnClickListener(mOnClickListener);
		button_signup.setOnClickListener(mOnClickListener);
		button_signin_per.setOnClickListener(mOnClickListener);
	}
	
	private void doLoginClubTask()
	{
		name = text_name.getText().toString();
		school = text_school.getText().toString();
		pass = text_pass.getText().toString();
		if(name.length()>0&&school.length()>0&&pass.length()>0)
		{
			app.setLong(System.currentTimeMillis());
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("name", name);
			urlParams.put("pass", pass);
			urlParams.put("school", school);
			doTaskAsync(C.task.loginClub, C.api.loginClub,urlParams);
		}
		else {
			toast("请将信息填写完整");
		}
		
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		switch (taskId) {
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
					toast("登录失败，请重新登录");
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
