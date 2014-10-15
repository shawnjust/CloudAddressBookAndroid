package com.example.cloudaddressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cloudaddressbook.beans.User;
import com.cloudaddressbook.wsclient.UserInstanceHelper;

public class SelfInfromationActivity extends Activity {

	ListView listView;
	SimpleAdapter adapter;
	List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self_infromation);

		listView = (ListView) findViewById(R.id.selfInformationListView);

		list = new ArrayList<Map<String, Object>>();
		int[] to = new int[] { R.id.titleTextView, R.id.contentTextView };
		String[] from = new String[] { "title", "content" };
		adapter = new SimpleAdapter(this, list, R.layout.contact_detail_item,
				from, to);
		listView.setAdapter(adapter);

		User selfInformation = UserInstanceHelper.getInstance()
				.getSelfInformation();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "姓名");
		map.put("content", selfInformation.getName());
		map.put("editable", new Boolean(true));
		map.put("deletable", new Boolean(false));
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "邮箱地址");
		map.put("content", selfInformation.getEmail());
		map.put("editable", new Boolean(false));
		map.put("deletable", new Boolean(false));
		list.add(map);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, Object> map = (Map<String, Object>) parent
						.getAdapter().getItem(position);
				LinearLayout layout = new LinearLayout(
						SelfInfromationActivity.this);
				layout.setOrientation(LinearLayout.VERTICAL);
				TextView titleTextView = new TextView(
						SelfInfromationActivity.this);
				titleTextView.setText("名称");
				titleTextView.setPadding(8, 8, 8, 8);
				TextView contentTextView = new TextView(
						SelfInfromationActivity.this);
				contentTextView.setText("内容");
				contentTextView.setPadding(8, 8, 8, 8);
				EditText title = new EditText(SelfInfromationActivity.this);
				title.setPadding(8, 8, 8, 8);
				title.setText((String)map.get("title"));
				EditText content = new EditText(SelfInfromationActivity.this);
				content.setPadding(8, 8, 8, 8);
				content.setText((String)map.get("content"));
				layout.addView(titleTextView);
				layout.addView(title);
				layout.addView(contentTextView);
				layout.addView(content);
				if ((Boolean) map.get("editable")) {
					new AlertDialog.Builder(SelfInfromationActivity.this)
							.setTitle("请输入验证信息")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setView(layout)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
										}
									}).setNegativeButton("删除该字段", null).show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.self_infromation, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add_field:
			return true;
		case R.id.action_save:
			LinearLayout layout = new LinearLayout(
					SelfInfromationActivity.this);
			layout.setOrientation(LinearLayout.VERTICAL);
			TextView titleTextView = new TextView(
					SelfInfromationActivity.this);
			titleTextView.setText("名称");
			titleTextView.setPadding(8, 8, 8, 8);
			TextView contentTextView = new TextView(
					SelfInfromationActivity.this);
			contentTextView.setText("内容");
			contentTextView.setPadding(8, 8, 8, 8);
			final EditText title = new EditText(SelfInfromationActivity.this);
			title.setPadding(8, 8, 8, 8);
			final EditText content = new EditText(SelfInfromationActivity.this);
			content.setPadding(8, 8, 8, 8);
			layout.addView(titleTextView);
			layout.addView(title);
			layout.addView(contentTextView);
			layout.addView(content);
			
			new AlertDialog.Builder(SelfInfromationActivity.this)
			.setTitle("添加字段")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setView(layout)
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								DialogInterface arg0, int arg1) {
							String titleStr = title.getText().toString();
							String contentStr = content.getText().toString();
							
						}
					}).setNegativeButton("删除该字段", null).show();
			return true;
		}
		return false;
	}

}
