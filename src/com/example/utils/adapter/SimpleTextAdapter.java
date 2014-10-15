package com.example.utils.adapter;

import java.util.Vector;

import com.example.cloudaddressbook.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 简单的ListView 文字列表
 * 要求TextView的id是“text”
 * @author Ren Jiayue
 *
 */
public class SimpleTextAdapter extends BaseAdapter{
	private Vector<String> proNames;
	LayoutInflater mInflater;
	private int mLayoutResourceId;

	public SimpleTextAdapter(Context context, int rid) {
		mInflater = LayoutInflater.from(context);
		proNames = new Vector<String>();
		mLayoutResourceId = rid;
	}
	
	public void addItem(String item){
		proNames.add(item);
	}
	
	public void removeAllItem(){
		proNames.clear();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return proNames.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return proNames.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}
	
	private class ViewHolder{
		public TextView name;
	}
	
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView== null){
			convertView = mInflater.inflate(mLayoutResourceId, null);
			holder = new ViewHolder();
			holder.name = (TextView)convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.name.setText(proNames.get(pos));
		return convertView;
	}

}
