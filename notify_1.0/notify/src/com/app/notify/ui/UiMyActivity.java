package com.app.notify.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.notify.base.BaseHandler;
import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseTask;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.app.notify.list.ActivityList;
import com.app.notify.model.Activity;
import com.example.notify.R;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
public class UiMyActivity extends BaseUi{
	
	private ListView activityListView = null;
	private ActivityList activityListAdapter = null;
	private PullToRefreshListView mPullRefreshListView;
	private int pageId = 1;
	private Bundle param = null;
	private boolean isFirst = false;
	private String personal = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "我发起的活动");
		setContentView(R.layout.my_parted_activity);
		ImageButton button_back = (ImageButton)this.findViewById(R.id.head_TitleBackBtn);
		button_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFinish();
			}
		});
		param = this.getIntent().getExtras();
		personal = param.getString("personal");
		 mPullRefreshListView = (PullToRefreshListView)this.findViewById(R.id.pull_refresh_list_my_activity);
		 activityListView =  mPullRefreshListView.getRefreshableView();
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
		 this.setHandler(new IndexHandler(this));
	}
	
	public void doTask()
	{
		HashMap<String, String> activtityParams = new HashMap<String, String>();
		activtityParams.put("pageId", Integer.toString(pageId));
		this.doTaskAsync(C.task.myActivity, C.api.activityListPersonal,activtityParams);
	}
	public void doTaskList()
	{
		HashMap<String, String> activityParams = new HashMap<String, String>();
		activityParams.put("pageId", Integer.toString(pageId));
		this.doTaskAsync(C.task.myActivityAdd, C.api.activityListPersonal,activityParams);
	}
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case C.task.myActivity:
			try {
				@SuppressWarnings("unchecked")
				final ArrayList<Activity> activityList = (ArrayList<Activity>) message.getResultList("Activity");
                for(Activity activity : activityList)
                {
                	loadImage(activity.getFace());
                	//activitySqlite.updateActivity(activity);
                }
               
               if(isFirst)
               {
            	   mPullRefreshListView.onRefreshComplete();
               }
                activityListAdapter = new ActivityList(this, activityList);
                activityListView.setAdapter(activityListAdapter);
                activityListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int pos, long id) {
						// TODO Auto-generated method stub
						Bundle params = new Bundle();
						params.putString("activityId", activityList.get(pos-1).getId());
						params.putString("personal", personal);
						overlay(UiActivity.class,params);
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				toast(e.getMessage());
			}
			break;
		case C.task.myActivityAdd:
			mPullRefreshListView.onRefreshComplete();
			if(message.getCode().equals("10000"))
			{
				try {
					final ArrayList<Activity> addedList = (ArrayList<Activity>)message.getResultList("Activity");
					  for(Activity activity : addedList)
		                {
		                	loadImage(activity.getFace());
		                	//activitySqlite.updateActivity(activity);
		                }
					activityListAdapter.addItem(addedList);
					activityListAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else
			{
				toast("已经没有更多的活动");
			}
			break;
		default:
			break;
		}
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
