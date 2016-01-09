package com.app.notify.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;

import com.app.notify.base.BaseSqlite;
import com.app.notify.base.C.action;
import com.app.notify.model.Activity;

public class ActivitySqlite extends BaseSqlite{

	public ActivitySqlite(Context context) {
		super(context);
	}

	@Override
	protected String tableName() {
		return "activitys";
	}

	@Override
	protected String[] tableColumns() {
		String[] columns = {
			Activity.COL_ID,
			Activity.COL_FACE,
			Activity.COL_CONTENT,
			Activity.COL_COMMENT,
			Activity.COL_PERSON,
			Activity.COL_UPTIME,
			Activity.COL_TITLE,
			Activity.COL_LIKE,
			Activity.COL_PART,
			Activity.COL_PICTURE
		};
		return columns;
	}

	@Override
	protected String createSql() {
		return "CREATE TABLE " + tableName() + " (" +
			Activity.COL_ID + " INTEGER PRIMARY KEY, " +
			Activity.COL_FACE + " TEXT, " +
			Activity.COL_CONTENT + " TEXT, " +
			Activity.COL_COMMENT + " TEXT, " +
			Activity.COL_PERSON + " TEXT, " +
			Activity.COL_UPTIME + " TEXT," +
			Activity.COL_TITLE + " TEXT, " +
			Activity.COL_LIKE + " TEXT, " +
			Activity.COL_PART + " TEXT, " +
			Activity.COL_PICTURE + " TEXT " +
			");";
	}

	@Override
	protected String upgradeSql() {
		return "DROP TABLE IF EXISTS " + tableName();
	}

	public boolean updateActivity (Activity blog) {
		// prepare blog data
		ContentValues values = new ContentValues();
		values.put(Activity.COL_ID, blog.getId());
		values.put(Activity.COL_FACE, blog.getFace());
		values.put(Activity.COL_CONTENT, blog.getContent());
		values.put(Activity.COL_COMMENT, blog.getComment());
		values.put(Activity.COL_PERSON, blog.getPerson());
		values.put(Activity.COL_UPTIME, blog.getUptime());
		values.put(Activity.COL_TITLE, blog.getTitle());
		values.put(Activity.COL_LIKE, blog.getLike());
		values.put(Activity.COL_PART, blog.getPart());
		values.put(Activity.COL_PICTURE, blog.getPicture());
		// prepare sql
		String whereSql = Activity.COL_ID + "=?";
		String[] whereParams = new String[]{blog.getId()};
		// create or update
		try {
			if (this.exists(whereSql, whereParams)) {
				this.update(values, whereSql, whereParams);
			} else {
				this.create(values);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public ArrayList<Activity> getAllActivitys () {
		ArrayList<Activity> blogList = new ArrayList<Activity>();
		try {
			ArrayList<ArrayList<String>> rList = this.query(null, null);
			int rCount = rList.size();
			for (int i = 0; i < rCount; i++) {
				ArrayList<String> rRow = rList.get(i);
				Activity blog = new Activity();
				blog.setId(rRow.get(0));
				blog.setFace(rRow.get(1));
				blog.setContent(rRow.get(2));
				blog.setComment(rRow.get(3));
				blog.setPerson(rRow.get(4));
				blog.setUptime(rRow.get(5));
				blog.setTitle(rRow.get(6));
				blog.setLike(rRow.get(7));
				blog.setPart(rRow.get(8));
				blog.setPicture(rRow.get(9));
				blogList.add(blog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return blogList;
	}
}
