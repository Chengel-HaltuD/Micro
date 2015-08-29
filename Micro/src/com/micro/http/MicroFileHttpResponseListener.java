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

import java.io.File;

import android.content.Context;

import com.micro.utils.F;


// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroFileHttpResponseListener
 * @Description: 描述：Http文件响应监听器
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:50:49
 * @version V1.0
 *
 */
public abstract class MicroFileHttpResponseListener extends MicroHttpResponseListener{
	
    /** 当前缓存文件. */
    private File mFile;
    
    /**
     * 下载文件的构造,用默认的缓存方式.
     *
     * @param url the url
     */
	public MicroFileHttpResponseListener(String url) {
		super();
	}
	
	/**
	 * 默认的构造.
	 */
	public MicroFileHttpResponseListener() {
		super();
	}
	
	/**
     * 下载文件的构造,指定缓存文件名称.
     * @param file 缓存文件名称
     */
    public MicroFileHttpResponseListener(File file) {
        super();
	    this.mFile = file;
    }
	
	/**
	 * 描述：下载文件成功会调用这里.
	 *
	 * @param statusCode the status code
	 * @param file the file
	 */
    public void onSuccess(int statusCode,File file){};
    
    /**
     * 描述：多文件上传成功调用.
     *
     * @param statusCode the status code
     */
    public void onSuccess(int statusCode){};
    
   
   /**
    * 成功消息.
    *
    * @param statusCode the status code
    */
    public void sendSuccessMessage(int statusCode){
    	sendMessage(obtainMessage(MicroHttpClient.SUCCESS_MESSAGE, new Object[]{statusCode}));
    }
    
    /**
     * 失败消息.
     *
     * @param statusCode the status code
     * @param error the error
     */
    public void sendFailureMessage(int statusCode,Throwable error){
    	sendMessage(obtainMessage(MicroHttpClient.FAILURE_MESSAGE, new Object[]{statusCode, error}));
    }
    

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return mFile;
	}

	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(File file) {
		this.mFile = file;
		try {
			if(!file.getParentFile().exists()){
			      file.getParentFile().mkdirs();
			}
			if(!file.exists()){
			      file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Sets the file.
	 *
	 * @param context the context
	 * @param name the name
	 */
	public void setFile(Context context,String name) {
		//生成缓存文件
        if(F.isCanUseSD()){
	    	File file = new File(F.getFileDownloadDir(context) + name);
	    	setFile(file);
        }
	}
    
}
