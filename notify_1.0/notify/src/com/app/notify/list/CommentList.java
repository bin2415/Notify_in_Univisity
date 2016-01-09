package com.app.notify.list;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.notify.base.BaseList;
import com.app.notify.base.BaseUi;
import com.app.notify.list.ActivityList.ActivityListItem;
import com.app.notify.model.Activity;
import com.app.notify.model.Comment;
import com.app.notify.util.AppCache;
import com.app.notify.util.AppFilter;
import com.example.notify.R;

public class CommentList extends BaseList {

	private BaseUi ui;
	private LayoutInflater inflater;
	private ArrayList<Comment> commentList;
	
	public final class CommentListItem {
		public TextView person;
		public TextView uptime;
		public TextView comment;
	}
	
	
	public CommentList (BaseUi ui, ArrayList<Comment> blogList) {
		this.ui = ui;
		this.inflater = LayoutInflater.from(this.ui);
		this.commentList = blogList;
	}
	
	@Override
	public int getCount() {
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int p, View v, ViewGroup parent) {
		// init tpl
		CommentListItem  blogItem = null;
		// if cached expired
		if (v == null) {
			v = inflater.inflate(R.layout.tpl_list_comment, null);
			blogItem = new CommentListItem();
			blogItem.person=(TextView)v.findViewById(R.id.tpl_list_comment_person);			
			blogItem.comment = (TextView) v.findViewById(R.id.tpl_list_comment_comment);
			blogItem.uptime = (TextView) v.findViewById(R.id.tpl_list_comment_uptime);
			v.setTag(blogItem);
		} else {
			blogItem = (CommentListItem) v.getTag();
		}
		// fill data
		blogItem.person.setText(commentList.get(p).getCustomername());
		blogItem.uptime.setText(commentList.get(p).getUptime());
		// fill html data
		blogItem.comment.setText(commentList.get(p).getContent());
		return v;
	}
}
