package com.example.utils.webservice;

import java.util.ArrayList;

import com.example.utils.entities.Message;
import com.example.utils.entities.PersonInfo;
import com.example.utils.entities.XunPanItem;
/**
 * 连接服务器并从服务器获取数据的类
 * @author Jiayue Ren
 *这个类
 */
public class ConnectServer {
	
	public volatile static ConnectServer connect;

	// set ConnectServer as an singleton class
	public static ConnectServer getInstance() {
		synchronized (ConnectServer.class) {
			if (connect == null)
				connect = new ConnectServer();
		}
		return connect;
	}

	/**
	 * 登陆验证
	 * @param userName 用户名
	 * @param password 密码
	 * @return login succeed, return 0 
	 * wrong password, return 2 
	 * userId doesn'texist, return 1
	 * connect failed, return -1
	 */
	public int logIn(String userName, String password) {
		PersonInfo.userId = "0";
		PersonInfo.userName = userName;
		PersonInfo.password = password;
		return 0;
	}

	/**
	 * 获得询盘总数
	 * @return 询盘总数，网络连接失败返回null
	 */
	public int getTotalXunpanNum(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 6000;
	}
	
	
	public int getMyFavNum(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 312;
	}
	
	
	public int getSubMeNum(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 10000;
	}
	
	public int getTotalNum(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 10000;
	}
	//互相关注
	public int getFriendsNum(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 31200;
	}
	
	
	/**
	 * 分页获得通知内容
	 * @param page 要获得的页数
	 * @return 该页数的通知，网络连接失败返回null
	 * message最后一个参数：
	 * 0：加好友请求，未处理
	 * 1：加好友求求，已通过
	 * 2：加好友请求，已拒绝
	 * 3：对方通过了加好友请求
	 * 4:您的好友xxx最近更换了号码
	 */
	public ArrayList<Message> getNoticeListByPage(int page){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		ArrayList<Message> result = new ArrayList<Message>();
		if(page>=3){
			return result;
		}
		for(int i = 0; i<5;i++){
		result.add(new Message(i,"aaa", "请求加你为好友", "2014-10-20", 0));
		}
		result.add(new Message(6,"bbb", "通过了你的好友请求", "2014-10-20", 3));
		result.add(new Message(7,"aaa", "请求加你为好友", "2014-10-20", 2));
		result.add(new Message(8,"aaa", "请求加你为好友", "2014-10-20", 1));
		return result;
	}
	/**
	 * 分页获得通知内容
	 * @param page 要获得的页数
	 * @param filter 筛选条件，不同二进制位代表不同类别的筛选条件
	 * @return 该页数的通知，网络连接失败返回null
	 */
	public ArrayList<XunPanItem> getXunPan(int page, int filter){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		ArrayList<XunPanItem> result = new ArrayList<XunPanItem>();
		if(page>=3){
			return result;
		}
		for(int i = 0; i<3;i++){
		result.add(new XunPanItem("任浃月"+2*i, "13761725087", 0, "China", "Chinese", 0, "etwservice@etw.com", ""));
		result.add(new XunPanItem("任浃月"+(2*i+1), "13761725087", 1, "China", "Chinese", 0, "etwservice@etw.com", ""));
		result.add(new XunPanItem("任浃月"+(2*i+1), "13761725087", 2, "China", "Chinese", 0, "etwservice@etw.com", ""));
		}
		return result;
	}

	public ArrayList<XunPanItem> searchItem(int pageNum, String key) {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		ArrayList<XunPanItem> result = new ArrayList<XunPanItem>();
		if(pageNum>=3){
			return result;
		}
		for(int i = 0; i<3;i++){
		result.add(new XunPanItem("任浃月"+2*i, "13761725087", 3, "China", "Chinese", 0, "etwservice@etw.com", ""));
		}
		return result;
	}
}
