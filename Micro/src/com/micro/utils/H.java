/*
 * Copyright (C) 2012 Chengel_HaltuD
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


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.micro.http.MicroBinaryHttpResponseListener;
import com.micro.http.MicroFileHttpResponseListener;
import com.micro.http.MicroHttpClient;
import com.micro.http.MicroHttpResponseListener;
import com.micro.http.MicroRequestParams;
import com.micro.http.MicroStringHttpResponseListener;

// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: H
 * @Description: H是AbHttpUtil 描述：Http执行工具类，可处理get，post，以及异步处理文件的上传下载,HTTP工具类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:05:56
 * @version V1.0
 *
 */
public class H {

	/** 实例化单例对象. */
	private MicroHttpClient mClient = null;

	/** 工具类单例. */
	private static H mAbHttpUtil = null;

	/**
	 * 描述：获取实例.
	 *
	 * @param context the context
	 * @return single instance of AbHttpUtil
	 */
	public static H getInstance(Context context){
		if (null == mAbHttpUtil){
			mAbHttpUtil = new H(context);
		}

		return mAbHttpUtil;
	}

	/**
	 * 初始化AbHttpUtil.
	 *
	 * @param context the context
	 */
	private H(Context context) {
		super();
		this.mClient = new MicroHttpClient(context);
	}

	/**
	 * 描述：无参数的get请求.
	 *
	 * @param url the url
	 * @param responseListener the response listener
	 */
	public void get(String url, MicroHttpResponseListener responseListener) {
		mClient.get(url,null,responseListener);
	}

	/**
	 * 描述：带参数的get请求.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	public void get(String url, MicroRequestParams params,
			MicroHttpResponseListener responseListener) {
		mClient.get(url, params, responseListener);
	}

	/**
	 *  
	 * 描述：下载数据使用，会返回byte数据(下载文件或图片).
	 *
	 * @param url the url
	 * @param responseListener the response listener
	 */
	public void get(String url, MicroBinaryHttpResponseListener responseListener) {
		mClient.get(url,null,responseListener);
	}

	/**
	 * 描述：文件下载的get.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	public void get(String url, MicroRequestParams params,
			MicroFileHttpResponseListener responseListener) {
		mClient.get(url, params, responseListener);
	}

	/**
	 * 描述：无参数的post请求.
	 *
	 * @param url the url
	 * @param responseListener the response listener
	 */
	public void post(String url, MicroHttpResponseListener responseListener) {
		mClient.post(url,null, responseListener);
	}

	/**
	 * 描述：带参数的post请求.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	public void post(String url, MicroRequestParams params,
			MicroHttpResponseListener responseListener) {
		mClient.post(url, params, responseListener);
	}

	/**
	 * 描述：文件下载的post.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	public void post(String url, MicroRequestParams params,
			MicroFileHttpResponseListener responseListener) {
		mClient.post(url, params, responseListener);
	}

	/**
	 * 描述：一般通用请求.
	 *
	 * @param url the url
	 * @param responseListener the response listener
	 */
	public void request(String url,MicroStringHttpResponseListener responseListener) {
		request(url,null,responseListener);
	}

	/**
	 * 描述：一般通用请求.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	public void request(String url, MicroRequestParams params,
			MicroStringHttpResponseListener responseListener) {
		mClient.doRequest(url, params, responseListener);
	}

	/**
	 * 描述：设置连接超时时间(第一次请求前设置).
	 *
	 * @param timeout 毫秒
	 */
	public void setTimeout(int timeout) {
		mClient.setTimeout(timeout);
	}

	/**
	 * 打开ssl 自签名(第一次请求前设置).
	 * @param enabled
	 */
	public void setEasySSLEnabled(boolean enabled){
		mClient.setOpenEasySSL(enabled);
	}

	/**
	 * 设置编码(第一次请求前设置).
	 * @param encode
	 */
	public void setEncode(String encode) {
		mClient.setEncode(encode);
	}

	/**
	 * 设置用户代理(第一次请求前设置).
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent) {
		mClient.setUserAgent(userAgent);
	}

	/**
	 * 关闭HttpClient
	 * 当HttpClient实例不再需要是，确保关闭connection manager，以释放其系统资源  
	 */
	public void shutdownHttpClient(){
		if(mClient != null){
			mClient.shutdown();
		}
	}


	/********************************************************************************************************************************************************/


	// 下载JSON的工具方法
	public static String loadJsonFromNet(String url, String method)
			throws Exception {
		// 创建一个HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest request = null;
		if ("get".equals(method)) {// 如果是get请求
			// 创建一个get请求
			request = new HttpGet(url);
		} else if ("post".equals(method)) {// 如果是post请求
			request = new HttpPost(url);
		}

		// 执行请求
		HttpResponse response = client.execute(request);
		// 2xx:标示请求成功;3xx:重定向;4xx:客户端错误;5xx:服务器端错误
		if (response.getStatusLine().getStatusCode() == 200) {
			// 得到一个网络实体"尸体".
			HttpEntity entity = response.getEntity();
			// 如果要返回流
			// InputStream content = entity.getContent();
			return EntityUtils.toString(entity);
		}

		return null;

	}

	// 下载图片的工具方法
	public static byte[] loadImgFromNet(String url, String method)
			throws Exception {
		// 创建一个HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest request = null;
		if ("get".equals(method)) {// 如果是get请求
			// 创建一个get请求
			request = new HttpGet(url);
		} else if ("post".equals(method)) {// 如果是post请求
			request = new HttpPost(url);
		}

		// 执行请求
		HttpResponse response = client.execute(request);
		// 2xx:标示请求成功;3xx:重定向;4xx:客户端错误;5xx:服务器端错误
		if (response.getStatusLine().getStatusCode() == 200) {
			// 得到一个网络实体"尸体".
			HttpEntity entity = response.getEntity();
			// 如果要返回流
			// InputStream content = entity.getContent();
			return EntityUtils.toByteArray(entity);
		}

		return null;

	}

}
