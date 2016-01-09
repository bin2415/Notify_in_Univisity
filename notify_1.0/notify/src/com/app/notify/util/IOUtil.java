package com.app.notify.util;

/**
 * @author pangbin
 * 
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.Proxy;

import org.apache.http.HttpConnection;

import android.util.Log;

public class IOUtil {

	private static String TAG = IOUtil.class.getSimpleName();
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	
	public static Bitmap getBitmapLocal(String url)
	{
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap getBitmapRemote(Context ctx,String url)
	{
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			Log.w(TAG,url);
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		try {
			HttpURLConnection conn = null;
			if(HttpUtil.WAP_INT == HttpUtil.getNetType(ctx))
			{
				Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("10.0.0.172", 80)); 				
				conn = (HttpURLConnection) myFileUrl.openConnection(proxy);	
			}
			else {
				conn = (HttpURLConnection) myFileUrl.openConnection();
			}
			
			conn.setConnectTimeout(10000);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 
	 * @param ctx Context
	 * @param updateurl ���ϴ��������ַ
	 * @param srcUrl ����Ҫ�ϴ���ͼƬ�ĵ�ַ
	 * @return �ϴ�ͼƬ�ɹ��ͷ���true�����򷵻�false
	 */
	public static boolean uploadImage(Context ctx,String uploadurl,String srcUrl)
	{
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";	
		URL url = null;
		try {
		    url = new URL(uploadurl);
			Log.w(TAG,uploadurl);
			url = new URL(uploadurl);
		} catch (MalformedURLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		try {
			HttpURLConnection httpURLConnection = null;
			if(HttpUtil.WAP_INT == HttpUtil.getNetType(ctx))
			{
				Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("10.0.0.172", 80)); 				
				httpURLConnection = (HttpURLConnection) url.openConnection(proxy);	
			}
			else {
				httpURLConnection = (HttpURLConnection) url.openConnection();
			}
			
			//����ÿ�δ��������С��������Ч��ֹ�ֻ���Ϊ�ڴ治�����
			//�η���������Ԥ�Ȳ�֪�����ݳ���ʱ����û�н����ڲ������http�������ĵ���
			httpURLConnection.setChunkedStreamingMode(128*1024);
			//�������������
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			//ʹ��post����
			httpURLConnection.setRequestMethod("POST");
		    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");  
		    httpURLConnection.setRequestProperty("Charset", "UTF-8");
		    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		    
		    DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
		    dos.writeBytes(twoHyphens + boundary + end);
		    dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""  
		            + srcUrl.substring(srcUrl.lastIndexOf("/") + 1)  
		            + "\""  
		            + end);
		    dos.writeBytes(end);
		    
		    FileInputStream fis = new FileInputStream(srcUrl);
		    byte[] buffer = new byte[8192]; //8k
		    int count = 0;
		    while((count = fis.read(buffer)) != -1)
		    {
		    	dos.write(buffer,0,count);
		    }
		    fis.close();
		    dos.writeBytes(end);
		    dos.writeBytes(twoHyphens + boundary + twoHyphens + end);  
		    dos.flush();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	public static boolean uploadImage(String actionUrl,Bitmap bitmap,String fileName)
	{
		
		//Log.info(TAG, "urlPath= " + actionUrl);
	      
	      String end ="\r\n";
	      String twoHyphens ="--";
	      String boundary ="*****";
	      
	      HttpURLConnection con = null;
	      DataOutputStream ds = null;
	      InputStream is = null;
	      StringBuffer sb = null;
	      //HashMap<String, Object> map = null;
	      
	      try{
	        URL url =new URL(actionUrl);
	        con=(HttpURLConnection)url.openConnection();
	        /* ����Input��Output����ʹ��Cache */
	        con.setDoInput(true);
	        con.setDoOutput(true);
	        con.setUseCaches(false);
	        
	        /* ���ô��͵�method=POST */
	        con.setRequestMethod("POST");
	        /* setRequestProperty */
	        con.setRequestProperty("Connection", "Keep-Alive");
	        con.setRequestProperty("Charset", "UTF-8");
	        con.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
	        /* ����DataOutputStream */
	        ds = new DataOutputStream(con.getOutputStream());
	        ds.writeBytes(twoHyphens + boundary + end);
	        ds.writeBytes("Content-Disposition: form-data; "+ "name=\"file1\";filename=\""+ fileName +"\""+ end);
	        ds.writeBytes(end);  
	        
	        if(bitmap != null){
	            byte[] buffer = Bitmap2Bytes(bitmap);
	            ds.write(buffer);
	        }
	        
	        ds.writeBytes(end);
	        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	        /* close streams */
	        ds.flush();
	        /* ȡ��Response���� */
	        
	            }
	        catch(FileNotFoundException e){
	            //Log.info("json","file not found");
	        	e.printStackTrace();
	        	return false;
	        }catch (IOException e) {
	            //Log.info("json","io error");
	            e.printStackTrace();
	            return false;
	        } finally{
	            try{
	                if(ds != null){
	                    ds.close();
	                }
	                if(is != null){
	                    is.close();
	                }
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
	      return true;
	    }
		
	 private static byte[] Bitmap2Bytes(Bitmap bm) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
	        return baos.toByteArray();
	    }
}
