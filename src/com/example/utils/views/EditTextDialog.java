package com.example.utils.views;

import com.example.cloudaddressbook.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 输入组名对话框
 * 
 * @author Ren Jiayue
 * 
 */
public class EditTextDialog extends Dialog {
	private Context context;
	private Button mconfirmBtn;
	private Button mcancelBtn;
	private EditText requestForName;
	private OnConfirmListener l;

	public EditTextDialog(Context context) {
		super(context, R.style.SelectGroupDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.edit_text_dialog);
		requestForName = (EditText) this.findViewById(R.id.input);

		mconfirmBtn = (Button) this.findViewById(R.id.confirm_btn);
		mcancelBtn = (Button) this.findViewById(R.id.cancel_btn);

		mconfirmBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String text = requestForName.getText().toString();
				if (text.length()>15) {
					requestForName.setError("验证信息不能超过15个 字！");
					
				}else{
					l.OnConfirm(text);
					EditTextDialog.this.dismiss();
				}
				}
			}

		);
		mcancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EditTextDialog.this.dismiss();
			}
		});
		
		
	}
	
	
	public void setOnConfirmListener(OnConfirmListener l){
		this.l = l;
	}
	public interface OnConfirmListener{
		/**
		 * 当用户选好分组之后的回调方法
		 * @param selectedItem 选中的组名
		 * @param addGroups 新添加的组名
		 */
		public void OnConfirm(String text);
	}
}
