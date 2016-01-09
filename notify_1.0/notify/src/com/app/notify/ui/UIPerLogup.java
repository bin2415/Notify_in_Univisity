package com.app.notify.ui;

import java.util.HashMap;
import java.util.jar.Attributes.Name;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C;
import com.app.notify.model.CustomerClub;
import com.example.notify.R;

public class UIPerLogup extends BaseUi{
	private EditText edit_sign_school;
	private EditText edit_sign_sid;
	private EditText edit_sign_phone;
	private EditText edit_sign_name;
	private EditText edit_sign_pass1;
	private EditText edit_sign_pass2;
	private Button button_submit;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	
        setContentView(R.layout.sign_in_personnal);
        
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        edit_sign_school = (EditText) this.findViewById(R.id.sign_in_per_school);
        edit_sign_name = (EditText) this.findViewById(R.id.sign_in_per_name);
        edit_sign_phone = (EditText) this.findViewById(R.id.sign_in_per_phone);
        edit_sign_pass1 = (EditText) this.findViewById(R.id.sign_in_per_pass1);
        edit_sign_pass2 = (EditText) this.findViewById(R.id.sign_in_per_pass2);
        edit_sign_sid = (EditText) this.findViewById(R.id.sign_in_per_sid);
        button_submit = (Button) this.findViewById(R.id.sign_in_per_signin);
        
        OnClickListener mOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.sign_in_per_signin:
					doTaskLogupPerson();
					break;

				default:
					break;
				}
			}
		};
		button_submit.setOnClickListener(mOnClickListener);
    }
    
    private void doTaskLogupPerson()
    {
    	app.setLong(System.currentTimeMillis());
    	String name = edit_sign_name.getText().toString();
    	String school = edit_sign_school.getText().toString();
    	String pass1 = edit_sign_pass1.getText().toString();
    	String pass2 = edit_sign_pass2.getText().toString();
    	String sid = edit_sign_sid.getText().toString();
    	String phone = edit_sign_phone.getText().toString();
    	if(name.length()>0&&school.length()>0&&pass1.length()>0&&
    			sid.length()>0&&phone.length()>0)
    	{
    		if(pass1.equals(pass2))
    		{
    			HashMap<String, String> urlParams = new HashMap<String, String>();
    			urlParams.put("name",name);
    			urlParams.put("school", school);
    			urlParams.put("pass", pass1);
    			urlParams.put("contact", phone);
    			urlParams.put("studentid", sid);
    			try {
					this.doTaskAsync(C.task.logupPerson, C.api.customerPersonCreate,urlParams);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
    		}
			else
			{
				toast("两次输入的密码不一致，请重新输入");
			}
    	}
		else
		{
			toast("信息输入不完整，请重新输入");
		}
		
    }
    
    @Override
    public void onTaskComplete(int taskId,BaseMessage message)
    {
    	super.onTaskComplete(taskId, message);
    	switch(taskId)
    	{
    	case C.task.logupPerson:
    		try {
				String code = message.getCode();
				if(code.equals("14006"))
					toast("该学生号已存在，请登录");
				else {
					if(code.equals("10000"))
					{
						Bundle params = new Bundle();
						params.putString("sid", edit_sign_sid.getText().toString());
						params.putString("school", edit_sign_school.getText().toString());
						params.putString("pass", edit_sign_pass1.getText().toString());
						params.putInt("personal", 0);
						forward(UiLogupSucc.class,params);
					}
					else {
						toast("发生网络错误，请稍后重试");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
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
