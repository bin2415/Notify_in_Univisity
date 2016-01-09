package com.app.notify.ui;


import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.notify.R;
import com.app.notify.base.BaseAuth;
import com.app.notify.base.BaseUi;

public class UILogup extends BaseUi{
	
	private Button button_club;
	private Button button_person;
	private Button button_signin;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(BaseAuth.isClubLogin()||BaseAuth.isPersonLogin())
		{
			this.forward(UiIndex.class);
		}
		
		setContentView(R.layout.sign_up);
		
		button_club = (Button) this.findViewById(R.id.sign_up_club_button);
		button_person = (Button) this.findViewById(R.id.sign_up_person_button);
		button_signin = (Button) this.findViewById(R.id.sign_up_denglu);
	
		OnClickListener mOnClickListener = new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				switch (v.getId()) {
				case R.id.sign_up_club_button:
					overlay(UiClubLogup.class);
					break;
				case R.id.sign_up_person_button:
					overlay(UIPerLogup.class);
					break;
				case R.id.sign_up_denglu:
					forward(UiPerLogin.class);
					break;
				default:
					break;
				}
			}
		};
		
		button_club.setOnClickListener(mOnClickListener);
		button_person.setOnClickListener(mOnClickListener);
		button_signin.setOnClickListener(mOnClickListener);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
			doFinish();
		return super.onKeyDown(keyCode, event);
	}
}
