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
 * @ClassName: MicroStringHttpResponseListener
 * @Description: TODO
 * @Author：Chengel_HaltuD
 * @Date：2015-6-5 上午9:41:11
 * @version V1.0
 *
 */
public abstract class MicroStringHttpResponseListener extends MicroHttpResponseListener{
	
    /**
     * 构造.
     */
	public MicroStringHttpResponseListener() {
		super();
	}

	/**
	 * 描述：获取数据成功会调用这里.
	 *
	 * @param statusCode the status code
	 * @param content the content
	 */
    public abstract void onSuccess(int statusCode,String content);
    
    
    /**
     * 成功消息.
     *
     * @param statusCode the status code
     * @param content the content
     */
    public void sendSuccessMessage(int statusCode,String content){
    	sendMessage(obtainMessage(MicroHttpClient.SUCCESS_MESSAGE, new Object[]{statusCode, content}));
    }
		

}
