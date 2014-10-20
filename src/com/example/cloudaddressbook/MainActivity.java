package com.example.cloudaddressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cloudaddressbook.beans.Result;
import com.cloudaddressbook.wsclient.NetWorkHelper;
import com.cloudaddressbook.wsclient.UserInstanceHelper;

public class MainActivity extends Activity {

	Button registButton;
	Button loginButton;

	EditText emailEditText;
	EditText passwordEditText;
	SharedPreferences sp;

	String email;
	String password;
	private LinearLayout progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		

		registButton = (Button) findViewById(R.id.registButton);
		loginButton = (Button) findViewById(R.id.loginButton);
		emailEditText = (EditText) findViewById(R.id.emailEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		progressBar = (LinearLayout)findViewById(R.id.progressBar);
		sp = getSharedPreferences("CloudAddressBookUserPref",
				Activity.MODE_PRIVATE);
		email = sp.getString("username", "");
		password = sp.getString("password", "");
		if (!email.equals("") && !password.equals("")) {

			emailEditText.setText(email);
			passwordEditText.setText(password);
			performSignIn(email,password);
			return;
		}
		
		
//		emailEditText.setText("shawn@qq.com");
//		passwordEditText.setText("1234");
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				email = emailEditText.getText().toString();
				password = passwordEditText.getText().toString();
//				NetWorkHelper net = NetWorkHelper.getInstance();
//				Result result = net.login(email, password);
				
				progressBar.setVisibility(View.VISIBLE);
				new LoginTask().execute();
			}
		});

		registButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RegistActivity.class);
				startActivity(intent);
			}
		});
		
		
	}

	public void performSignIn(String username, String password) {
//		NetWorkHelper net = NetWorkHelper.getInstance();
//		Result result = net.login(username, password);
//
//		if (result.isSuccess()) {
//
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, BookListActivity.class);
//			startActivity(intent);
//			MainActivity.this.finish();
//		} else {
//			new AlertDialog.Builder(MainActivity.this).setTitle("登陆失败")
//					.setMessage("失败信息： " + result.getMessage())
//					.setPositiveButton("确定", null).show();
//		}
		progressBar.setVisibility(View.VISIBLE);
		new LoginTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class  LoginTask extends AsyncTask<Void, Void, Result>{

		@Override
		protected Result doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			Result result = NetWorkHelper.getInstance().loginNet(email, password);
			return result;
			
			
		}
		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			if (result.isSuccess()) {
				Editor editor = sp.edit();
				editor.putString("username", email);
				editor.putString("password", password);
				editor.commit();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, BookListActivity.class);
				progressBar.setVisibility(View.GONE);
				startActivity(intent);
				MainActivity.this.finish();
			} else {
				progressBar.setVisibility(View.GONE);
				new AlertDialog.Builder(MainActivity.this).setTitle("登陆失败")
						.setMessage("失败信息： " + result.getMessage())
						.setPositiveButton("确定", null).show();
			}
			
			super.onPostExecute(result);
		}
	}
}
