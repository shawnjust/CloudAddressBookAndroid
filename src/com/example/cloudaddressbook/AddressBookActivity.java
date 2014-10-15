package com.example.cloudaddressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class AddressBookActivity extends Activity {

	ProgressBar progressBar;
	ListView contactListView;
	SimpleAdapter adapter;
	List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_book);

		contactListView = (ListView) findViewById(R.id.contact_listview);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		progressBar.setVisibility(View.GONE);

		int[] to = new int[] { R.id.nameTextView };
		String[] from = new String[] { "name" };
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, list, R.layout.connector_item, from,
				to);
		contactListView.setAdapter(adapter);
		contactListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String email = (String) ((Map<String, Object>) parent
								.getAdapter().getItem(position)).get("email");
						String name = (String) ((Map<String, Object>) parent
								.getAdapter().getItem(position)).get("name");
						Intent intent = new Intent();
						intent.setClass(AddressBookActivity.this,
								ContactorDetailActivity.class);
						intent.putExtra("email", email);
						intent.putExtra("name", name);
						startActivity(intent);
					}
				});

		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "shawn" + i);
			map.put("email", "shawn@qq.com");
			list.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.address_book, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		Intent intent;
		switch (item.getItemId()) {
		case R.id.action_self_detail:
			intent = new Intent();
			intent.setClass(AddressBookActivity.this,
					SelfInfromationActivity.class);
			AddressBookActivity.this.startActivity(intent);
			return true;
		case R.id.action_search:
			intent = new Intent();
			intent.setClass(AddressBookActivity.this, SearchActivity.class);
			AddressBookActivity.this.startActivity(intent);
			return true;
		case R.id.action_stranger_application:
			intent = new Intent();
			intent.setClass(AddressBookActivity.this, StrangerApplicationActivity.class);
			AddressBookActivity.this.startActivity(intent);
			return true;
		case R.id.action_refresh:
			return true;
		}
		return false;
	}
}