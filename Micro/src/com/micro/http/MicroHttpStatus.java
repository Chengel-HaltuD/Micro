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


// TODO: Auto-generated Javadoc

/**
 * 
 * @ClassName: MicroHttpStatus
 * @Description: 描述：常量.
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:51:48
 * @version V1.0
 *
 */
public class MicroHttpStatus {
	
	/** 成功返回码. */
	public static final int SUCCESS_CODE = 200;
	
	/** 连接失败的HTTP返回码. */
	public static final int CONNECT_FAILURE_CODE = 600;
	
	/** 连接超时的HTTP返回码. */
	public static final int CONNECT_TIMEOUT_CODE = 601;
	
	/** 响应失败的HTTP返回码. */
	public static final int RESPONSE_TIMEOUT_CODE = 602;
	
	/** 未处理的HTTP返回码. */
	public static final int UNTREATED_CODE = 900;
	
	/** 服务出错的HTTP返回码. */
	public static final int SERVER_FAILURE_CODE = 500;
   
}
