package com.example.cloudaddressbook;

import com.cloudaddressbook.wsclient.UserInstanceHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	private TextView changePW;
	private TextView changeNum;
	private Button saveButton;
	private CheckBox numChangedInform;
	private CheckBox msgInform;
	private TextView signButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		changePW = (TextView)findViewById(R.id.change_pw_btn);
		changeNum = (TextView)findViewById(R.id.change_num_btn);
		saveButton = (Button)findViewById(R.id.save_btn);
		numChangedInform = (CheckBox)findViewById(R.id.checkbox_num_changed_inform);
		msgInform = (CheckBox)findViewById(R.id.checkbox_msg_inform);
		signButton =  (TextView)findViewById(R.id.signout_btn);
		
		SharedPreferences sp = getSharedPreferences("CloudAddressBookUserPref", Activity.MODE_PRIVATE);
		msgInform.setChecked(sp.getBoolean("msgInform", true));
		numChangedInform.setChecked(sp.getBoolean("numChangedInform", true));
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sp = getSharedPreferences("CloudAddressBookUserPref", Activity.MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.putBoolean("msgInform", msgInform.isChecked());
				editor.putBoolean("numChangedInform", numChangedInform.isChecked());
				editor.commit();
				new AlertDialog.Builder(SettingsActivity.this)
				.setTitle("保存")
				.setMessage("保存成功")
				.setPositiveButton("确定",null).show();
			}
		});
		
		signButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sp = getSharedPreferences("CloudAddressBookUserPref", Activity.MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.remove("username");
				editor.remove("password");
				editor.commit();
				setResult(404);
				finish();
			}
		});
		changePW.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				intent.putExtra("changeType", 0);
				intent.setClass(SettingsActivity.this, ChangePrefActivity.class);
				startActivity(intent);
			}
		});
		
		changeNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				intent.putExtra("changeType", 1);
				intent.setClass(SettingsActivity.this, ChangePrefActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
