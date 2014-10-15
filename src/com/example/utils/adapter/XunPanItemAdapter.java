package com.example.utils.adapter;

import java.util.Vector;

import com.example.cloudaddressbook.R;
import com.example.utils.entities.XunPanItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
/**
 * 显示询盘列表的适配器
 * @author Ren Jiayue
 *
 */
public class XunPanItemAdapter extends BaseAdapter{
	private Vector<XunPanItem> datas;
	LayoutInflater mInflater;
	private int mLayoutResourceId;
	private OnListCheckBoxStateChanged checkListener;

	
	private Drawable labelReaded;
	private Drawable labelUnreaded;
	
	private boolean[] checked;
	private int checkedCount = 0;
	
	public XunPanItemAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		datas = new Vector<XunPanItem>();
		mLayoutResourceId = R.layout.xunpan_item;
		labelReaded = context.getResources().getDrawable(R.drawable.label_readed);
		labelUnreaded = context.getResources().getDrawable(R.drawable.label_unreaded);
		int width,height;
		width = labelReaded.getMinimumWidth();
		height = labelReaded.getMinimumHeight();
		labelReaded.setBounds(0,0,width,height);
		labelUnreaded.setBounds(0,0,width,height);
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
		public TextView pruductName;
		public TextView date;
		public TextView eMail;
		public CheckBox checkBox;
	}
	
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView== null){
			convertView = mInflater.inflate(mLayoutResourceId, null);
			holder = new ViewHolder();
			holder.pruductName = (TextView)convertView.findViewById(R.id.product_name);
			holder.date = (TextView)convertView.findViewById(R.id.date);
			holder.eMail = (TextView)convertView.findViewById(R.id.email);
			holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkbox);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.pruductName.setText(datas.get(pos).getProductName());
		holder.date.setText(datas.get(pos).getTime());
		holder.eMail.setText(datas.get(pos).geteMail());
		holder.checkBox.setOnCheckedChangeListener(null);
		if(checked!=null){
			holder.checkBox.setChecked(checked[pos]);
			if(checked[pos]){
				convertView.setBackgroundResource(R.drawable.item_bg_selected);
			}else{
				convertView.setBackgroundResource(R.drawable.item_bg);
			}
		}else{
			holder.checkBox.setChecked(false);
			convertView.setBackgroundResource(R.drawable.item_bg);
		}

		holder.checkBox.setOnCheckedChangeListener(new MyOnCheckedChangeListener(pos));
		
		if(datas.get(pos).isRead()){
			holder.pruductName.setCompoundDrawables(labelReaded, null, null, null);
		}else{
			holder.pruductName.setCompoundDrawables(labelUnreaded, null, null, null);
		}
		return convertView;
	}
	
	private class MyOnCheckedChangeListener implements OnCheckedChangeListener{
		private int pos;
		public MyOnCheckedChangeListener(int pos){
			this.pos = pos;
		}
		@Override
		public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
			// TODO Auto-generated method stub
			
			if(isChecked){
				if(checkedCount == 0){
					checkStart();
				}
				checkedCount++;
				checked[pos] = true;
				View container = (View)btn.getParent();
				container.setBackgroundResource(R.drawable.item_bg_selected);
			}else{
				checkedCount--;
				checked[pos] = false;
				if(checkedCount == 0&&!isChecked){
					clearCheck();
				}
				View container = (View)btn.getParent();
				container.setBackgroundResource(R.drawable.item_bg);
			} 
			if(checkListener!=null){
				checkListener.onCheckedChanged(pos, isChecked);
			}
		}
		
	}
	private void checkStart(){
		checked = new boolean[datas.size()];
		if(checkListener!=null){
			checkListener.onFirstItemChecked();
		}
	}
	
	private void clearCheck() {
		checked = null;
		checkedCount = 0;
		if(checkListener!=null){
			checkListener.onNothingChecked();
		}
	}
	/**
	 * 结束多选，清空选中状态
	 */
	public void finishCheck(){
		checked = null;
		checkedCount = 0;
		notifyDataSetChanged();
	}
	/**
	 * 获得选中情况
	 * @return 与list长度相等的一个
	 */
	public boolean[] getChecked(){
		return checked;
	}
	
	public OnListCheckBoxStateChanged getCheckListener() {
		return checkListener;
	}

	public void setCheckListener(OnListCheckBoxStateChanged checkListener) {
		this.checkListener = checkListener;
	}

	public interface OnListCheckBoxStateChanged{
		public void onCheckedChanged(int pos, boolean isChecked);
		public void onNothingChecked();
		public void onFirstItemChecked();
	}

	public int getSelectedCount(){
		return checkedCount;
	}
	/**
	 * 释放图片资源
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		labelReaded.setCallback(null);
		labelUnreaded.setCallback(null);
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
