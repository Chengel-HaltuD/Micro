/*
 * Copyright (C) 2013 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.micro.utils;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.app.Activity;  
import android.content.ComponentName;  
import android.content.Context;  
import android.content.Intent;  
import android.net.ConnectivityManager;  
import android.net.NetworkInfo;  
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 
 * @ClassName: N
 * @Description: N是NetUtils跟网络相关的工具类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:01:23
 * @version V1.0
 *
 */
public class N  
{  
	private N()  
	{  
		/* cannot be instantiated */  
		throw new UnsupportedOperationException("cannot be instantiated");  
	}  

	/** 
	 * 判断网络是否连接 
	 *  
	 * @param context 
	 * @return 
	 */  
	public static boolean isConnected(Context context)  
	{  

		ConnectivityManager connectivity = (ConnectivityManager) context  
				.getSystemService(Context.CONNECTIVITY_SERVICE);  

		if (null != connectivity)  
		{  

			NetworkInfo info = connectivity.getActiveNetworkInfo();  
			if (null != info && info.isConnected())  
			{  
				if (info.getState() == NetworkInfo.State.CONNECTED)  
				{  
					return true;  
				}  
			}  
		}  
		return false;  
	}  

	/** 
	 * 判断是否是wifi连接 
	 */  
	public static boolean isWifi(Context context)  
	{  
		ConnectivityManager cm = (ConnectivityManager) context  
				.getSystemService(Context.CONNECTIVITY_SERVICE);  

		if (cm == null)  
			return false;  
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;  

	}  

	/** 
	 * 打开网络设置界面 
	 */  
	public static void openSetting(Activity activity)  
	{  
		Intent intent = new Intent("/");  
		ComponentName cm = new ComponentName("com.android.settings",  
				"com.android.settings.WirelessSettings");  
		intent.setComponent(cm);  
		intent.setAction("android.intent.action.VIEW");  
		activity.startActivityForResult(intent, 0);  

	}  


	/**
	 * 使用SSL不信任的证书
	 */
	public static  void useUntrustedCertificate() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
					}
				}
		};
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**********************************************W是WifiUtil 打开,是否连接，是否Wifi，Wifi列表，SSID过滤结果,Wifi信息*****************************************************/
	
	
	/**
	 * 
	 * 描述：打开wifi.
	 * @param context
	 * @param enabled
	 * @return
	 */
	public static void setWifiEnabled(Context context,boolean enabled){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if(enabled){
			wifiManager.setWifiEnabled(true);
		}else{
			wifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * 
	 * 描述：是否有网络连接.
	 * @param context
	 * @return
	 */
	public static boolean isConnectivity(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return ((connectivityManager.getActiveNetworkInfo() != null && connectivityManager
				.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || telephonyManager
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	/**
	 * 判断当前网络是否是wifi网络.
	 *
	 * @param context the context
	 * @return boolean
	 */
	public static boolean isWifiConnectivity(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 描述：得到所有的WiFi列表.
	 * @param context
	 * @return
	 */
	public static List<ScanResult> getScanResults(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> list = null;
		//开始扫描WiFi
		boolean f = wifiManager.startScan();
		if(!f){
			getScanResults(context);
		}else{
			// 得到扫描结果
			list = wifiManager.getScanResults();
		}

		return list;
	}

	/**
	 * 
	 * 描述：根据SSID过滤扫描结果.
	 * @param context
	 * @param bssid
	 * @return
	 */
	public static ScanResult getScanResultsByBSSID(Context context,String bssid) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		ScanResult scanResult = null;
		//开始扫描WiFi
		boolean f = wifiManager.startScan();
		if(!f){
			getScanResultsByBSSID(context,bssid);
		}
		// 得到扫描结果
		List<ScanResult> list = wifiManager.getScanResults();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				// 得到扫描结果
				scanResult = list.get(i);
				if (scanResult.BSSID.equals(bssid)) {
					break;
				}
			}
		}
		return scanResult;
	}

	/**
	 * 
	 * 描述：获取连接的WIFI信息.
	 * @param context
	 * @return
	 */
	public static WifiInfo getConnectionInfo(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo;
	}

}  