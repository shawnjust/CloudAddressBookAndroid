package com.example.cloudaddressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class SearchActivity extends Activity {

	ProgressBar progressBar;
	ListView searchListView;
	SimpleAdapter adapter;
	List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		searchListView = (ListView) findViewById(R.id.search_result_listview);
		progressBar = (ProgressBar) findViewById(R.id.searchProgressBar);
		progressBar.setVisibility(View.GONE);

		int[] to = new int[] { R.id.nameTextView };
		String[] from = new String[] { "name" };
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, list, R.layout.connector_item, from,
				to);
		searchListView.setAdapter(adapter);
		searchListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						new AlertDialog.Builder(SearchActivity.this)
								.setTitle("请输入验证信息")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setView(new EditText(SearchActivity.this))
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
											}
										}).setNegativeButton("取消", null).show();
					}
				});

		for (int i = 0; i < 5; i++) {
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
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
