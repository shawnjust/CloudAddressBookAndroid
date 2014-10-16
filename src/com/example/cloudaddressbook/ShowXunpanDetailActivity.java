package com.example.cloudaddressbook;

import com.example.utils.entities.XunPanItem;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 显示询盘详细信息的界面
 * @author admin
 *
 */
public class ShowXunpanDetailActivity extends Activity {
	private String name;
	private String email;
	private String phone;
	private OnClickListener deleteContractorListener;
	private OnClickListener deleteSubListener;
	private OnClickListener addSubListener;
	private OnClickListener sendFriendRequestListener;
	private OnClickListener addFriendListener;
	
	private int state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_xunpan_detail);
		Intent selfIntent = getIntent();
		XunPanItem item = (XunPanItem)selfIntent.getSerializableExtra("item");
		name = "";
		email = "";
		phone = "";
		state = 1;
		if(item != null){
			name = item.getProductName();
			email = item.geteMail();
			phone = item.getPhone();
			state = item.getState();
		}
		
		TextView tv =(TextView)findViewById(R.id.name);
		tv.setText(name);
		tv =(TextView)findViewById(R.id.guest_name);
		tv.setText(name);
		tv =(TextView)findViewById(R.id.email);
		tv.setText(email);
		tv =(TextView)findViewById(R.id.email1);
		tv.setText(email);
		tv =(TextView)findViewById(R.id.phone);
		tv.setText(phone);
		
		deleteContractorListener = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(400);
				finish();
			}
		};
		
		deleteSubListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				setResult(400);
				((Button)btn).setText("关联");
				btn.setOnClickListener(addSubListener);
			}
		};
		
		addSubListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				setResult(400);
				((Button)btn).setText("取消关联");
				btn.setOnClickListener(deleteSubListener);
			}
		};
		
		sendFriendRequestListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				setResult(401);
				((Button)btn).setText("请求已发送");
				btn.setEnabled(false);
			}
		};
		
		addFriendListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				setResult(400);
				((Button)btn).setText("等待验证");
				btn.setEnabled(false);
			}
		};
		
		Button btn = (Button)findViewById(R.id.send_btn);
		btn.setOnClickListener(deleteContractorListener);
		Button btn1 = (Button)findViewById(R.id.send_btn2);
		switch(state){
		case 0:
			btn.setText("删除该联系人");
			btn1.setText("取消关联");
			btn1.setOnClickListener(deleteSubListener);
			break;
		case 1:
			btn.setText("删除该联系人");
			btn1.setText("发送好友请求");
			btn1.setOnClickListener(sendFriendRequestListener);
			break;
		case 2:
			btn.setText("删除该联系人");
			btn1.setText("关联");
			btn1.setOnClickListener(addSubListener);
			break;
		case 3:
			btn.setText("加好友");
			btn1.setVisibility(View.GONE);
			btn1.setOnClickListener(addFriendListener);
			break;
			
		}

	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_xunpan_detail, menu);
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

	
}
