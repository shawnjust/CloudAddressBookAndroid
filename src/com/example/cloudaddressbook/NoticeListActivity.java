package com.example.cloudaddressbook;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.utils.adapter.NoticeListAdapter;
import com.example.utils.entities.Message;
import com.example.utils.utils.NetworkState;
import com.example.utils.views.DisplayNoticeDialog;
import com.example.utils.webservice.ConnectServer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 分页显示通知的界面
 * @author Jiayue Ren
 */
public class NoticeListActivity extends Activity {

	private final int LOAD_NEXT_PAGE = 10;
	private final int LOAD_PREVIOUS_PAGE = 20;
	
	private ListView noticeList;
	private NoticeListAdapter adapter;
	private ConnectServer connect = ConnectServer.getInstance();
	private int pageNum = -1;
	private boolean isLastPage = false;
	private boolean isLoading = false;
	private View headerView;
	private View footView;
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_list);
		Date date1 = new Date(); 
		Locale locale = getResources().getConfiguration().locale;
		DateFormat dateFormat = DateFormat.getDateInstance(SimpleDateFormat.FULL, locale);
		date = dateFormat.format(date1);
		// 初始化通知栏
		noticeList = (ListView) findViewById(R.id.notice_list);
		footView = LayoutInflater.from(this).inflate(R.layout.loadingbar, null);
		displayFootView(false);
		footView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isLoading)
					return;
				
				displayFootView(true);
				isLoading = true;
				new RequestForNoticeTask().execute(LOAD_NEXT_PAGE);
			}
		});
		noticeList.addFooterView(footView);
		
		headerView = LayoutInflater.from(this).inflate(R.layout.loadingbar, null);
		displayHeadView(false);
		TextView loadTextView = (TextView) headerView.findViewById(R.id.load_text);
		loadTextView.setText(getString(R.string.text_today_is)+" "+date);
		
		headerView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isLoading||pageNum<=0)
					return;
				
				displayHeadView(true);
				isLoading = true;
				new RequestForNoticeTask().execute(LOAD_PREVIOUS_PAGE);
			}
		});
		noticeList.addHeaderView(headerView);
		adapter = new NoticeListAdapter(this, R.layout.notice_item);
		noticeList.setAdapter(adapter);
		noticeList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				if (pos < noticeList.getCount() - 1) {
					new DisplayNoticeDialog(NoticeListActivity.this, (Message) adapter
							.getItem(pos-1)).show();
				}
			}
		});
		isLoading = true;
		displayFootView(true);
		new RequestForNoticeTask().execute(LOAD_NEXT_PAGE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notice_list, menu);
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
	 * 异步加载公告消息进程
	 * @author Jiayue Ren
	 */
	private class RequestForNoticeTask extends AsyncTask<Integer, Void, ArrayList<Message>>{
		private int type = -1;
		@Override
		protected ArrayList<Message> doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			if(!NetworkState.isNetworkConnected(NoticeListActivity.this)){
				return null;
			}
			type = arg0[0];
			if(LOAD_NEXT_PAGE == type&&!isLastPage){
				return connect.getNoticeListByPage(pageNum+1);
			}else if(LOAD_PREVIOUS_PAGE == type&&pageNum>0){
				return connect.getNoticeListByPage(pageNum-1);
			}
			cancel(true);
			return null;
		}
		@Override
		protected void onPostExecute(ArrayList<Message> result) {
			// TODO Auto-generated method stub
			if(result==null){
				Toast.makeText(NoticeListActivity.this, R.string.error_connect_failed, Toast.LENGTH_SHORT).show();
			}else if(result.size()>0){
				adapter.removeAllItem();
				for(Message notice:result){
					adapter.addItem(notice);
				}
				adapter.notifyDataSetChanged();
				noticeList.setSelection(0);
				if(type == LOAD_NEXT_PAGE){
					pageNum ++;
				}else{
					isLastPage = false;
					pageNum --;
				}
				
			}else{
				isLastPage = true;
			}
			displayFootView(false);
			displayHeadView(false);
			isLoading = false;
		}
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			if(type == LOAD_NEXT_PAGE){
				displayFootView(false);
			}else{
				displayHeadView(false);
			}
			isLoading = false;
			super.onCancelled();
		}
		
	}
	/**
	 * 改变表尾加载进度条的显示状态
	 * @param isVisible 进度条是否可见
	 */
	private void displayFootView(boolean isVisible){
		ProgressBar progressBar = (ProgressBar) footView.findViewById(R.id.progress_bar);
		TextView loadTextView = (TextView) footView.findViewById(R.id.load_text);
		if(isLastPage){
			loadTextView.setText(R.string.already_the_last_page);
			progressBar.setVisibility(View.GONE);
		}
		else if(isVisible){
			loadTextView.setText(R.string.text_loading);
			progressBar.setVisibility(View.VISIBLE);
		}else{
			loadTextView.setText(R.string.click_to_load_next_page);
			progressBar.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 改变表头加载进度条的显示状态
	 * @param isVisible 是否可见（这里同时定义再第一页表头显示当前事件，其它时候显示加载上1页）
	 */
	private void displayHeadView(boolean isVisible){
		ProgressBar progressBar = (ProgressBar) headerView.findViewById(R.id.progress_bar);
		TextView loadTextView = (TextView) headerView.findViewById(R.id.load_text);
		if(isVisible){
			loadTextView.setText(R.string.text_loading);
			progressBar.setVisibility(View.VISIBLE);
		}else{
			loadTextView.setText(R.string.click_to_load_previous_page);
			progressBar.setVisibility(View.GONE);
			if(pageNum == 0){
				loadTextView.setText(getString(R.string.text_today_is)+" "+date);
			}
		}
	}
}
