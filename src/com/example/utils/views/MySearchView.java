package com.example.utils.views;

import com.example.cloudaddressbook.R;
import com.example.utils.adapter.HistoryRecordCursorAdapter;
import com.example.utils.sqlite.ETWLocalDataBase;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
/**
 * ActionBar SearchView 通过布局文件 actionbar_search实例化
 * 必须先调用init方法
 * 外观可自定义
 * 提供接口 OnQueryTextListener
 * 	public boolean onQueryTextSubmit(String query);
		public boolean onQueryTextChange(String newText);
		public void onBackPressed();
 * 响应搜索和搜索提示
 * @author admin
 *
 */
public class MySearchView extends LinearLayout{
	private ImageView closeSearchBtn;
	private AutoCompleteTextView editField;
	private ImageView searchBtn;
	private OnQueryTextListener listener;
	private Context context;
	private ETWLocalDataBase db;
	private Cursor searchTipCursor;
	private HistoryRecordCursorAdapter mSearchTipAdapter;
	
	
	public MySearchView(Context context) {
		this(context,null);
		this.context = context;
		db = ETWLocalDataBase.getInstance(context);
	}
	
	public MySearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		db = ETWLocalDataBase.getInstance(context);
		// TODO Auto-generated constructor stub
		
	}
	public void init(){
		closeSearchBtn = (ImageView) findViewById(R.id.search_close_btn);
		searchBtn = (ImageView) findViewById(R.id.search_go_btn);
		editField = (AutoCompleteTextView)findViewById(R.id.search_src_text);
		
		closeSearchBtn.setVisibility(View.GONE);
		
		closeSearchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editField.setText("");
			}
		});
		
		editField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				//Log.i("a","text:"+s);
				if(s.length()==0){
					closeSearchBtn.setVisibility(View.GONE);
				}else if(closeSearchBtn.getVisibility() == View.GONE){
					closeSearchBtn.setVisibility(View.VISIBLE);
				}
				searchTipCursor = db.getHistoryProductCursor(s.toString(),3);
				mSearchTipAdapter.swapCursor(searchTipCursor);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2,
					int after) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void afterTextChanged(Editable text) {
				// TODO Auto-generated method stub
				if(listener!=null){
					listener.onQueryTextChange(text.toString());
				}
			}
		});
		
		editField.setOnEditorActionListener(new OnEditorActionListener() {
			
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					if (listener != null) {
						String key = v.getText().toString();
						listener.onQueryTextSubmit(key);
						if(!key.equals("")){
							db.increaseHistoryRank(key);
						}
					}
					return true;
				}
				return false;
			}
		});

		searchTipCursor = db.getHistoryProductCursor(null,3);
		mSearchTipAdapter = new HistoryRecordCursorAdapter(context, 
				searchTipCursor,true);
		editField.setAdapter(mSearchTipAdapter);
		
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(listener!=null){
					String key = editField.getText().toString();
					listener.onQueryTextSubmit(key);
					if(!key.equals("")){
						db.increaseHistoryRank(key);
					}
					editField.requestFocus();
					editField.setActivated(true);
				}
			}
		});
	}
	
	/**
	 * 外部调用"查询改变"接口的方法
	 * @param newText 搜索的关键字
	 */
	public void callonQueryTextChange(String newText){
		if(listener!=null){
			listener.onQueryTextSubmit(editField.getText().toString());
		}
	}
	public void setOnQueryTextListener(OnQueryTextListener l){
		listener = l;
	}
	public interface OnQueryTextListener{
		public boolean onQueryTextSubmit(String query);
		public boolean onQueryTextChange(String newText);
	}
	/**
	 * 设置搜索框里显示的文字
	 * @param text 搜索框里要显示的文字
	 */
	public void setSearchViewText(String text){
		editField.setText(text);
	}
	
	
}
