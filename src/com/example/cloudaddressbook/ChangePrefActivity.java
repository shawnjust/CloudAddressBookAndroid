package com.example.cloudaddressbook;

import com.cloudaddressbook.wsclient.NetWorkHelper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePrefActivity extends Activity {
	private Button saveButton;
	private TextView newValueText;
	private TextView passwordText;
	private TextView passwordConfirmText;
	private EditText newValue;
	private EditText password;
	private EditText passwordConfirm;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pref);
		saveButton = (Button)findViewById(R.id.save_btn);
		newValueText = (TextView)findViewById(R.id.new_value_text);
		passwordText = (TextView)findViewById(R.id.password_text);
		passwordConfirmText = (TextView)findViewById(R.id.new_value_confirm_text);
		
		newValue = (EditText)findViewById(R.id.new_value);
		password = (EditText)findViewById(R.id.password);
		passwordConfirm = (EditText)findViewById(R.id.new_value_confirm);
		
		Intent intent = getIntent();
		type = intent.getIntExtra("changeType",0);
		if(type ==0){
			newValueText.setText("新密码");
			passwordConfirmText.setText("确认新密码");
			passwordText.setText("旧密码");
			passwordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
			newValue.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}else{
			newValueText.setText("新号码");
			passwordConfirmText.setText("旧号码");
			SharedPreferences sp = getSharedPreferences("CloudAddressBookUserPref", Activity.MODE_PRIVATE);
			String email = sp.getString("username", "");
			passwordConfirm.setText(email);
			passwordConfirm.setEnabled(false);
			passwordText.setText("密码");
		}
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sp = getSharedPreferences("CloudAddressBookUserPref", Activity.MODE_PRIVATE);
				String pw = sp.getString("password", "");
				if(!pw.equals(password.getText().toString())){
					password.setError("密码不正确");
					return;
				}
				if(type == 0&&!passwordConfirm.getText().toString().equals(newValue.getText().toString())){
					passwordConfirm.setError("两次输入的密码不一致！");
					return;
				}
				if(type == 0){
					Editor editor = sp.edit();
					editor.putString("password", newValue.getText().toString());
					editor.commit();
				}
				if(type ==1){
					new SavePrefTask().execute("phone",newValue.getText().toString());
				}
				new AlertDialog.Builder(ChangePrefActivity.this)
				.setTitle("保存")
				.setMessage("保存成功")
				.setPositiveButton("确定",null).show();
			}
		});
		
		
	}

	private class SavePrefTask extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String key = arg0[0];
			String value = arg0[1];
			String email =  getSharedPreferences("CloudAddressBookUserPref",
					Activity.MODE_PRIVATE).getString("username","");
			NetWorkHelper.getInstance().insertOrUpdateContact(email,key,value);
			return null;
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_pref, menu);
		return true;
	}

}
