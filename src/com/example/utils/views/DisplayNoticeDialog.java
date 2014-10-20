package com.example.utils.views;

import java.util.ArrayList;
import java.util.List;

import com.cloudaddressbook.beans.UserDetail;
import com.cloudaddressbook.wsclient.NetWorkHelper;
import com.example.cloudaddressbook.NoticeListActivity;
import com.example.cloudaddressbook.R;
import com.example.utils.entities.Message;
import com.example.utils.utils.NetworkState;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 这个View是一个弹出的对话框，显示通知的详细信息
 * @author admin
 *
 */
public class DisplayNoticeDialog extends Dialog{
	private Button cancelBtn;
	private Button aggreeBtn;
	private Message msg;
	private String userEmail;
	/**
	 * 构造函数
	 * @param context 对话框显示的上下文
	 * @param showText 对话框里要显示的文字
	 */
	public DisplayNoticeDialog(Context context, Message message, String email) {
		super(context ,R.style.NoTitleDialog);
		// TODO Auto-generated constructor stub
		this.msg = message;
		userEmail = email;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.show_notice_dialog);
		((TextView)findViewById(R.id.name)).setText(msg.getUserName());
		((TextView)findViewById(R.id.content)).setText(msg.getMsg());
		((TextView)findViewById(R.id.date)).setText(msg.getDate());
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		aggreeBtn = (Button) findViewById(R.id.confirm_btn);
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new ReplyNoticeTask().execute(1);
				dismiss();
			}
		});
		
		aggreeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new ReplyNoticeTask().execute(0);
				dismiss();
			}
		});
		switch(msg.getType()){
		case 0:
			cancelBtn.setText("拒绝");
			break;
		case 1:
			aggreeBtn.setEnabled(false);
			aggreeBtn.setText("已同意");
			break;
		case 2:
			aggreeBtn.setEnabled(false);
			aggreeBtn.setText("已拒绝");
			break;
		default:
			aggreeBtn.setVisibility(View.GONE);
		}
	}
	/**
	 * 异步加载公告消息进程
	 * @author Jiayue Ren
	 */
	private class ReplyNoticeTask extends AsyncTask<Integer, Void, ArrayList<Message>>{
		private int type = -1;
		@Override
		protected ArrayList<Message> doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			type = arg0[0];
			String email =  msg.getEmail();
			if(type == 0){
				NetWorkHelper.getInstance().acceptAddingFriend(userEmail,email);
			}else{
				//NetWorkHelper.getInstance().re(userEmail,email);
			}
			return null;
		}
		
	}
}
