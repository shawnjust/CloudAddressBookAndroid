package com.example.utils.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * using this class need permission <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE" />
 * 这个工具类用来检查网络状况
 * @author ren jiayue
 * 
 */
final public class NetworkState {
	/**
	 * 检测WIFI是否连接
	 * 
	 * @param context
	 * @return 连接状态
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 检测网络是否连接
	 * 
	 * @param context 上下文
	 * @return 网络连接状况
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isConnected();
			}
		}
		return false;
	}
}
