package com.app.notify.util;

import com.app.notify.model.CustomerClub;
import com.app.notify.model.CustomerPerson;
import com.example.notify.R;

import android.content.Context;
import android.content.res.Resources;

public class UIUtil {

	public static String getCustomerPersonInfo(Context ctx,CustomerPerson customer)
	{
		Resources resources = ctx.getResources();
		StringBuffer sb = new StringBuffer();
		sb.append(resources.getString(R.string.customer_activity));
		sb.append(" ");
		sb.append(customer.getActivitycount());
		
		return sb.toString();
	}
	
	public static String getCustomerClubInfo(Context ctx,CustomerClub customer)
	{
		Resources resources = ctx.getResources();
		StringBuffer sb = new StringBuffer();
		sb.append(resources.getString(R.string.customer_activity));
		sb.append(" ");
		sb.append(customer.getActivitycount());
		
		return sb.toString();
	}
}
