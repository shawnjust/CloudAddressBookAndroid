package com.example.utils.abstractActivities;

import com.example.cloudaddressbook.R;
import com.example.utils.utils.DensityUtil;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 支持下拉刷新的列表的活动
 * @author Ren Jiayue
 *用到的string 的定义如下：
 *<string name="loading">正在加载，请稍后&#8230;</string>
    <string name="pull_up_to_load_next_page">上拉加载下一页</string>
    <string name="release_to_load_next_page">松开加载下一页</string>
    <string name="connect_to_internet_fail">网络连接失败</string>
    <string name="fail_to_load">加载失败，请检查网络连接！</string>
    <string name="load_success">加载成功！</string>
    <string name="already_the_last_page">已经是最后一页了！</string>
    
    list及其容器布局定义参考如下
    <m.ETWINTERNATIONAL.COM.myviews.NonInterreptLinearLayout
        android:id="@+id/list_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="-50dp"
        android:background="#f6f6f6"
        android:orientation="vertical" >

        <TextView
            android:id="@+id/total_result_num"
            android:layout_width="match_parent"
            android:layout_height="30dp"
            android:background="#f6f6f6"
            android:gravity="center" 
            android:textColor="#b5b5b5"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="1dp" 
            android:background="#b5b5b5"/>

        <ListView
            android:id="@+id/products_list"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:divider="#d0d0d0"
            android:dividerHeight="1px"
            android:fadingEdgeLength="10dp"
            android:requiresFadingEdge="vertical"
            android:scrollbars="none" >
        </ListView>

        <LinearLayout
            android:id="@+id/load_next_page_view"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:background="@drawable/fading_edge"
            android:gravity="center"
            android:orientation="horizontal" >

            <TextView
                android:id="@+id/load_next_page_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/pull_up_to_load_next_page"
                android:textColor="#029eeb" />
        </LinearLayout>
    </m.ETWINTERNATIONAL.COM.myviews.NonInterreptLinearLayout>
 */
abstract public class PageListActivity extends UpdatableActivity{
	/**一次加载多少条产品信息**/
	protected int PAGE_SIZE = 10;//
	/**当前页码（已加载的最后一页的页码）**/
	protected int pageNum;//
	/**显示产品的列表容器**/
	protected ListView productsListView;//
	/**列表容器的适配器**/
	protected ListAdapter adapter;//
	/**列表的父容器**/
	private LinearLayout listContainer;//
	/**加载下一页文字**/
	private TextView loadNextPageText;//
	/**是否有http请求正在执行**/
	protected boolean isLoading = false;//
	/**是否滑动到底部**/
	private boolean isLastRow = false;//
	/**加载需要达到的最小滑动距离**/
	protected int LOAD_MIN_SCROLL = 250;//
	/**加载视图的高度**/
	protected int LOAD_VIEW_HEIGHT = 50;//
	/**屏幕的高度**/
	private int screenHeight;//
	/****/
	private MyOnItemClickListener onItemClickListener;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutRes());
		

		WindowManager wm = this.getWindowManager();
		screenHeight = wm.getDefaultDisplay().getHeight();

		// 将这两个标量（单位dp）转换成设备上的长度（px）
		LOAD_VIEW_HEIGHT = DensityUtil.dip2px(this, LOAD_VIEW_HEIGHT);
		LOAD_MIN_SCROLL = DensityUtil.dip2px(this, LOAD_MIN_SCROLL);

		// 初始化结果列表
		productsListView = (ListView) findViewById(getListViewId());
		adapter = getListAdapter();
		productsListView.setAdapter(adapter);

		// 列表容器
		listContainer = (LinearLayout) findViewById(getListViewContainerId());
		loadNextPageText = (TextView) findViewById(getLoadStateTextId());
		//初始化列表容器
		initListViewContainer();
		
		//初始化列表
		initListView();
		
		//设置查询第一页的数据
		pageNum = 1;
		
	}
	
	/**初始化列表容器**/
		public void initListViewContainer(){
			listContainer.setOnTouchListener(new OnTouchListener() {

				float y = 0;
				float updateScrollY = 0;
				float updateViewY = 0;
				boolean isupdating = false;

				@Override
				public boolean onTouch(View view, MotionEvent m) {

					if (isLoading) {
						return true;
					}

					if (MotionEvent.ACTION_DOWN == m.getAction()) {
						updateScrollY = m.getY();
						judgeReachTheBootom();
						if(isLastRow){
							isupdating = true;
							//不论按下的时候状态是否为更新，都要把DOWN事件派发给listview，以便listview中途可以相应MOVE事件
							productsListView.dispatchTouchEvent(m);
						}else{
							isupdating = false;
						}
						y = m.getY();
					}
					// 如果是最后一行
					if (isupdating) {
						// 如果是UP事件
						if (MotionEvent.ACTION_UP == m.getAction()) {
							isupdating = false;
							updateScrollY = m.getY() - updateScrollY;
							// 如果滑动距离超过了加载下一页的最小滑动距离，加载框弹回原位并开始加载
							if ((-updateScrollY) >= LOAD_MIN_SCROLL) {
								isLoading = true;

								TranslateAnimation tAnimi = new TranslateAnimation(
										0, 0, updateViewY, -LOAD_VIEW_HEIGHT);
								tAnimi.setAnimationListener(new AnimationListener() {

									@Override
									public void onAnimationStart(Animation arg0) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onAnimationRepeat(Animation arg0) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onAnimationEnd(Animation arg0) {
										// TODO Auto-generated method stub
										loadNextPageText.setText(getResources()
												.getString(R.string.loading));
										pageNum++;
										isLoading = true;
										//isLastRow = false;
										startLoadNextPage(pageNum);
									}
								});
								tAnimi.setFillAfter(true);
								tAnimi.setDuration(400);
								// updateStateView.startAnimation(tAnimi);
								listContainer.startAnimation(tAnimi);

							}
							// 如果滑动距离没有超过加载下一页的最小滑动距离，加载框滑动消失
							else {
								TranslateAnimation tAnimi = new TranslateAnimation(
										0, 0, updateViewY, 0);
								tAnimi.setFillAfter(true);
								listContainer.startAnimation(tAnimi);
								loadNextPageText.setText(getResources().getString(
										R.string.pull_up_to_load_next_page));
								//judgeReachTheBootom();
							}
							
							updateViewY = 0;
						}
						
					}
					if(MotionEvent.ACTION_MOVE == m.getAction()){
						// 如果是移动事件，加载框跟随手指上拉
						
						float scroll = m.getY() - y;
						y = m.getY();
						float updateViewY1 = updateViewY + scroll / 3;
						if (isupdating) {
							if (updateViewY1 < 0) {
								TranslateAnimation tAnimi = new TranslateAnimation(
										0, 0, updateViewY, updateViewY1);
								tAnimi.setFillAfter(true);
								listContainer.startAnimation(tAnimi);
							}
							else{
								isupdating = false;
								isLastRow = false;
								updateViewY = 0;
								loadNextPageText.setText(getResources().getString(
										R.string.pull_up_to_load_next_page));
								TranslateAnimation tAnimi = new TranslateAnimation(
										0, 0, updateViewY, 0);
								tAnimi.setFillAfter(true);
								listContainer.startAnimation(tAnimi);
								updateViewY1 = 0;
							}
							if (updateScrollY - m.getY() < LOAD_MIN_SCROLL) {
								loadNextPageText.setText(getResources().getString(
										R.string.pull_up_to_load_next_page));
							}else {
								loadNextPageText.setText(getResources().getString(
										R.string.release_to_load_next_page));
							}
							updateViewY = updateViewY1;
						}else if(updateViewY1 < 0){
							judgeReachTheBootom();
							if(isLastRow){
								isupdating = true;
								updateScrollY = m.getY();
								productsListView.dispatchTouchEvent(m);
							}else{
								isupdating = false;
							}
						}
					}
					if(!isupdating){
						productsListView.dispatchTouchEvent(m);
					}
					return true;
				}

			});

		}
	
		/**初始化列表**/
		public void initListView(){
			
					
					//监听产品列表的点击事件
					productsListView.setOnItemClickListener(new OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> parent, View item, int pos,
								long id) {
							if(isLoading){
								return;
							}
							
							if(onItemClickListener!=null){
								Object data = adapter.getItem(pos);
								onItemClickListener.onItemClick(data);
							}
							
						}
						
					});
		}
		
	/**
	 * 检查是否滑动到了列表底部	
	 */
	public void judgeReachTheBootom(){
		if(productsListView.getLastVisiblePosition() < productsListView.getCount()-1){
			isLastRow = false;
			return;
		}
		try {
			View lastItem = productsListView
					.getChildAt(productsListView
							.getLastVisiblePosition()
							- productsListView.getFirstVisiblePosition());
			int[] location = new int[2];
			lastItem.getLocationOnScreen(location);
			
			// 如果碰到了底部（允许5px的误差）
			if (location[1] + lastItem.getHeight() <= screenHeight + 5) {
				isLastRow = true;
			}
			
		} catch (NullPointerException e) {
		}
	}
	
	public void scrollToFirst(){
		productsListView.setSelection(0);
	}
	public void setOnItemCLickListener(MyOnItemClickListener listener){
		this.onItemClickListener = listener;
	}
	
	public interface MyOnItemClickListener{
		public void onItemClick(Object item);
	}
	/**	
	 * @return 活动布局资源文件ID
	 */
	abstract protected int getActivityLayoutRes();
	
	/**	
	 * @return 列表视图ID
	 */
	abstract protected int getListViewId();
	/**	
	 * @return 列表容器视图ID
	 */
	abstract protected int getListViewContainerId();
	/**	
	 * @return 加载状态文字视图ID
	 */
	abstract protected int getLoadStateTextId();
	/**	
	 * 开始加载下一页
	 */
	abstract protected void startLoadNextPage(int pageNum);
	/**
	 * 获得列表适配器
	 */
	abstract protected ListAdapter getListAdapter();
	
	/**
	 * 在task结束后必须调用这个函数来隐藏加载进度条
	 */
	public void onTaskDone(String msg){
		//在加载视图显示加载成功，2秒后隐藏加载框
		loadNextPageText.setText(msg);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TranslateAnimation tAnimi = new TranslateAnimation(0,
						0, -LOAD_VIEW_HEIGHT, 0);
				tAnimi.setFillAfter(true);
				tAnimi.setDuration(500);
				tAnimi.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation arg0) {
						// TODO Auto-generated method stub
						isLoading = false;
						//isLastRow = false;
					}
				});
				//updateStateView.startAnimation(tAnimi);
				listContainer.startAnimation(tAnimi);
			}
		}, 2000);
	}
	
}
