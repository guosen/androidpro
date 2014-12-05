package com.sam.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
  //  private static final String DB_NAME = "ZipIp.db"; //数据库名称 zipcpde
    
    private static final String DB_NAME = "zibdb.s3db"; 
    private static final int version = 1; //数据库版本
    
    
    
    private static String ASSETS_NAME = "zibdb.s3db";

    private static String DB_PATH="/data/data/com.sam.mytool/";
    private final Context myContext;

    
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
        myContext=context;
        // TODO Auto-generated constructor stub
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
       copyData();
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
 
    }
    private void copyBigDataBase() throws IOException{
    	InputStream myInput;
    	String outFileName = DB_PATH + DB_NAME;
    	FileOutputStream myOutput = new FileOutputStream(outFileName);
    	myInput = myContext.getAssets().open(ASSETS_NAME);
    	byte[] buffer = new byte[1024*200];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    	myOutput.write(buffer, 0, length);
    	}
    	/*myOutput.flush();*/
    	myInput.close();
    	
    	myOutput.close();
    	System.out.println("数据库已经复制");
    	}
    
    private void copyData() {		
		try {
			AssetManager m = myContext.getResources().getAssets();
			InputStream is = m.open("zibdb.s3db");              
			File file = new File(Environment.getDataDirectory(),"data/"+myContext.getPackageName()+"/zidb.s3db");
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[1024*10];
			int len = -1;
			while((len=is.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}
			fos.close();
			m.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


 
}
