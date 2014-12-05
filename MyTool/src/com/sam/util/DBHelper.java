package com.sam.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	//得到SD卡路径
	private final String DATABASE_PATH = "/data/data/com.sam.mytool/databases";
	private final Activity activity;
	//数据库名
	private final String DATABASE_FILENAME;
	public DBHelper(Context context) {
	    // TODO Auto-generated constructor stub
	//这里直接给数据库名
	  DATABASE_FILENAME = "zibdb.s3db";
	  activity = (Activity)context;
	} 
	//得到操作数据库的对象
	public  SQLiteDatabase openDatabase()
	{
	  try
	  {
	   boolean b = false;
	   //得到数据库的完整路径名
	   String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
	//将数据库文件从资源文件放到合适地方（资源文件也就是数据库文件放在项目的res下的raw目录中）
	   //将数据库文件复制到SD卡中 
	   File dir = new File(DATABASE_PATH);
	   if (!dir.exists())
	     b = dir.mkdir();
	   //判断是否存在该文件
	   if (!(new File(databaseFilename)).exists())
	   {     
	    //不存在得到数据库输入流对象
		   AssetManager m = activity.getResources().getAssets();
			InputStream is = m.open("zibdb.s3db"); 
	    //创建输出流
	    FileOutputStream fos = new FileOutputStream(databaseFilename);
	    //将数据输出
	    byte[] buffer = new byte[8192];
	    int count = 0;
	    while ((count = is.read(buffer)) > 0)
	    {
	     fos.write(buffer, 0, count);
	    }
	    //关闭资源
	    fos.close();
	    is.close();
	   }
	//得到SQLDatabase对象
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
