package com.example.a1409_viewgroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	private SlideView slideview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		slideview = (SlideView) findViewById(R.id.slideview);
				
	}

	//���СͼƬ��ʾ����˲˵�
	public void showMenu(View v){
		boolean isShowMenu = slideview.isShowMenu();
		if(isShowMenu){
			//�˵�������ʾ״̬��������ز˵�
			slideview.hideMenu();
		}else{
			//�˵���������״̬�������ʾ�˵�
			slideview.showMenu();
		}
	}

}
