package com.sam.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	//�õ�SD��·��
	private final String DATABASE_PATH = "/data/data/com.sam.mytool/databases";
	private final Activity activity;
	//���ݿ���
	private final String DATABASE_FILENAME;
	public DBHelper(Context context) {
	    // TODO Auto-generated constructor stub
	//����ֱ�Ӹ����ݿ���
	  DATABASE_FILENAME = "zibdb.s3db";
	  activity = (Activity)context;
	} 
	//�õ��������ݿ�Ķ���
	public  SQLiteDatabase openDatabase()
	{
	  try
	  {
	   boolean b = false;
	   //�õ����ݿ������·����
	   String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
	//�����ݿ��ļ�����Դ�ļ��ŵ����ʵط�����Դ�ļ�Ҳ�������ݿ��ļ�������Ŀ��res�µ�rawĿ¼�У�
	   //�����ݿ��ļ����Ƶ�SD���� 
	   File dir = new File(DATABASE_PATH);
	   if (!dir.exists())
	     b = dir.mkdir();
	   //�ж��Ƿ���ڸ��ļ�
	   if (!(new File(databaseFilename)).exists())
	   {     
	    //�����ڵõ����ݿ�����������
		   AssetManager m = activity.getResources().getAssets();
			InputStream is = m.open("zibdb.s3db"); 
	    //���������
	    FileOutputStream fos = new FileOutputStream(databaseFilename);
	    //���������
	    byte[] buffer = new byte[8192];
	    int count = 0;
	    while ((count = is.read(buffer)) > 0)
	    {
	     fos.write(buffer, 0, count);
	    }
	    //�ر���Դ
	    fos.close();
	    is.close();
	   }
	//�õ�SQLDatabase����
	   SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
	     databaseFilename, null);   
	   return database;   
	  }
	  catch (Exception e)
	  {
	   System.out.println(e.getMessage());
	  }
	  return null;
	}
	}
