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

	//点击小图片显示和因此菜单
	public void showMenu(View v){
		boolean isShowMenu = slideview.isShowMenu();
		if(isShowMenu){
			//菜单处于显示状态，点击隐藏菜单
			slideview.hideMenu();
		}else{
			//菜单处于隐藏状态，点击显示菜单
			slideview.showMenu();
		}
	}

}
