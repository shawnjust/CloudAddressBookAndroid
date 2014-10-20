package com.example.cloudaddressbook;

import java.util.ArrayList;

import com.example.utils.adapter.XunPanItemAdapter;
import com.example.utils.adapter.XunPanItemAdapter.OnListCheckBoxStateChanged;
import com.example.utils.entities.XunPanItem;
import com.example.utils.utils.NetworkState;
import com.example.utils.views.MySearchView;
import com.example.utils.views.MySearchView.OnQueryTextListener;
import com.example.utils.webservice.ConnectServer;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 显示询盘列表信息的界面
 * @author Jiayue Ren
 *
 */
public class BookListActivity extends com.example.utils.abstractActivities.PageListActivity {
	private final static int FILTER_ALL = 0;
	private final static int FILTER_UNREADED = 1;
	private final static int FILTER_READED = 0;
	private int filterNow = 0x0000;
	
	private XunPanItemAdapter adapter;
	private int pageNum;
	private TaskSchedule<Integer, Void, ArrayList<XunPanItem>> taskSchedule;
	private ConnectServer connect;
	private ImageButton updateBtn;
	private ImageButton searchBtn;
	private LinearLayout updateState;
	private ImageButton addFriendsBtn;
	private TextView totalNum;
	
	private LinearLayout normalControlBar;
	private LinearLayout editControlBar;
	private RelativeLayout actionbarNormal;
	private LinearLayout actionbarEdit;
	private TextView selectedNum;
	private MySearchView actionbarSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_xun_pan);
		
		normalControlBar = (LinearLayout)findViewById(R.id.normal_bar);
		editControlBar = (LinearLayout)findViewById(R.id.edit_bar);
		
		addFriendsBtn =  (ImageButton)findViewById(R.id.add_friend_btn);
		addFriendsBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				intent.setClass(BookListActivity.this, SearchActivity.class);
				startActivityForResult(intent, 300);
			}
		});
		
		
		updateBtn = (ImageButton)findViewById(R.id.update_btn);
		updateState = (LinearLayout)findViewById(R.id.update_state);
		updateBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				displayUpdateView(true);
				taskSchedule.newTask(new GetXunPanItemsTask(), 1,filterNow);
			}
		});
		
		searchBtn = (ImageButton)findViewById(R.id.search_btn);
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				showSearchBar();
				searchBtn.setVisibility(View.GONE);
			}
		});
		
		adapter.setCheckListener(new OnListCheckBoxStateChanged() {
			
			@Override
			public void onCheckedChanged(int pos, boolean isChecked) {
				// TODO Auto-generated method stub
				selectedNum.setText(getString(R.string.text_selected_num_before)+" "+
				adapter.getSelectedCount()+
				" "+getString(R.string.text_selected_num_after));
			}

			@Override
			public void onNothingChecked() {
				// TODO Auto-generated method stub
				showNormalBar();
			}

			@Override
			public void onFirstItemChecked() {
				// TODO Auto-generated method stub
				showEditBar();
			}
		});
		super.setOnItemCLickListener(new MyOnItemClickListener() {
			
			@Override
			public void onItemClick(Object item) {
				// TODO Auto-generated method stub
				XunPanItem xunpan = (XunPanItem)item;
				Intent intent = getIntent();
				intent.putExtra("item", xunpan);
				intent.setClass(BookListActivity.this, ShowXunpanDetailActivity.class);
				startActivityForResult(intent, 300);
			}
		});
		
		initActionBar();
		//showNormalBar();
		connect = ConnectServer.getInstance();
		taskSchedule = new TaskSchedule<Integer, Void, ArrayList<XunPanItem>>();
		taskSchedule.newTask(new GetXunPanItemsTask(), 1,FILTER_ALL);
	}
	/**
	 * 初始化ActionBar
	 */
	public void initActionBar(){
		View customView = LayoutInflater.from(this).inflate(R.layout.actionbar_filter_view, null);
		actionbarEdit = (LinearLayout)customView.findViewById(R.id.actionbar_edit);
		actionbarNormal = (RelativeLayout)customView.findViewById(R.id.actionbar_control);
		actionbarSearch = (MySearchView)customView.findViewById(R.id.actionbar_search);
		actionbarSearch.init();
		actionbarSearch.setVisibility(View.GONE);
		actionbarSearch.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		totalNum = (TextView)customView.findViewById(R.id.total_num);
		selectedNum = (TextView)actionbarEdit.findViewById(R.id.selected_num);
		ImageButton finishBtn = (ImageButton)actionbarEdit.findViewById(R.id.finish_btn);
		finishBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				adapter.finishCheck();
				showNormalBar();
			}
		});
		Spinner spinner = (Spinner) customView.findViewById(R.id.actionbar_spinner);
		int[] temp = new int[]{23,12,1,14};
		final ActionbarSpinnerAdapter spinnerAdapter = new ActionbarSpinnerAdapter(
				getResources().getStringArray(R.array.xunpan_filters),
				temp
				);
		spinner.setAdapter(spinnerAdapter); 
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				totalNum.setText(""+spinnerAdapter.getTotalNumAt(pos));
				filterNow = (filterNow & 0x11111100)+pos;
				displayUpdateView(true);
				taskSchedule.newTask(new GetXunPanItemsTask(), 1,filterNow);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL|Gravity.LEFT);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(customView,params);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xun_pan, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	/**
	 * 异步加载询盘列表的线程
	 * @author Jiayue Ren
	 * execute参数：
	 * type 是重新加载（value=1）还是加载下一页（value=0）
	 * filter 筛选参数，这个可自行定义自行解析
	 */
	public class GetXunPanItemsTask extends AsyncTask<Integer, Void, ArrayList<XunPanItem>>{
		private int type;
		private int filter;
		@Override
		protected ArrayList<XunPanItem> doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			if(!NetworkState.isNetworkConnected(BookListActivity.this)){
				return null;
			}
			type = arg0[0];
			filter = arg0[1];
			if(type == 1){
				pageNum = 0;
			}else{
				pageNum++;
			}
			return connect.getXunPan(pageNum, filter);
		}
		@Override
		protected void onPostExecute(ArrayList<XunPanItem> result) {
			// TODO Auto-generated method stub
			if(result==null){
				Toast.makeText(BookListActivity.this, R.string.error_connect_failed, Toast.LENGTH_SHORT).show();
			}else{
				if(type == 1){
					adapter.removeAllItem();
					adapter.finishCheck();
					showNormalBar();
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
			displayUpdateView(false);
		}
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			taskSchedule.taskFinished();
			//displayUpdateView(false);
			if(type == 2){
				onTaskDone(getString(R.string.load_success));
			}
			super.onCancelled();
		}
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
			if(!NetworkState.isNetworkConnected(BookListActivity.this)){
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
				Toast.makeText(BookListActivity.this, R.string.error_connect_failed, Toast.LENGTH_SHORT).show();
			}else{
				if(type == 1){
					adapter.removeAllItem();
					adapter.finishCheck();
					showNormalBar();
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
			displayUpdateView(false);
		}
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			taskSchedule.taskFinished();
			//displayUpdateView(false);
			if(type == 2){
				onTaskDone(getString(R.string.load_success));
			}
			super.onCancelled();
		}
	} 
	
	/**
	 * 显示更新的操作栏
	 * @param isLoading 状态是否是正在加载
	 */
	public void displayUpdateView(boolean isLoading){
		if(isLoading){
			updateBtn.setVisibility(View.GONE);
			updateState.setVisibility(View.VISIBLE);
		}else{
			updateState.setVisibility(View.GONE);
			updateBtn.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 根布局文件ID
	 */
	@Override
	protected int getActivityLayoutRes() {
		// TODO Auto-generated method stub
		return R.layout.activity_xun_pan;
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
		displayUpdateView(true);
		taskSchedule.newTask(new GetXunPanItemsTask(), 2,filterNow);
	}

	/**
	 * 获得listview的适配器
	 */
	@Override
	protected ListAdapter getListAdapter() {
		// TODO Auto-generated method stub
		adapter = new XunPanItemAdapter(this);
		return adapter;
	}
	/**
	 * actionbar 下拉菜单的适配器
	 * @author Jiayue Ren
	 */
	private class ActionbarSpinnerAdapter extends ArrayAdapter<String>{
		private String[] names;
		private int[] numbers;
		
		
		public ActionbarSpinnerAdapter(String[] name, int[] num) {
			super(BookListActivity.this, R.layout.spinner_item, android.R.layout.simple_spinner_item, name);
			names = name;
			numbers = num;
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public String getItem(int pos) {
			// TODO Auto-generated method stub
			return names[pos];
		}

		public int getTotalNumAt(int pos){
			return numbers[pos];
		}
		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = LayoutInflater.from(BookListActivity.this).inflate(R.layout.spinner_text_view, null);
				TextView tv = (TextView)convertView.findViewById(R.id.text);
				tv.setText(names[pos]);
//				tv  = (TextView)convertView.findViewById(R.id.num);
//				tv.setText(""+numbers[pos]);
			}
			return convertView;
		}
		
		@Override
		public View getDropDownView(int pos, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = LayoutInflater.from(BookListActivity.this).inflate(R.layout.spinner_item, null);
				TextView tv = (TextView)convertView.findViewById(R.id.text);
				tv.setText(names[pos]);
				tv  = (TextView)convertView.findViewById(R.id.num);
				tv.setText(""+numbers[pos]);
			}
			return convertView;
		}
	}
		
	/**
	 * 显示一般情况下的actionbar
	 */
		public void showNormalBar(){
			if(actionbarSearch.getVisibility()==View.GONE){
				normalControlBar.setVisibility(View.VISIBLE);
				actionbarNormal.setVisibility(View.VISIBLE);
			}
			editControlBar.setVisibility(View.GONE);
			actionbarEdit.setVisibility(View.GONE);
		}
		
		/**
		 * 显示编辑状态下的actionbar
		 */
		public void showEditBar(){
			selectedNum.setText(getString(R.string.text_selected_num_before)+"  "+getString(R.string.text_selected_num_after));
			editControlBar.setVisibility(View.VISIBLE);
			actionbarEdit.setVisibility(View.VISIBLE);
			normalControlBar.setVisibility(View.GONE);
			actionbarNormal.setVisibility(View.GONE);
			actionbarSearch.setVisibility(View.GONE);
			searchBtn.setVisibility(View.VISIBLE);
		}
		/**
		 * 显示编辑状态下的actionbar
		 */
		public void showSearchBar(){
			actionbarEdit.setVisibility(View.GONE);
			actionbarNormal.setVisibility(View.GONE);
			actionbarSearch.setVisibility(View.VISIBLE);
		}
		
		/**
		 * 当点击返回键时，如果搜索框打开，则隐藏搜索框
		 * 否则交由父类来处理
		 */
		@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			if(actionbarSearch.getVisibility()==View.VISIBLE){
				actionbarSearch.setVisibility(View.GONE);
				actionbarNormal.setVisibility(View.VISIBLE);
				searchBtn.setVisibility(View.VISIBLE);
			}else{
				super.onBackPressed();
			}
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
		if (resultCode == 400) {
			displayUpdateView(true);
			taskSchedule.newTask(new GetXunPanItemsTask(), 1,filterNow);
		}
			super.onActivityResult(requestCode, resultCode, data);
		}

}
