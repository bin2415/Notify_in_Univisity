package com.app.notify.util;

import com.app.notify.util.AppFilter;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class AppFilter {

	public static Spanned getHtml (String text)
	{
		return Html.fromHtml(text);
	}
	
	//��Ҫ����ķ���
	public static void setHtml (TextView tv, String text)
	{
		tv.setText(AppFilter.getHtml(text));
	}

}
