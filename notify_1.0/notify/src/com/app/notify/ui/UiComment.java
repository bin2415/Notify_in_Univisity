package com.app.notify.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseUi;
import com.app.notify.base.C;
import com.app.notify.base.CustomTitleBar;
import com.example.notify.R;

public class UiComment extends BaseUi{
	
	private EditText editComment = null;
	private Button buttonComment = null;
	private Bundle bundle = null;
	private String activityId = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "写评论");
		setContentView(R.layout.comment);
		
		ImageButton button_back = (ImageButton)this.findViewById(R.id.head_TitleBackBtn);
		button_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFinish();
			}
		});
		
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		
		bundle = this.getIntent().getExtras();
		activityId = bundle.getString("activityId");
		
		editComment = (EditText)this.findViewById(R.id.comment_editText);
		buttonComment = (Button)this.findViewById(R.id.comment_button);
		
		OnClickListener mClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.comment_button:
					String comment = editComment.getText().toString();
					if(comment.length() > 0)
					{
					HashMap<String, String> commentParams = new HashMap<String, String>();
					commentParams.put("activityId", activityId);
					commentParams.put("content", comment);
					doTaskAsync(C.task.createComment, C.api.commentCreate,commentParams);
					}
					else {
						toast("请写点评论内容吧");
					}
					break;

				default:
					break;
				}
			}
		};
		buttonComment.setOnClickListener(mClickListener);
	}
	
	@Override
	public void onTaskComplete(int taskId,BaseMessage message)
	{
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case C.task.createComment:
			try {
				String code = message.getCode();
				if(code.equals("10000"))
				{
					toast("你已评论成功");
					doFinish();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			break;

		default:
			break;
		}
	}

}
