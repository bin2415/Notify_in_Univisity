package com.app.notify.ui;

import java.net.PasswordAuthentication;





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
import com.app.notify.model.CustomerPerson;
import com.example.notify.R;

public class UiPerLogin extends BaseUi{
	
	private EditText text_school;
	private EditText text_name;
	private EditText text_pass;
	
	private String name = null;
	private String school = null;
	private String pass = null;
	
	private Button button_per_signup;
	private Button button_per_signin;
	private Button button_club_signin;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_personnal);
		
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		
	    text_name = (EditText)this.findViewById(R.id.sign_up_per_school);
	    text_school = (EditText)this.findViewById(R.id.sign_up_per_name);
	    text_pass = (EditText)this.findViewById(R.id.sign_up_per_pass);
	    
	    button_per_signup = (Button)this.findViewById(R.id.sign_up_per_signup);
	    button_per_signin = (Button)this.findViewById(R.id.sign_up_per_signin);
	    button_club_signin = (Button)this.findViewById(R.id.sign_up_club_signin);
	    
	    OnClickListener mClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.sign_up_per_signup:
					forward(UILogup.class);
					break;
				case R.id.sign_up_per_signin:
					doLoginTaskPerson();
					break;
				case R.id.sign_up_club_signin:
					forward(UiClubLogin.class);
					break;
				default:
					break;
				}
			}
		};
		button_per_signin.setOnClickListener(mClickListener);
		button_per_signup.setOnClickListener(mClickListener);
		button_club_signin.setOnClickListener(mClickListener);
	}
	
     private void doLoginTaskPerson()
     {
    	 name = text_name.getText().toString();
    	 school = text_school.getText().toString();
    	 pass = text_pass.getText().toString();
    	 
    	 if(name.length()>0&&school.length()>0&&pass.length()>0)
    	 {
    		 app.setLong(System.currentTimeMillis());
    		 HashMap<String, String> urlParams = new HashMap<String, String>();
    		 urlParams.put("studentid", name);
    		 urlParams.put("pass", pass);
    		 urlParams.put("school", school);
    		 doTaskAsync(C.task.loginPerson, C.api.loginPerson,urlParams);
    	 }
    	 else {
			toast("请将信息填完整");
		}
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
 					toast("登录失败，请重新登录");
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
