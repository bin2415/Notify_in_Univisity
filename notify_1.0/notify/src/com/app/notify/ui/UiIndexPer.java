package com.app.notify.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.notify.base.BaseHandler;
import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseTask;
import com.app.notify.base.BaseUi;
import com.app.notify.base.BaseUiAuth;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.app.notify.list.ActivityList;
import com.app.notify.model.Activity;
import com.app.notify.sqlite.ActivitySqlite;
import com.example.notify.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class UiIndexPer extends BaseUiAuth{
	
	private ListView activityListView;
	private ActivityList activityListAdapter;
	private ActivitySqlite activitySqlite;
	private PullToRefreshListView mPullRefreshListView;
	private int pageId = 1;
	private boolean isFirst = false;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "个人活动");
		
		ImageButton button_back = (ImageButton)this.findViewById(R.id.head_TitleBackBtn);
		button_back.setVisibility(View.GONE);
		setContentView(R.layout.ui_index);
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_activities_per);
		ib.setImageResource(R.drawable.icon_huodong2);
		
		this.setHandler(new IndexHandler(this));
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		activityListView = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setMode(Mode.BOTH);
		//mPullRefreshListView.getLoadingLayoutProxy()
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				if(refreshView.isHeaderShown())
				{
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				isFirst = true;
				pageId = 1;
				// Do work to refresh the list here.
				doTask();}
				else {
					if(refreshView.isFooterShown())
					{
						pageId++;
						doTaskList();
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					}
				}
			}
		});
		
		doTask();
		activitySqlite = new ActivitySqlite(this);
		
		
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	private void doTask()
	{
		HashMap<String, String> activityParams = new HashMap<String, String>();
		activityParams.put("personal", "0");
		activityParams.put("pageId", Integer.toString(pageId));
		this.doTaskAsync(C.task.activityListPersonal, C.api.activityList, activityParams);
	}
	
	private void doTaskList()
	{
		HashMap<String, String> activityParams = new HashMap<String, String>();
		//pageId = pageId + 1;
		activityParams.put("personal", "0");
		activityParams.put("pageId", Integer.toString(pageId));
		this.doTaskAsync(C.task.activityListPersonAdd, C.api.activityList,activityParams);
	}
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		
		switch (taskId) {
		case C.task.activityListPersonal:
			try {
				@SuppressWarnings("unchecked")
				final ArrayList<Activity> activityList = (ArrayList<Activity>) message.getResultList("Activity");
                for(Activity activity : activityList)
                {
                	loadImage(activity.getFace());
                	activitySqlite.updateActivity(activity);
                }
                
               
                //activityListView = (ListView) this.findViewById(R.id.app_index_list_view);
                activityListAdapter = new ActivityList(this, activityList);
                activityListView.setAdapter(activityListAdapter);
                
                if(isFirst)
                {
                	mPullRefreshListView.onRefreshComplete();
                }
                
                activityListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int pos, long id) {
						// TODO Auto-generated method stub
						Bundle params = new Bundle();
						int poss = pos-1;
						ArrayList<Activity> activityst = activityListAdapter.getActivityList();
						String activityid = activityst.get(poss).getId();
						params.putString("activityId", activityid);
						params.putString("personal", "0");
						overlay(UiActivity.class,params);
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				toast(e.getMessage());
			}
			
			break;
		case C.task.activityListPersonAdd:
			mPullRefreshListView.onRefreshComplete();
			if(message.getCode().equals("10000"))
			{
			try {
				final ArrayList<Activity> addedList = (ArrayList<Activity>)message.getResultList("Activity");
				activityListAdapter.addItem(addedList);
				  for(Activity activity : addedList)
	                {
	                	loadImage(activity.getFace());
	                	activitySqlite.updateActivity(activity);
	                }
				activityListAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
			else {
				toast("已没有更多的活动");
			}
			
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onNetworkError(int taskId)
	{
		super.onNetworkError(taskId);
		toast(C.err.network);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private class IndexHandler extends BaseHandler {
		public IndexHandler(BaseUi ui) {
			super(ui);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
					case BaseTask.LOAD_IMAGE:
						activityListAdapter.notifyDataSetChanged();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ui.toast(e.getMessage());
			}
		}
	}

}
