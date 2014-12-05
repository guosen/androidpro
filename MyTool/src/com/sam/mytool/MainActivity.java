package com.sam.mytool;

import java.util.regex.Pattern;

import com.sam.model.zipcode;
import com.sam.util.DatabaseHelper;
import com.sam.util.IDCardUtil;
import com.sam.util.UseDatabase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	
	private EditText editIDCard;//����Ҫ��ѯ�����֤����
	
	private Button btnID;//��ʼ��ѯ���֤����
	
	private String IDCard;//����
	
	/******�������벿��*/
	private EditText editZipCode;
	
	private Button btnZip;
	
	private String zipcode;
	
	
	
    private EditText editZipplace;
	
	private Button btnZipByPlace;
	
	private String place;
	
	private UseDatabase usedatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		usedatabase=new UseDatabase(this);
		initView();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	void initView(){
		editIDCard=(EditText) findViewById(R.id.editIDCard);
		editZipCode=(EditText) findViewById(R.id.editZipCode);
		editZipplace=(EditText) findViewById(R.id.editZipPlace);
		btnZipByPlace=(Button) findViewById(R.id.btnZipByplace);
		
				
		
		btnID=(Button) findViewById(R.id.btnID);
		btnZip=(Button) findViewById(R.id.btnZip);
		
		btnID.setOnClickListener(this);
        btnZip.setOnClickListener(this);
        btnZipByPlace.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnID://chaxunIDCard
			IDCard=editIDCard.getText().toString();
			if(IDCard.length()!=15&&IDCard.length()!=18){
				Toast.makeText(this,"��������ȷ���֤����", Toast.LENGTH_LONG).show();
			}
			else{
				
				IDCard(IDCard);
			}
			
			break;
		case R.id.btnZip:
			zipcode=editZipCode.getText().toString();
			if(zipcode.equals("")||zipcode.length()<6){
				Toast.makeText(this,"��������ȷ���ʱ�", Toast.LENGTH_LONG).show();
			}
			else{
				usedatabase.opendb(this);
				zipcode zip=usedatabase.getZipByZipcode(zipcode);
				Toast.makeText(this,"�鵽���ǣ�"+zip.getProvinceName()+"    "+zip.getCityName(), Toast.LENGTH_LONG).show();
				usedatabase.closedb(this);
			}
			break;
			
		case R.id.btnZipByplace:
			place=editZipplace.getText().toString();
			
			if("".equals(place)){
				
				Toast.makeText(this,"���������", Toast.LENGTH_SHORT).show();
				
			}else{
				usedatabase.opendb(this);
				zipcode zip=usedatabase.getZipCodeByPlaceName(place);
				Toast.makeText(this,"�鵽��������������Ϣ�ǣ�"+zip.getZipCode()+"    "+zip.getCityName(), Toast.LENGTH_LONG).show();
				usedatabase.closedb(this);
			}
			break;
		default:
			break;
		}
		
	}
	public void IDCard(String id){
		
		//350301 19910612 0419  ������֤�Ƿ���Ч================================================
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
			Toast.makeText(this, "��Ч���֤��������������������������",Toast.LENGTH_LONG).show();
		}
		else{
		String year=id.substring(6, 10);
		
		String month=id.substring(10,12);
		
		String day=id.substring(12, 14);
		
		String sex=String.valueOf(id.charAt(16));
		
		String card=id.substring(0, 6);
		if(Integer.parseInt(sex)%2!=0){
		  sex="��";
		}else{
			sex="Ů";
		}
		String str="�Ա�"+sex+"    "+year+"��"+month+"��"+day+"��";
		usedatabase.opendb(this);
		zipcode zip=usedatabase.getInfoByIDCard(card);
		Toast.makeText(this,"�鵽����Ϣ�ǣ�"+str+zip.getProvinceName()+"    "+zip.getCityName()+zip.getCountryName(), Toast.LENGTH_LONG).show();
		usedatabase.closedb(this);
		
		
		Toast.makeText(this, str,Toast.LENGTH_LONG).show();
		}
		
	}else{
		Toast.makeText(this, "��Ч���֤��������������������������",Toast.LENGTH_LONG).show();
		
	}
	}
}
