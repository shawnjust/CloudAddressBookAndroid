package com.example.utils.adapter;

import com.example.cloudaddressbook.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 显示历史记录的列表的适配器，关联本地数据库的历史记录表
 * @author Ren Jiayue
 *
 */
public class HistoryRecordCursorAdapter extends CursorAdapter{
	private static String TAG = "HistoryRecordCursorAdapter";
	private LayoutInflater inflater;
	private Drawable his_icon;
	
	
	@SuppressWarnings("deprecation")
	public HistoryRecordCursorAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
	}
	
	
	
	public HistoryRecordCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		Resources res = inflater.getContext().getResources();
		his_icon = res.getDrawable(R.drawable.history_icon);

		his_icon.setBounds(0, 0, his_icon.getMinimumWidth(), his_icon.getMinimumHeight());
	}
	
	/**项目个数**/
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}
	
	
	/**设置AutoCompleteText里显示的内容**/
	@Override  
    public CharSequence convertToString(Cursor cursor)  
    {  
		try{
	       return cursor == null ? "" : cursor.getString(1); 
	   }catch(CursorIndexOutOfBoundsException ce){
				Log.e(TAG, ce.toString());
				return "";
		} 
		
    } 
	
	/**类似于getView,父类的getView会调动这个函数**/
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		Log.i(TAG, "bindView:"+cursor.getString(1));
		setChildView(view, cursor);
		cursor.moveToNext();
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search_tip_his_item, null);
		Log.i(TAG, "newView:"+cursor.getString(1));
		setChildView(view, cursor);
		return view;
	}
	
	@SuppressWarnings("null")
	public void setChildView(View view, Cursor cursor){
		TextView name = (TextView) view.findViewById(R.id.text);
		
		name.setCompoundDrawables(his_icon, null, null, null); //设置左图标
		if(cursor!=null||cursor.getCount()==0){
			try{
			if(cursor.getString(1)!=null)
				name.setText(cursor.getString(1));
				Log.i(TAG, "setChildView:"+cursor.getString(1));
			}catch(CursorIndexOutOfBoundsException ce){
				Log.e(TAG, ce.toString());
			}
		}
	}
}
