package com.example.utils.views;

import com.example.cloudaddressbook.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DeleteItemDialog extends Dialog{
	private OnConfirmListener onconfirmed;
	private Button confirmBtn;
	private Button cancelBtn;
	private String showText;
	
	public DeleteItemDialog(Context context, OnConfirmListener l, String showText) {
		super(context ,R.style.SelectGroupDialog);
		// TODO Auto-generated constructor stub
		this.onconfirmed = l;
		this.showText = showText;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.delete_item_dialog);
		((TextView)findViewById(R.id.content)).setText(showText);
		
		confirmBtn = (Button) findViewById(R.id.confirm_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		confirmBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DeleteItemDialog.this.dismiss();
				if (onconfirmed != null) {
					onconfirmed.OnConfirm(true);
				}
			}
		});
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
	}
	
	public OnConfirmListener getOnConfirmListener() {
		return onconfirmed;
	}


	public void setOnConfirmListener(OnConfirmListener onconfirmed) {
		this.onconfirmed = onconfirmed;
	}


	public interface OnConfirmListener{
		public void OnConfirm(boolean deleteWithGroup);
	}
}
