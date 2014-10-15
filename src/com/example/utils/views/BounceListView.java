package com.example.utils.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 拦截TOUCH EVENT自己处理的ListView
 * 作为m.ETWINTERNATIONAL.COM.myviews.NonInterreptLinearLayout的子View使用
 * 可以灵活的控制各种手势下listView的响应，自定义点击事件的处理
 * @author Ren Jiayue
 *
 */
final public class BounceListView extends ListView{  
    private OnItemClickListener onItemClickListener;
    public BounceListView(Context context){  
        super(context);  
        this.requestDisallowInterceptTouchEvent(true);
    }  
       
    public BounceListView(Context context, AttributeSet attrs){  
        super(context, attrs);
    }  
    
    
   /**
    * 设置适配器    
    */
    @Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
	}

	public BounceListView(Context context, AttributeSet attrs, int defStyle){  
        super(context, attrs, defStyle); 
    }  
      
    @Override   
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	Log.i("bbb","BounceListView:InterceptTouchEvent");
    	super.onInterceptTouchEvent(ev);
		return true;   
    }
    
    //private int lastMotion = -1;
    private long lastDownTime = 0;
    private float lastDownX = 0;
    private float lastDownY = 0;
    
    
    /**
     * 处理触屏事件
     */
    @Override   
    public boolean onTouchEvent(MotionEvent ev) {
    	Log.i("bbb","BounceListView:onTouchEvent action="+ev.getAction());
    	super.onTouchEvent(ev);
    	if(MotionEvent.ACTION_UP==ev.getAction()){
			if (Math.abs(lastDownY - ev.getY()) < 5 && Math.abs(lastDownX - ev.getX() )< 5
					&& System.currentTimeMillis() - lastDownTime <= 400) {
				performOnClick(ev);
			}
    	}else if(MotionEvent.ACTION_DOWN==ev.getAction()){
    		lastDownTime = System.currentTimeMillis();
    		lastDownX = ev.getX();
    		lastDownY = ev.getY();
    	}
		return true;   
    }
    
    /**
     * 处理点击事件
     */
    private void performOnClick(MotionEvent e){
    	Log.i("bbb","BounceListView:performOnClick action="+e.getAction());
    	
    	//find which item is clicked
    	Rect viewRect = new Rect();
    	int x = (int)e.getX();
    	int y = (int)e.getY();
    	
		for(int i=0;i<getChildCount()-1;i++){
			View child = getChildAt(i);
			int left = child.getLeft();
			int right = child.getRight();
			int top = child.getTop();
			int bottom = child.getBottom();
			viewRect.set(left, top, right, bottom);
			if(viewRect.contains(x, y)){
				int index = 0;
		    	if(x>viewRect.right/2){
		    		index = 1;
		    	}
		    	//perform onItemClickListener
				if(onItemClickListener != null){
					Log.i("bbb","BounceListView:call OnItemClick action="+e.getAction());					
					onItemClickListener.onItemClick(BounceListView.this, child,
							2*(this.getFirstVisiblePosition() + i)+index,
							this.getFirstVisiblePosition() + i);
				}
				break;
			}
			
		}
    	
    };
    
    
    
    @Override 
    public void setOnItemClickListener(OnItemClickListener listener){
    	this.onItemClickListener = listener;
    }
    
	
}  
