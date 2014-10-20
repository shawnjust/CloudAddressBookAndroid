package com.example.cloudaddressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.utils.entities.XunPanItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ContactorDetailActivity extends Activity {

	ListView listView;

	String name;
	String email;
	String phone;

	SimpleAdapter adapter;
	List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_xunpan_detail);

		Intent selfIntent = getIntent();
		XunPanItem item = (XunPanItem)selfIntent.getSerializableExtra("item");
		name = "";
		email = "";
		phone = "";
		if(item != null){
			name = item.getProductName();
			email = item.geteMail();
			phone = item.getPhone();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contactor_detail, menu);
		return true;
	}

}
