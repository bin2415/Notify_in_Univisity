package com.app.notify.base;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.notify.util.AppUtil;

public class BaseMessage {
	


	private String code;
	private String message;
	private String resultSrc;
	private Map<String, BaseModel> resultMap;
	private Map<String, ArrayList<?extends BaseModel>> resultList;

	public BaseMessage() {
		// TODO Auto-generated constructor stub
		this.resultMap = new HashMap<String, BaseModel>();
		this.resultList = new HashMap<String, ArrayList<? extends BaseModel>>();
	}
	
	public String toString()
	{
		return code + " | " + message + " | " + resultSrc;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getResult()
	{
		return this.resultSrc;
	}
	
	public Object getResult(String modelName) throws Exception
	{
		Object model = this.resultMap.get(modelName);
		if( model == null)
		{
			throw new Exception("Message data is empty");
		}
		return model;
	}
	
	 public ArrayList<? extends BaseModel> getResultList(String modelName) throws Exception
	 {
		 ArrayList<? extends BaseModel> modelList = this.resultList.get(modelName);
		 if(modelList == null || modelList.size() == 0)
			 throw new Exception("Message data list is empty");
		 return modelList;
	 }
	 
	 public void setResult(String result) throws Exception
	 {
		 this.resultSrc = result;
		 if(result.length() > 0)
		 {
			 JSONObject jsonObject = null;
			 jsonObject = new JSONObject(result);
			 Iterator<String> it = jsonObject.keys();
		     while(it.hasNext())
		     {
		    	 String jsonKey = it.next();
		    	 String modelName = getModelName(jsonKey);
		    	 String modelClassName = "com.app.notify.model."+modelName;
		    	 JSONArray modelJsonArray = jsonObject.optJSONArray(jsonKey);
		    	 
		    	 if(modelJsonArray == null)
		    	 {
		    		 JSONObject modelJsonObject = jsonObject.optJSONObject(jsonKey);
		    		 if(modelJsonObject == null)
		    			 throw new Exception("Message result is invalid");
		    		 this.resultMap.put(modelName, json2model(modelClassName,modelJsonObject));
		    		 
		    	 }
		    	 else {
					ArrayList<BaseModel>modelList = new ArrayList<BaseModel>();
					for(int i = 0;i < modelJsonArray.length();i++)
					{
						JSONObject modelJsonObject = modelJsonArray.optJSONObject(i);
						modelList.add(json2model(modelClassName,modelJsonObject));
					}
					this.resultList.put(modelName, modelList);
				}
		     }
		 }
	 }
	 
	 private BaseModel json2model(String modelClassName, JSONObject modelJsonObject) throws Exception
	 {
		 BaseModel modelObj = (BaseModel)Class.forName(modelClassName).newInstance();
		 Class<?extends BaseModel> modelClass = modelObj.getClass();
		 
		 Iterator<String> it = modelJsonObject.keys();
		 while(it.hasNext())
		 {
			 String varField = it.next();
			 String varValue = modelJsonObject.getString(varField);
			 Field field = modelClass.getDeclaredField(varField);
			 field.setAccessible(true);
			 field.set(modelObj, varValue);
		 }
		 
		 return modelObj;
	 }
	 private String getModelName(String str)
	 {
		 String[] strArr = str.split("\\W");
		 if(strArr.length > 0)
		 {
			 str = strArr[0];
		 }
		 return AppUtil.ucfirst(str);
	 }

}
