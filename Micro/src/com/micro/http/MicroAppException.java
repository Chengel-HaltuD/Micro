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
package com.micro.http;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import android.text.TextUtils;

import com.micro.cache.MicroAppConfig;



// TODO: Auto-generated Javadoc

/**
 * 
 * @ClassName: MicroAppException
 * @Description: 描述：公共异常类.
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:49:18
 * @version V1.0
 *
 */
public class MicroAppException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	
	/** 异常消息. */
	private String msg = null;

	/**
	 * 构造异常类.
	 *
	 * @param e 异常
	 */
	public MicroAppException(Exception e) {
		super();

		try {
			if( e instanceof HttpHostConnectException) {  
				msg = MicroAppConfig.UNKNOWN_HOST_EXCEPTION;
			}else if (e instanceof ConnectException) {
				msg = MicroAppConfig.CONNECT_EXCEPTION;
			}else if (e instanceof ConnectTimeoutException) {
				msg = MicroAppConfig.CONNECT_EXCEPTION;
			}else if (e instanceof UnknownHostException) {
				msg = MicroAppConfig.UNKNOWN_HOST_EXCEPTION;
			}else if (e instanceof SocketException) {
				msg = MicroAppConfig.SOCKET_EXCEPTION;
			}else if (e instanceof SocketTimeoutException) {
				msg = MicroAppConfig.SOCKET_TIMEOUT_EXCEPTION;
			}else if( e instanceof NullPointerException) {  
				msg = MicroAppConfig.NULL_POINTER_EXCEPTION;
			}else if( e instanceof ClientProtocolException) {  
				msg = MicroAppConfig.CLIENT_PROTOCOL_EXCEPTION;
			}else {
				if (e == null || TextUtils.isEmpty(e.getMessage())) {
					msg = MicroAppConfig.NULL_MESSAGE_EXCEPTION;
				}else{
				    msg = e.getMessage();
				}
			}
		} catch (Exception e1) {
		}
		
	}

	/**
	 * 用一个消息构造异常类.
	 *
	 * @param message 异常的消息
	 */
	public MicroAppException(String message) {
		super(message);
		msg = message;
	}

	/**
	 * 描述：获取异常信息.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return msg;
	}

}
