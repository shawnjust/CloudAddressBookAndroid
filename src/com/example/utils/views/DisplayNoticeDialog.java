package com.example.utils.views;

import com.example.cloudaddressbook.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * 这个View是一个弹出的对话框，显示通知的详细信息
 * @author admin
 *
 */
public class DisplayNoticeDialog extends Dialog{
	private Button cancelBtn;
	private String showText;
	/**
	 * 构造函数
	 * @param context 对话框显示的上下文
	 * @param showText 对话框里要显示的文字
	 */
	public DisplayNoticeDialog(Context context, String showText) {
		super(context ,R.style.NoTitleDialog);
		// TODO Auto-generated constructor stub
		this.showText = showText;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.show_notice_dialog);
		((TextView)findViewById(R.id.content)).setText(showText);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
	}
	
}
