package com.example.cloudaddressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.cloudaddressbook.beans.Result;
import com.cloudaddressbook.wsclient.NetWorkHelper;

public class RegistActivity extends Activity {

	EditText emailEditText;
	EditText nameEditText;
	EditText passwordEditText;
	EditText repeatPasswordEditText;
	Button submitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		emailEditText = (EditText) findViewById(R.id.emailEditText);
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);
		submitButton = (Button) findViewById(R.id.submitButton);

		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String email = emailEditText.getText().toString();
				String name = nameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				String repeatPassword = repeatPasswordEditText.getText()
						.toString();
				if (email.isEmpty()) {
					emailEditText.requestFocus();
				} else if (name.isEmpty()) {
					nameEditText.requestFocus();
				} else if (password.isEmpty()) {
					passwordEditText.requestFocus();
				} else if (repeatPassword.isEmpty()) {
					repeatPasswordEditText.requestFocus();
				} else if (!password.equals(repeatPassword)) {
					new AlertDialog.Builder(RegistActivity.this)
							.setTitle("输入有误").setMessage("两次密码输入不一致")
							.setPositiveButton("确定", null).show();
					passwordEditText.requestFocus();
				} else {
					// success;
					Result result = NetWorkHelper.getInstance().regist(email,
							name, password);
					if (result.isSuccess()) {
						new AlertDialog.Builder(RegistActivity.this)
								.setTitle("注册成功")
								.setMessage("注册成功，点击确定返回登陆界面")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												RegistActivity.this.finish();
											}
										}).show();
					} else {
						new AlertDialog.Builder(RegistActivity.this)
								.setTitle("注册失败")
								.setMessage("失败信息： " + result.getMessage())
								.setPositiveButton("确定", null).show();
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.regist, menu);
		return true;
	}

}
