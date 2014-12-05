package com.sam.mytool;

import java.util.regex.Pattern;

import com.sam.model.zipcode;
import com.sam.util.IDCardUtil;
import com.sam.util.UseDatabase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IDCardInqueryActivity extends Activity implements OnClickListener{
    private EditText input_id;
    
    private Button  inquiry;
    
    
    private TextView sex;
    
    private TextView birthday;
    
    
    private TextView address;
    private UseDatabase usedatabase;
    
    Button  button_back;//返回
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.id_inquiry_layout_1);
		usedatabase=new UseDatabase(this);
		initView();
	}
      
	void initView(){
		input_id=(EditText) findViewById(R.id.input_id);
		
		inquiry=(Button) findViewById(R.id.inquiry);
		
		sex=(TextView) findViewById(R.id.sex);
		
		birthday=(TextView) findViewById(R.id.birthday);
		
		address=(TextView) findViewById(R.id.address);
		button_back=(Button) findViewById(R.id.button_back);
		inquiry.setOnClickListener(this);
		button_back.setOnClickListener(this);
		
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.inquiry:
			String idcard=input_id.getText().toString();
			
			if(idcard.length()!=15&&idcard.length()!=18){
				Toast.makeText(this,"请输入正确身份证号码", Toast.LENGTH_LONG).show();
			}
			else{
				
				IDCard(idcard);
			}
				
			break;
			
		case R.id.button_back:
			
			onBackPressed();
			break;

		default:
			break;
		}
		
	}
   public void IDCard(String id){
		
		//350301 19910612 0419  检测身份证是否有效================================================
		int []num17=new int[17];
		String str17=id.substring(0,17);
		
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		 boolean isnum= pattern.matcher(str17).matches();    
		 
		if(isnum){
		for(int i=0;i<str17.length();i++){
			num17[i]=Integer.parseInt(String.valueOf(str17.charAt(i)));
			
		}
		String jianyan=IDCardUtil.getVerify(num17);
		if(!jianyan.equals(String.valueOf(id.charAt(17)))){
			Toast.makeText(this, "无效身份证！！！！！！！！！！！！！",Toast.LENGTH_LONG).show();
		}
		else{
		String year=id.substring(6, 10);
		
		String month=id.substring(10,12);
		
		String day=id.substring(12, 14);
		
		String lsex=String.valueOf(id.charAt(16));
		
		String card=id.substring(0, 6);
		if(Integer.parseInt(lsex)%2!=0){
		  lsex="男";
		}else{
			lsex="女";
		}
		String str="性别："+sex+"    "+year+"年"+month+"月"+day+"日";
		sex.setText(lsex);
		
		birthday.setText(year+"年"+month+"月"+day+"日");
		
		
		
		usedatabase.opendb(this);
		zipcode zip=usedatabase.getInfoByIDCard(card);
		address.setText(zip.getProvinceName()+zip.getCityName()+zip.getCountryName());
	//	Toast.makeText(this,"查到的信息是："+str+zip.getProvinceName()+"    "+zip.getCityName()+zip.getCountryName(), Toast.LENGTH_LONG).show();
		usedatabase.closedb(this);
		
		
		Toast.makeText(this, str,Toast.LENGTH_LONG).show();
		}
		
	}else{
		Toast.makeText(this, "无效身份证！！！！！！！！！！！！！",Toast.LENGTH_LONG).show();
		
	}
	}
	
}
