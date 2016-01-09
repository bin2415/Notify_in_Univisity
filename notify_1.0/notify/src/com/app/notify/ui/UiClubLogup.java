package com.app.notify.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C;
import com.example.notify.R;
public class UiClubLogup extends BaseUi{
	
	private EditText sign_up_school;
	private EditText sign_up_club;
	private EditText sign_up_phone;
	private EditText sign_up_name;
	private EditText sign_up_pass1;
	private EditText sign_up_pass2;
	private Button sign_up_submit;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.sign_in_org);
		
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		sign_up_school = (EditText) this.findViewById(R.id.sign_in_org_school);
		sign_up_club = (EditText) this.findViewById(R.id.sign_in_org_club);
	
		sign_up_phone = (EditText) this.findViewById(R.id.sign_in_org_phone);
		sign_up_pass1 = (EditText) this.findViewById(R.id.sign_in_org_pass1);
		sign_up_pass2 = (EditText) this.findViewById(R.id.sign_in_org_pass2);
		sign_up_submit = (Button) this.findViewById(R.id.sign_in_org_button);
		sign_up_name = (EditText) this.findViewById(R.id.sign_in_org_name);
		OnClickListener mOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.sign_in_org_button:
					doTaskLogupClub();
					break;

				default:
					break;
				}
			}
		};
		sign_up_submit.setOnClickListener(mOnClickListener);
	
	}
	
	private void doTaskLogupClub()	
	{
		app.setLong(System.currentTimeMillis());
		String name = sign_up_name.getText().toString();
		String school = sign_up_school.getText().toString();
		String club = sign_up_club.getText().toString();
		String phone = sign_up_phone.getText().toString();
		String pass1 = sign_up_pass1.getText().toString();
		String pass2 = sign_up_pass2.getText().toString();
		if(name.length()>0&&school.length()>0&&pass1.length()>0&&
				club.length()>0&&phone.length()>0)
		{
			if(pass1.equals(pass2))
			{
				HashMap<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("admin", name);
				urlParams.put("name", club);
				urlParams.put("pass", pass1);
				urlParams.put("contact", phone);
				urlParams.put("school", school);
				try {
					this.doTaskAsync(C.task.logupClub, C.api.customerClubCreate,urlParams);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else {
				toast("两次输入的密码不一致，请重新输入");
			}
		}
		else {
			toast("信息输入不完整，请重新输入");
		}
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case C.task.logupClub:
			try {
				String code = message.getCode();
				if(code.equals("10000"))
				{
					Bundle params = new Bundle();
					params.putString("school", sign_up_school.getText().toString());
					params.putString("name", sign_up_club.getText().toString());
					params.putString("pass", sign_up_pass1.getText().toString());
					params.putInt("personal", 1);
					forward(UiLogupSucc.class,params);
					
				}
				else {
					toast("发生网络错误，请稍后重试");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}    
}
