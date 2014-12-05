package com.sam.adapter;

import java.util.ArrayList;

import com.sam.model.zipcode;
import com.sam.mytool.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class listviewadapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<zipcode>list;
	
	
	
	public listviewadapter(Context context,ArrayList<zipcode>listzip){
		
		this.context=context;
		this.list=listzip;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.code_result_item_layout,null);
		}
		TextView addr=(TextView) convertView.findViewById(R.id.textView1);
		TextView code=(TextView) convertView.findViewById(R.id.textView2);
		zipcode zip=list.get(position);
		addr.setText("µØÇø:"+zip.getProvinceName()+zip.getCityName()+zip.getCountryName());
		code.setText("ÓÊ±à£º"+zip.getZipCode());
		
		return convertView;
	}

	
	
}
