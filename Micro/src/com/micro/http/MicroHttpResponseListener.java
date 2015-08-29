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

import android.os.Handler;
import android.os.Message;



// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroHttpResponseListener
 * @Description: 描述：Http响应监听器
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:51:35
 * @version V1.0
 *
 */
public abstract class MicroHttpResponseListener {
	
    /** The handler. */
    private Handler mHandler;
    
    /**
     * 构造.
     */
    public MicroHttpResponseListener() {
		super();
	}

	/**
	 * 描述：获取数据开始.
	 */
    public abstract void onStart();
    
    
    /**
	 * 完成后调用，失败，成功，调用.
	 */
    public abstract void onFinish();
    
    /**
	 * 重试.
	 */
    public void onRetry() {}
    
    /**
     * 描述：失败，调用.
     *
     * @param statusCode the status code
     * @param content the content
     * @param error the error
     */
    public abstract void onFailure(int statusCode, String content,Throwable error);
    
    /**
     * 进度.
     *
     * @param bytesWritten the bytes written
     * @param totalSize the total size
     */
    public void onProgress(long bytesWritten, long totalSize) {}
    
    /**
     * 开始消息.
     */
    public void sendStartMessage(){
    	sendMessage(obtainMessage(MicroHttpClient.START_MESSAGE, null));
    }
    
    /**
     * 完成消息.
     */
    public void sendFinishMessage(){
    	sendMessage(obtainMessage(MicroHttpClient.FINISH_MESSAGE,null));
    }
    
    /**
     * 进度消息.
     *
     * @param bytesWritten the bytes written
     * @param totalSize the total size
     */
    public void sendProgressMessage(long bytesWritten, long totalSize) {
        sendMessage(obtainMessage(MicroHttpClient.PROGRESS_MESSAGE, new Object[]{bytesWritten, totalSize}));
    }
    
    /**
     * 失败消息.
     *
     * @param statusCode the status code
     * @param content the content
     * @param error the error
     */
    public void sendFailureMessage(int statusCode,String content,Throwable error){
    	sendMessage(obtainMessage(MicroHttpClient.FAILURE_MESSAGE, new Object[]{statusCode,content, error}));
    }
    
    /**
     * 重试消息.
     */
    public void sendRetryMessage() {
        sendMessage(obtainMessage(MicroHttpClient.RETRY_MESSAGE, null));
    }
    
    /**
     * 发送消息.
     * @param msg the msg
     */
    public void sendMessage(Message msg) {
        if (msg != null) {
        	msg.sendToTarget();
        }
    }
    
    /**
     * 构造Message.
     * @param responseMessage the response message
     * @param response the response
     * @return the message
     */
    protected Message obtainMessage(int responseMessage, Object response) {
        Message msg;
        if (mHandler != null) {
            msg = mHandler.obtainMessage(responseMessage, response);
        } else {
            msg = Message.obtain();
            if (msg != null) {
                msg.what = responseMessage;
                msg.obj = response;
            }
        }
        return msg;
    }

	/**
	 * Gets the handler.
	 *
	 * @return the handler
	 */
	public Handler getHandler() {
		return mHandler;
	}

	/**
	 * 描述：设置Handler.
	 *
	 * @param handler the new handler
	 */
	public void setHandler(Handler handler) {
		this.mHandler = handler;
	}

}
