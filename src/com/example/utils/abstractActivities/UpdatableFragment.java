package com.example.utils.abstractActivities;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;


abstract public class UpdatableFragment extends Fragment{
	abstract public void update();
	/**
	 * @author ren jiayue
	 *这个类调度搜索线程，防止在上一个搜索线程没有执行完时开始下一个搜索线程
	 *同时确保在有多个等待线程时只执行最后一个
	 */
	public class TaskSchedule<V,P,R>{
		private AsyncTask<V,P,R> runTask = null;
		private AsyncTask<V,P,R> waitTask = null;
		private V[] params = null;
		
		public TaskSchedule(){};
		
		@SuppressWarnings("unchecked")
		public void newTask(AsyncTask<V,P,R> task,V... params){
			this.params = params;
			
			if(runTask!=null){
				runTask.cancel(true);
				waitTask = task;
			}else{
				runTask = task;
				task.execute(params);
			}
			
		}
		/**
		 * 在异步线程结束后调用这个函数释放线程锁，否则下一个线程不会执行
		 * 在onPostExecuted和OnCancel里都要调用
		 */
		public void taskFinished(){
			runTask = null;
			if(waitTask!=null){
				runTask = waitTask;
				runTask.execute(params);
				waitTask = null;
			}
		}
	}
}
