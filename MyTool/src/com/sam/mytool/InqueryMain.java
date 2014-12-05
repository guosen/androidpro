package com.sam.mytool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class InqueryMain extends Activity implements OnClickListener{

	
	TextView item11;//youbian查询
	
	
	TextView item8;//身份证
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		item11=(TextView) findViewById(R.id.item11);
		item8=(TextView) findViewById(R.id.item8);
		item11.setOnClickListener(this);
		item8.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.item11:
			intent=new Intent(InqueryMain.this,CodeInquiryActivity.class);
			overridePendingTransition(R.anim.slide_to_left,R.anim.slide_to_right);
			break;
		case R.id.item8:
			intent=new Intent(InqueryMain.this,IDCardInqueryActivity.class);
			break;

		default:
			break;
		}
		startActivity(intent);
		
	}

}
