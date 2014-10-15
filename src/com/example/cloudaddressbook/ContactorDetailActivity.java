package com.example.cloudaddressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	SimpleAdapter adapter;
	List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactor_detail);

		Intent selfIntent = getIntent();
		name = selfIntent.getStringExtra("name");
		email = selfIntent.getStringExtra("email");

		listView = (ListView) findViewById(R.id.contactDetailListView);

		list = new ArrayList<Map<String, Object>>();
		int[] to = new int[] { R.id.titleTextView, R.id.contentTextView };
		String[] from = new String[] { "title", "content" };
		adapter = new SimpleAdapter(this, list, R.layout.contact_detail_item,
				from, to);
		listView.setAdapter(adapter);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "姓名");
		map.put("content", name);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "邮箱");
		map.put("content", email);
		list.add(map);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contactor_detail, menu);
		return true;
	}

}
