package com.example.cloudaddressbook;

import java.util.ArrayList;

import com.example.utils.adapter.SearchItemAdapter;
import com.example.utils.entities.XunPanItem;
import com.example.utils.utils.NetworkState;
import com.example.utils.views.MySearchView;
import com.example.utils.views.MySearchView.OnQueryTextListener;
import com.example.utils.webservice.ConnectServer;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

public class SearchActivity extends com.example.utils.abstractActivities.PageListActivity {

	private LinearLayout progressBar;

	private MySearchView actionbarSearch;
	private TaskSchedule<Integer, Void, ArrayList<XunPanItem>> taskSchedule;
	
	private String searchKey;
	private ConnectServer connect;
	private SearchItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		connect = ConnectServer.getInstance();
		View customView = LayoutInflater.from(this).inflate(R.layout.actionbar_search_view, null);
		actionbarSearch = (MySearchView)customView.findViewById(R.id.actionbar_search);
		actionbarSearch.init();
		actionbarSearch.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.VISIBLE);
				taskSchedule.newTask(new SearchItemsTask(query), 1);
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL|Gravity.LEFT);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(customView,params);
		progressBar = (LinearLayout)findViewById(R.id.progressBar);
		taskSchedule = new TaskSchedule<Integer, Void, ArrayList<XunPanItem>>();
		//taskSchedule.newTask(new SearchItemsTask(""), 1);
		progressBar.setVisibility(View.GONE);
	}

	/**
	 * 根布局文件ID
	 */
	@Override
	protected int getActivityLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.activity_search;
	}

	/**
	 * 布局文件中需要分页加载的LISTVIEW的ID
	 */
	@Override
	protected int getListViewId() {
		// TODO Auto-generated method stub
		return R.id.xunpan_list;
	}
	/**
	 * 布局文件中需要分页加载的LISTVIEW的容器的ID
	 */
	@Override
	protected int getListViewContainerId() {
		// TODO Auto-generated method stub
		return R.id.list_container;
	}
	/**
	 * 布局文件中显示加载状态的TextView的id
	 */
	@Override
	protected int getLoadStateTextId() {
		// TODO Auto-generated method stub
		return R.id.load_next_page_text;
	}
	
	

	/**
	 * 开始加载下一页的实现
	 */
	@Override
	protected void startLoadNextPage(int pageNum) {
		// TODO Auto-generated method stub
		taskSchedule.newTask(new SearchItemsTask(searchKey), 2);
	}

	/**
	 * 获得listview的适配器
	 */
	@Override
	protected ListAdapter getListAdapter() {
		// TODO Auto-generated method stub
		adapter = new SearchItemAdapter(this);
		return adapter;
	}
	
	/**
	 * 异步加载询盘列表的线程
	 * @author Jiayue Ren
	 * execute参数：
	 * type 是重新加载（value=1）还是加载下一页（value=0）
	 * filter 筛选参数，这个可自行定义自行解析
	 */
	public class SearchItemsTask extends AsyncTask<Integer, Void, ArrayList<XunPanItem>>{
		private int type;
		private String key;
		public SearchItemsTask(String searchKey){
			key = searchKey;
		}
		@Override
		protected ArrayList<XunPanItem> doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			if(!NetworkState.isNetworkConnected(SearchActivity.this)){
				progressBar.setVisibility(View.GONE);
				return null;
			}
			type = arg0[0];
			if(type == 1){
				pageNum = 0;
			}else{
				pageNum++;
			}
			return connect.searchItem(pageNum, key);
		}
		@Override
		protected void onPostExecute(ArrayList<XunPanItem> result) {
			// TODO Auto-generated method stub
			if(result==null){
				Toast.makeText(SearchActivity.this, R.string.error_connect_failed, Toast.LENGTH_SHORT).show();
			}else{
				if(type == 1){
					adapter.removeAllItem();
				}
				for(XunPanItem item:result){
					adapter.addItem(item);
				}
				adapter.updateCheckedArraySize();
				adapter.notifyDataSetChanged();
				
			}
			if(type == 1){
				scrollToFirst();
			}else{
				onTaskDone(getString(R.string.load_success));
			}
			taskSchedule.taskFinished();
			progressBar.setVisibility(View.GONE);
		}
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			taskSchedule.taskFinished();
			//displayUpdateView(false);
			if(type == 2){
				onTaskDone(getString(R.string.load_success));
			}
			
			progressBar.setVisibility(View.GONE);
			super.onCancelled();
		}
	} 
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			this.finish();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
