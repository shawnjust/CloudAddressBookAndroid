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
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StrangerApplicationActivity extends Activity {

	ListView applicationListView;
	SimpleAdapter adapter;
	List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stranger_application);

		applicationListView = (ListView) findViewById(R.id.strangerApplicationListView);

		int[] to = new int[] { R.id.nameTextView };
		String[] from = new String[] { "name" };
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, list, R.layout.connector_item, from,
				to);
		applicationListView.setAdapter(adapter);
		applicationListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Map<String, Object> map = (Map<String, Object>) parent
								.getAdapter().getItem(position);
						String message = map.get("name") + "（"
								+ map.get("email") + "）请求添加您为好友。";
						new AlertDialog.Builder(
								StrangerApplicationActivity.this)
								.setTitle("添加信息")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setMessage(message)
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
											}
										}).setNegativeButton("忽略", null)
								.show();
					}
				});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "路捷");
		map.put("email", "chaos@gamil.com");
		list.add(map);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stranger_application, menu);
		return true;
	}

}
