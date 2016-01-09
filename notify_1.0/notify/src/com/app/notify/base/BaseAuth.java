package com.app.notify.base;

import com.app.notify.model.CustomerClub;
import com.app.notify.model.CustomerPerson;

public class BaseAuth {
	
	/**
	 * �����person�ڵ�½����club��½���Ƕ�û��½
	 * @return 0��ʾ��û��½��1��ʾperson��½��2��ʾclub��½
	 */
	private static int status = 0;
	static public int getStatus()
	{
		return status;
	}

    /**
     * 
     * @param s
     */
	static public void setStatus(int s)
	{
		status = s;
	}
	static public boolean isPersonLogin () {
		CustomerPerson customer = CustomerPerson.getInstance();
		if (customer.getLogin() == true) {
			return true;
		}
		return false;
	}
	
	static public void setPersonLogin (Boolean status) {
		CustomerPerson customer = CustomerPerson.getInstance();
		customer.setLogin(status);
	}
	
	static public void setCustomerPerson (CustomerPerson mc) {
		CustomerPerson customer = CustomerPerson.getInstance();
		customer.setId(mc.getId());
		customer.setSid(mc.getSid());
		customer.setName(mc.getName());
		customer.setContact(mc.getContact());
		customer.setFace(mc.getFace());
		customer.setFaceUrl(mc.getFaceUrl());
		customer.setSchool(mc.getSchool());
		customer.setActivitycount(mc.getActivitycount());
		customer.setUptime(mc.getUptime());	
	}
	
	static public CustomerPerson getCustomerPerson () {
		return CustomerPerson.getInstance();
	}
	
	static public boolean isClubLogin () {
		CustomerClub customer = CustomerClub.getInstance();
		if (customer.getLogin() == true) {
			return true;
		}
		return false;
	}
	
	static public void setClubLogin (Boolean status) {
		CustomerClub customer = CustomerClub.getInstance();
		customer.setLogin(status);
	}
	
	static public void setCustomerClub (CustomerClub mc) {
		CustomerClub customer = CustomerClub.getInstance();
		customer.setId(mc.getId());
		customer.setSid(mc.getSid());
		customer.setName(mc.getName());
		customer.setContact(mc.getContact());
		customer.setFace(mc.getFace());
		customer.setFaceUrl(mc.getFaceUrl());
		customer.setSchool(mc.getSchool());
		customer.setActivitycount(mc.getActivitycount());
		customer.setUptime(mc.getUptime());	
		customer.setAdmin(mc.getAdmin());
	}
	
	static public CustomerClub getCustomerClub () {
		return CustomerClub.getInstance();
	}
}
