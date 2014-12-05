package com.sam.mytool;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sam.adapter.listviewadapter;
import com.sam.model.zipcode;
import com.sam.util.UseDatabase;

public class CodeInquiryActivity  extends Activity implements OnClickListener{

	
	private EditText editText_input;
	
	private Button inquiry_bottom;
	
	private ListView list;
	
	private ArrayList<zipcode>listZipcodes;
	
	private UseDatabase usedatabase;
	
	listviewadapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.code_inquiry_layout_1);
		usedatabase=new UseDatabase(this);
		initView();
		
		
	}
	public void initView(){
		editText_input=(EditText) findViewById(R.id.editText_input);
		inquiry_bottom=(Button) findViewById(R.id.inquiry_bottom);
		inquiry_bottom.setOnClickListener(this);
		list=(ListView) findViewById(R.id.listView1);
		
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.inquiry_bottom:
			if(editText_input.getText().toString().equals("")){
				Toast.makeText(this,"请输入邮编", Toast.LENGTH_LONG).show();
			
			}
			else{
				 usedatabase.opendb(this);
				 Pattern pattern = Pattern.compile("[0-9]*"); 
				 boolean isnum= pattern.matcher(editText_input.getText().toString()).matches();   
				 if(isnum){
					 //如果用户输入的全部为数字那么就按照邮编号码进行查询
					 listZipcodes=usedatabase.getZipCodeListByCode(editText_input.getText().toString()); 
					 
				 }
				 else{
				
				listZipcodes=usedatabase.getZipCodeListByPlaceName(editText_input.getText().toString());
				//Toast.makeText(this,"查到的是邮政编码信息是："+zip.getZipCode()+"    "+zip.getCityName(), Toast.LENGTH_LONG).show();
				 }
				adapter=new listviewadapter(this, listZipcodes);
				list.setAdapter(adapter);
				usedatabase.closedb(this);
			}
			
			break;

		default:
			break;
		}
	}

	
	
	
	
	
}
