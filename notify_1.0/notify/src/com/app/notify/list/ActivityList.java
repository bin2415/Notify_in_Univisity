package com.app.notify.list;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notify.R;
import com.app.notify.base.BaseList;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C.intent;
import com.app.notify.list.ActivityList.ActivityListItem;
import com.app.notify.model.Activity;
import com.app.notify.ui.UiComment;
import com.app.notify.util.AppCache;
import com.app.notify.util.AppFilter;

public class ActivityList extends BaseList{

	private BaseUi ui;
	private LayoutInflater inflater;
	private ArrayList<Activity> blogList;
	private ActivityListItem  blogItem ;
	//private MyClickListener myClickListener;
	
	public final class ActivityListItem {
		public ImageView face = null;
		public TextView person = null;
		public TextView uptime = null;
		public TextView content = null;
		public Button part = null;
		public Button comment = null;
	}
	
	/*public ActivityList (BaseUi ui, ArrayList<Activity> blogList,MyClickListener myClickListener) {
		this.ui = ui;
		this.inflater = LayoutInflater.from(this.ui);
		this.blogList = blogList;
		//this.myClickListener = myClickListener;
	}*/
	
	public ActivityList (BaseUi ui, ArrayList<Activity> blogList) {
		this.ui = ui;
		this.inflater = LayoutInflater.from(this.ui);
		this.blogList = blogList;
		//this.myClickListener = myClickListener;
	}
	public ArrayList<Activity> getActivityList()
	{
		return blogList;
	}
	@Override
	public int getCount() {
		return blogList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	public void addItem(final ArrayList<Activity> activitylist)
	{
		blogList.addAll(activitylist);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int p, View v, ViewGroup parent) {
		// init tpl
		
		// if cached expired
		if (v == null) {
			v = inflater.inflate(R.layout.tpl_list_activities, null);
			blogItem = new ActivityListItem();
			blogItem.person=(TextView)v.findViewById(R.id.tpl_list_activities_person);
			blogItem.face = (ImageView) v.findViewById(R.id.tpl_list_activities_face);
			blogItem.content = (TextView) v.findViewById(R.id.tpl_list_activities_text_content);
			blogItem.uptime = (TextView) v.findViewById(R.id.tpl_list_activities_uptime);
			blogItem.comment = (Button) v.findViewById(R.id.tpl_list_activities_comment);
			blogItem.comment.setTag(p+"");
			blogItem.comment.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int index = Integer.parseInt(v.getTag().toString());
					//Toast.makeText(context, text, duration)
					
				}
			});
			blogItem.part = (Button) v.findViewById(R.id.tpl_list_activities_part);
			v.setTag(blogItem);
		} else {
			blogItem = (ActivityListItem) v.getTag();
		}
		
		// fill data
		blogItem.person.setText(blogList.get(p).getPerson());
		blogItem.uptime.setText(blogList.get(p).getUptime());
		// fill html data
		blogItem.content.setText(AppFilter.getHtml(blogList.get(p).getContent()));
		blogItem.comment.setText(blogList.get(p).getComment());
		blogItem.part.setText(blogList.get(p).getPart());
		//String up = blogList.get(p).getUptime();
		blogItem.uptime.setText(blogList.get(p).getUptime());
		//blogItem.comment.setOnClickListener(myClickListener);
		//blogItem.part.setOnClickListener(myClickListener);
		// load face image
		String faceUrl = blogList.get(p).getFace();
		Bitmap faceImage = AppCache.getImage(faceUrl);
		if (faceImage != null) {
			blogItem.face.setImageBitmap(faceImage);
		}
		return v;
	}
	
	
}
