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
	
	/**
	 * 获得未读询盘总数
	 * @return 未读询盘总数，网络连接失败返回null
	 */
	public int getUnreadedXunpanNum(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 312;
	}
	
	/**
	 * 获得访问总人数
	 * @return 总人数，网络连接失败返回null
	 */
	public int getVisitNum(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 10000;
	}
	/**
	 * 获得访问总次数
	 * @return 访问总次数，网络连接失败返回null
	 */
	public int getVisitTime(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		return 31200;
	}
	/**
	 * 获得公告
	 * @return 公告数组，网络连接失败返回null
	 */
	public ArrayList<String> getNoticeList(){
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			
		}
		ArrayList<String> result = new ArrayList<String>();
		result.add("重 要 通 知\n"
				+ "远程市场开发，距离的增加，会促使采购商对您企业不信任程度的增加！\n"
				+ "我们在回访中发现，使用国际在线视频营销的客户，其询盘转化率大大高于使用前，最高达一倍左右！\n"
				+ "因此，为了提高ETW老客户的询盘收获率，和感谢老客户多年来对ETW国际的支持和认可，ETW国际决定：从2014年8月1日开始，为选择视频营销的老客户免除9000元的拍摄费用，截止时间为：2014年10月31日，特此敬告！\n"
				+ "ETW国际客服部！");
		result.add("重 要 通 知\n"
				+ "远程市场开发，距离的增加，会促使采购商对您企业不信任程度的增加！\n"
				+ "我们在回访中发现，使用国际在线视频营销的客户，其询盘转化率大大高于使用前，最高达一倍左右！\n"
				+ "因此，为了提高ETW老客户的询盘收获率，和感谢老客户多年来对ETW国际的支持和认可，ETW国际决定：从2014年8月1日开始，为选择视频营销的老客户免除9000元的拍摄费用，截止时间为：2014年10月31日，特此敬告！\n"
				+ "ETW国际客服部！");
		result.add("重 要 通 知\n"
				+ "远程市场开发，距离的增加，会促使采购商对您企业不信任程度的增加！\n"
				+ "我们在回访中发现，使用国际在线视频营销的客户，其询盘转化率大大高于使用前，最高达一倍左右！\n"
				+ "因此，为了提高ETW老客户的询盘收获率，和感谢老客户多年来对ETW国际的支持和认可，ETW国际决定：从2014年8月1日开始，为选择视频营销的老客户免除9000元的拍摄费用，截止时间为：2014年10月31日，特此敬告！\n"
				+ "ETW国际客服部！");
		return result;
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
