package com.example.cloudaddressbook;

import com.example.utils.entities.XunPanItem;
import com.example.utils.views.DeleteItemDialog;
import com.example.utils.views.EditTextDialog;
import com.example.utils.views.EditTextDialog.OnConfirmListener;

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
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				final Button b = (Button)btn;
				DeleteItemDialog.OnConfirmListener l = new DeleteItemDialog.OnConfirmListener() {
					@Override
					public void OnConfirm(boolean deleteWithGroup) {
						// TODO Auto-generated method stub
						setResult(400);
						finish();
					}
				};
				DeleteItemDialog dialog = new DeleteItemDialog(ShowXunpanDetailActivity.this,l,"确认要删除该联系人吗？");
				
				dialog.show();
//				setResult(400);
//				finish();
			}
		};
		
		deleteSubListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				final Button b = (Button)btn;
				DeleteItemDialog.OnConfirmListener l = new DeleteItemDialog.OnConfirmListener() {
					@Override
					public void OnConfirm(boolean deleteWithGroup) {
						// TODO Auto-generated method stub
						setResult(400);
						b.setText("关注");
						b.setOnClickListener(addSubListener);
					}
				};
				DeleteItemDialog dialog = new DeleteItemDialog(ShowXunpanDetailActivity.this,l,"确认要取消关注吗？");
				
				dialog.show();
			}
		};
		
		addSubListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				
				final Button b = (Button)btn;
				EditTextDialog dialog = new EditTextDialog(ShowXunpanDetailActivity.this);
				dialog.setOnConfirmListener(new OnConfirmListener() {
					
					@Override
					public void OnConfirm(String text) {
						// TODO Auto-generated method stub
						b.setText("等待验证");
						b.setEnabled(false);
						setResult(400);
					}
				});
				dialog.show();
//				((Button)btn).setText("取消关注");
//				btn.setOnClickListener(deleteSubListener);
			}
		};
		
		sendFriendRequestListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				final Button b = (Button)btn;
				EditTextDialog dialog = new EditTextDialog(ShowXunpanDetailActivity.this);
				dialog.setOnConfirmListener(new OnConfirmListener() {
					
					@Override
					public void OnConfirm(String text) {
						// TODO Auto-generated method stub
						b.setText("请求已发送");
						b.setEnabled(false);
						setResult(401);
					}
				});
				dialog.show();
//				setResult(401);
//				((Button)btn).setText("请求已发送");
//				btn.setEnabled(false);
			}
		};
		
		addFriendListener = new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				final Button b = (Button)btn;
				EditTextDialog dialog = new EditTextDialog(ShowXunpanDetailActivity.this);
				dialog.setOnConfirmListener(new OnConfirmListener() {
					
					@Override
					public void OnConfirm(String text) {
						// TODO Auto-generated method stub
						b.setText("等待验证");
						b.setEnabled(false);
						setResult(400);
					}
				});
				dialog.show();
//				setResult(400);
//				((Button)btn).setText("等待验证");
//				btn.setEnabled(false);
			}
		};
		
		Button btn = (Button)findViewById(R.id.send_btn);
		btn.setOnClickListener(deleteContractorListener);
		Button btn1 = (Button)findViewById(R.id.send_btn2);
		switch(state){
		case 0:
			btn.setText("删除该联系人");
			btn1.setText("取消关注");
			btn1.setOnClickListener(deleteSubListener);
			break;
		case 1:
			btn.setText("删除该联系人");
			btn1.setText("发送好友请求");
			btn1.setOnClickListener(sendFriendRequestListener);
			break;
		case 2:
			btn.setText("删除该联系人");
			btn1.setText("关注");
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
