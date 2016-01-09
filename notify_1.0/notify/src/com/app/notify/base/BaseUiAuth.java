package com.app.notify.base;

import com.app.notify.model.CustomerClub;
import com.app.notify.model.CustomerPerson;
import com.app.notify.ui.UILogup;
import com.app.notify.ui.UiAddActivity;
import com.app.notify.ui.UiIndex;
import com.app.notify.ui.UiIndexPer;
import com.app.notify.ui.UiPerson;
import com.example.notify.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BaseUiAuth extends BaseUi{
	
	protected static CustomerPerson customerPerson = null;
	protected static CustomerClub customerClub = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(!BaseAuth.isClubLogin()&&!BaseAuth.isPersonLogin())
		{
			this.forward(UILogup.class);
			this.onStop();
		}
		else {
			if(BaseAuth.isClubLogin())
			{
				customerClub = BaseAuth.getCustomerClub();
			}
			else {
				customerPerson = BaseAuth.getCustomerPerson();
			}
		}
	}
	@Override
	public void onStart()
	{
		super.onStart();
		this.bindMainTab();
	}
	
	private void bindMainTab()
	{
		ImageButton image_club = (ImageButton)this.findViewById(R.id.main_tab_activities_club);
		ImageButton image_per = (ImageButton)this.findViewById(R.id.main_tab_activities_per);
		ImageButton image_add = (ImageButton)this.findViewById(R.id.main_tab_add);
		ImageButton image_person = (ImageButton)this.findViewById(R.id.main_tab_person);
		
		OnClickListener mOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.main_tab_activities_club:
					forward(UiIndex.class);
					break;
				case R.id.main_tab_activities_per:
					forward(UiIndexPer.class);
					break;
				case R.id.main_tab_add:
					forward(UiAddActivity.class);
					break;
				case R.id.main_tab_person:
					forward(UiPerson.class);
				default:
					break;
				}
			}
		};
		image_club.setOnClickListener(mOnClickListener);
		image_per.setOnClickListener(mOnClickListener);
		image_add.setOnClickListener(mOnClickListener);
		image_person.setOnClickListener(mOnClickListener);
	}

}
