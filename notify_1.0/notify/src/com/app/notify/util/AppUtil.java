package com.app.notify.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.app.notify.base.BaseMessage;
import com.app.notify.base.BaseModel;
import com.app.notify.model.Activity;
import com.app.notify.model.CustomerClub;
import com.app.notify.model.CustomerPerson;

public class AppUtil {

	/**
	 * md5加密
	 * @param str
	 * @return
	 */
	static public String md5 (String str)
	{
		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(algorithm != null)
			
		{
			algorithm.reset();
			algorithm.update(str.getBytes());
			byte[] bytes = algorithm.digest();
			StringBuilder hexString = new StringBuilder();
			for(byte b : bytes)
				hexString.append(Integer.toHexString(0xFF & b));
			return hexString.toString();
		}
		return "";
	}
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	static public String ucfirst (String str)
	{
		if(str != null && str != "")
		{
			str = str.substring(0,1).toUpperCase() + str.substring(1);
		}
		return str;
		
	}
	
	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity, final String defaultCharset) throws IOException, ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return "";
		}
		// gzip logic start
		if (entity.getContentEncoding().getValue().contains("gzip")) {
			instream = new GZIPInputStream(instream);
		}
		// gzip logic end
		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
		}
		int i = (int)entity.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		String charset = EntityUtils.getContentCharSet(entity);
		if (charset == null) {
			charset = defaultCharset;
		}
		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}
		Reader reader = new InputStreamReader(instream, charset);
		CharArrayBuffer buffer = new CharArrayBuffer(i);
		try {
			char[] tmp = new char[1024];
			int l;
			while((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}
	
	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity)
		throws IOException, ParseException {
		return gzipToString(entity, null);
	}
	
	public static SharedPreferences getSharedPreferences (Context ctx) {
		return ctx.getSharedPreferences("com.app.demos.sp.global", Context.MODE_PRIVATE);
	}
	
	public static SharedPreferences getSharedPreferences (Service service) {
		return service.getSharedPreferences("com.app.demos.sp.global", Context.MODE_PRIVATE);
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// 业务逻辑
	
	/* 获取 Session Id */
	static public String getPersonSessionId () {
		CustomerPerson customer = CustomerPerson.getInstance();
		return customer.getSid();
	}
	
	static public String getClubSessionId()
	{
		CustomerClub customer = CustomerClub.getInstance();
		return customer.getSid();	
	}
	/* 获取 Message */
	static public BaseMessage getMessage (String jsonStr) throws Exception {
		BaseMessage message = new BaseMessage();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonStr);
			if (jsonObject != null) {
				 message.setCode(jsonObject.getString("code"));
				message.setMessage(jsonObject.getString("message"));
				message.setResult(jsonObject.getString("result"));
			}
		} catch (JSONException e) {
			throw new Exception("Json format error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/* Model 数组转化成 Map 列表 */
	static public List<? extends Map<String,?>> dataToList (List<? extends BaseModel> data, String[] fields) {
		ArrayList<HashMap<String,?>> list = new ArrayList<HashMap<String,?>>();
		for (BaseModel item : data) {
			list.add((HashMap<String, ?>) dataToMap(item, fields));
		}
		return list;
	}
	
	/* Model 转化成 Map */
	static public Map<String,?> dataToMap (BaseModel data, String[] fields) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		try {
			for (String fieldName : fields) {
				Field field = data.getClass().getDeclaredField(fieldName);
				field.setAccessible(true); // have private to be accessable
				map.put(fieldName, field.get(data));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/* 判断int是否为空 */
	static public boolean isEmptyInt (int v) {
		Integer t = new Integer(v);
		return t == null ? true : false;
	}
	
	/* 获取毫秒数 */
	public static long getTimeMillis () {
		return System.currentTimeMillis();
	}
	
	/* 获取耗费内存 */
	public static long getUsedMemory () {
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return total - free;
	}
	
	/* 璇诲彇骞跺垱寤轰綅鍥�*/
	public static Bitmap createBitmap(String path, int w, int h) {
		try {
			File f = new File(path);
			if (f.exists() == false)
				return null;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// 杩欓噷鏄暣涓柟娉曠殑鍏抽敭锛宨nJustDecodeBounds璁句负true鏃跺皢涓嶄负鍥剧墖鍒嗛厤鍐呭瓨
			BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;// 鑾峰彇鍥剧墖鐨勫師濮嬪搴�
			int srcHeight = opts.outHeight;// 鑾峰彇鍥剧墖鍘熷楂樺害
			int destWidth = 0;
			int destHeight = 0;
			// 缂╂斁鐨勬瘮渚�
			double ratio = 0.0;
			if (srcWidth < w || srcHeight < h) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {// 鎸夋瘮渚嬭绠楃缉鏀惧悗鐨勫浘鐗囧ぇ灏�
				ratio = (double) srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / h;
				destHeight = h;
				destWidth = (int) (srcWidth / ratio);
			}
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缂╂斁鐨勬瘮渚嬶紝缂╂斁鏄緢闅炬寜鍑嗗鐨勬瘮渚嬭繘琛岀缉鏀剧殑锛岀洰鍓嶆垜鍙彂鐜板彧鑳介�杩噄nSampleSize鏉ヨ繘琛岀缉鏀�
			newOpts.inSampleSize = (int) ratio + 1;
			// inJustDecodeBounds璁句负false琛ㄧず鎶婂浘鐗囪杩涘唴瀛樹腑
			newOpts.inJustDecodeBounds = false;
			// 璁剧疆澶у皬锛岃繖涓竴鑸槸涓嶅噯纭殑锛屾槸浠nSampleSize鐨勪负鍑嗭紝浣嗘槸濡傛灉涓嶈缃嵈涓嶈兘缂╂斁
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 鑾峰彇缂╂斁鍚庡浘鐗�
			FileInputStream is = new FileInputStream(path);
			Bitmap  map = BitmapFactory.decodeFileDescriptor(is.getFD(),null,newOpts);
					return map;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/** 
	 * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换 
	 * @param activity 
	 * @param imageUri 
	 * @author yaoxing 
	 * @date 2014-10-12 
	 */  
	@TargetApi(19)  
	public static String getImageAbsolutePath(Context context, Uri imageUri) {  
	    if (context == null || imageUri == null)  
	        return null;  
	    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {  
	        if (isExternalStorageDocument(imageUri)) {  
	            String docId = DocumentsContract.getDocumentId(imageUri);  
	            String[] split = docId.split(":");  
	            String type = split[0];  
	            if ("primary".equalsIgnoreCase(type)) {  
	                return Environment.getExternalStorageDirectory() + "/" + split[1];  
	            }  
	        } else if (isDownloadsDocument(imageUri)) {  
	            String id = DocumentsContract.getDocumentId(imageUri);  
	            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  
	            return getDataColumn(context, contentUri, null, null);  
	        } else if (isMediaDocument(imageUri)) {  
	            String docId = DocumentsContract.getDocumentId(imageUri);  
	            String[] split = docId.split(":");  
	            String type = split[0];  
	            Uri contentUri = null;  
	            if ("image".equals(type)) {  
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
	            } else if ("video".equals(type)) {  
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
	            } else if ("audio".equals(type)) {  
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
	            }  
	            String selection = MediaStore.Images.Media._ID + "=?";  
	            String[] selectionArgs = new String[] { split[1] };  
	            return getDataColumn(context, contentUri, selection, selectionArgs);  
	        }  
	    } // MediaStore (and general)  
	    else if ("content".equalsIgnoreCase(imageUri.getScheme())) {  
	        // Return the remote address  
	        if (isGooglePhotosUri(imageUri))  
	            return imageUri.getLastPathSegment();  
	        return getDataColumn(context, imageUri, null, null);  
	    }  
	    // File  
	    else if ("file".equalsIgnoreCase(imageUri.getScheme())) {  
	        return imageUri.getPath();  
	    }  
	    return null;  
	}  
	  
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {  
	    Cursor cursor = null;  
	    String column = MediaStore.Images.Media.DATA;  
	    String[] projection = { column };  
	    try {  
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);  
	        if (cursor != null && cursor.moveToFirst()) {  
	            int index = cursor.getColumnIndexOrThrow(column);  
	            return cursor.getString(index);  
	        }  
	    } finally {  
	        if (cursor != null)  
	            cursor.close();  
	    }  
	    return null;  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is ExternalStorageProvider. 
	 */  
	public static boolean isExternalStorageDocument(Uri uri) {  
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is DownloadsProvider. 
	 */  
	public static boolean isDownloadsDocument(Uri uri) {  
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is MediaProvider. 
	 */  
	public static boolean isMediaDocument(Uri uri) {  
	    return "com.android.providers.media.documents".equals(uri.getAuthority());  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is Google Photos. 
	 */  
	public static boolean isGooglePhotosUri(Uri uri) {  
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
	}  
}
