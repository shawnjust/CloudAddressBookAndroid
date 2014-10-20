package com.example.utils.adapter;

import java.util.Vector;

import com.example.cloudaddressbook.R;
import com.example.cloudaddressbook.SearchActivity;
import com.example.utils.entities.XunPanItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.utils.views.EditTextDialog;
import com.example.utils.views.EditTextDialog.OnConfirmListener;
/**
 * 显示询盘列表的适配器
 * @author Ren Jiayue
 *
 */
public class SearchItemAdapter extends BaseAdapter{
	private Vector<XunPanItem> datas;
	LayoutInflater mInflater;
	private int mLayoutResourceId;

	
	private Drawable labelFriends;
	private Drawable labelSubMe;
	private Drawable labelMySub;
	private Drawable labelUnknown;
	
	private boolean[] checked;
	private int checkedCount = 0;
	
	public SearchItemAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		datas = new Vector<XunPanItem>();
		mLayoutResourceId = R.layout.search_item;
		labelFriends = context.getResources().getDrawable(R.drawable.cloud);
		labelSubMe = context.getResources().getDrawable(R.drawable.icon_sub_me);
		labelMySub = context.getResources().getDrawable(R.drawable.icon_my_sub);
		labelUnknown = context.getResources().getDrawable(R.drawable.icon_unknown);
		int width,height;
		width = labelFriends.getMinimumWidth();
		height = labelFriends.getMinimumHeight();
		labelFriends.setBounds(0,0,width,height);
		labelSubMe.setBounds(0,0,width,height);
		labelMySub.setBounds(0,0,width,height);
		labelUnknown.setBounds(0,0,width,height);
	}
	
	public void addItem(XunPanItem item){
		datas.add(item);
	}
	
	public void removeAllItem(){
		datas.clear();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return datas.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}
	
	private class ViewHolder{
		public TextView name;
		public TextView email;
		public Button addBtn;
	}
	
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView== null){
			convertView = mInflater.inflate(mLayoutResourceId, null);
			holder = new ViewHolder();
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.email = (TextView)convertView.findViewById(R.id.email);
			holder.name.setCompoundDrawables(labelUnknown, null, null, null);
			holder.addBtn = (Button)convertView.findViewById(R.id.add_friend_btn);
			convertView.setBackgroundResource(R.drawable.item_bg);
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.name.setText(datas.get(pos).getProductName());
		holder.email.setText(datas.get(pos).geteMail());
		holder.addBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				final Button b = (Button)btn;
				EditTextDialog dialog = new EditTextDialog(mInflater.getContext());
				dialog.setOnConfirmListener(new OnConfirmListener() {
					
					@Override
					public void OnConfirm(String text) {
						// TODO Auto-generated method stub
						b.setText("等待验证");
						b.setEnabled(false);
					}
				});
				dialog.show();
			}
		});
		return convertView;
	}
	
	/**
	 * 释放图片资源
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		labelFriends.setCallback(null);
		labelMySub.setCallback(null);
		labelSubMe.setCallback(null);
		labelUnknown.setCallback(null);
		super.finalize();
	}
	
	/**
	 * 更新选中数组的长度（在列表长度变化时需要调用）
	 */
	public void updateCheckedArraySize(){
		if(checked!=null){
			int newsize = datas.size()>checked.length?datas.size():checked.length;
			boolean[] newchecked = new boolean[newsize];
			for(int i = 0 ; i<checked.length && i<newsize ; i++){
				newchecked[i] = checked [i];
			}
			checked = newchecked;
		}
	}
	
}
