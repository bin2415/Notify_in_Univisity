package com.app.notify.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Comparator;

import com.app.notify.base.C;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class SDUtil {
	
	private static String TAG = SDUtil.class.getSimpleName();

	private static double MB = 1024;
	private static double FREE_SD_SPACE_NEEDED_TO_CACHE = 10;
	private static double IMAGE_EXPIRE_TIME = 10;
	
	/**
	 * 
	 * @param fileName
	 * @return ��������ļ��򷵻�Bitmap�ļ������򷵻ؿ�
	 */
	
	public static Bitmap getImage(String fileName)
	{
		
		//����ļ��Ƿ����
		String realFileName = C.dir.faces + "/" +fileName;
		File file = new File(realFileName);
		if(!file.exists())
			return null;
		
		//�õ�ԭʼ��ͼƬ�ļ�
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(realFileName,options);
		
	}
	
	/**
	 * �õ��涨��С���ļ�
	 * @param String fileName
	 * @return ��������ļ��򷵻�Bitmap�ļ������򷵻ؿ�
	 */
	
	public static Bitmap getSample(String fileName)
	{
		
		//����ļ��Ƿ����
		String realFileName = C.dir.faces + "/" + fileName;
		File file = new File(realFileName);
		if(!file.exists())
		{
			return null;
		}
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		
		//��ѡ��ֻ�ǻ��ͼƬ�ĳ��͸�
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(realFileName,options);
		int zoom = (int)(options.outHeight / (float) 50);
		if(zoom < 0)
			zoom = 1;
		options.inSampleSize = zoom;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(realFileName,options);
		return bitmap;
	}
	
	/**
	 * ��bitmap��ʽ��ͼƬ
	 * @param bitmap
	 * @param fileName
	 * @return null
	 */
	public static void saveImage(Bitmap bitmap,String fileName)
	{
		if(bitmap == null)
		{
			Log.w(TAG,"trying to save null bitmap");
		}
		//�ж�sdcard�ϵĿռ�
		if(FREE_SD_SPACE_NEEDED_TO_CACHE > getFreeSpace())
		{
			Log.w(TAG,"Low free space onsd, do not cache");
		}
		
		// �������򴴽�Ŀ¼
				File dir = new File(C.dir.faces);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				// ����ͼƬ
				try {
					String realFileName = C.dir.faces + "/" + fileName;
					File file = new File(realFileName);
					file.createNewFile();
					OutputStream outStream = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
					outStream.flush();
					outStream.close();
					Log.i(TAG, "Image saved tosd");
				} catch (FileNotFoundException e) {
					Log.w(TAG, "FileNotFoundException");
				} catch (IOException e) {
					Log.w(TAG, "IOException");
				}
	}
	
	protected static void updateTime(String fileName) {
		File file = new File(fileName);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}
	
	/**
	 * ����sdcard�ϵ�ʣ��ռ�
	 * 
	 * @return
	 */
	public static int getFreeSpace() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}
	
	public static void removeExpiredCache(String dirPath, String filename) {
		File file = new File(dirPath, filename);
		if (System.currentTimeMillis() - file.lastModified() > IMAGE_EXPIRE_TIME) {
			Log.i(TAG, "Clear some expiredcache files ");
			file.delete();
		}
	}

	public static void removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > getFreeSpace()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifSort());
			Log.i(TAG, "Clear some expiredcache files ");
			for (int i = 0; i < removeFactor; i++) {
				files[i].delete();
			}

		}

	}

	private static class FileLastModifSort implements Comparator<File> {
		@Override
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}
