package com.example.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
/**
 * 一个拦截所有touch事件的容器视图，用于那些需要自定义手势事件处理流程的布局
 * 通过setOnTouchListener的方法来处理触屏事件
 * @author admin
 *
 */
public class NonInterreptLinearLayout extends LinearLayout{
	public NonInterreptLinearLayout(Context context) {
		super(context);
	}
	public NonInterreptLinearLayout(Context context, AttributeSet attrs){  
        super(context, attrs);  
    }  
       
    public NonInterreptLinearLayout(Context context, AttributeSet attrs, int defStyle){  
        super(context, attrs, defStyle);  
    }  
    /**
     * 任何时候都拦截MotionEvent来自己处理
     */
    @Override   
    public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;   
    }
    
    
}
