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
package com.micro.db.task.thread;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

import android.os.Handler;
import android.os.Message;

import com.micro.db.task.MicroTaskItem;
import com.micro.db.task.MicroTaskListListener;
import com.micro.db.task.MicroTaskObjectListener;
// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroTaskPool
 * @Description: 描述：用andbase线程池
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:38:52
 * @version V1.0
 *
 */

public class MicroTaskPool{
	
	/** 单例对象 The http pool. */
	private static MicroTaskPool abTaskPool = null; 
	
	/** 线程执行器. */
	public static Executor mExecutorService = null;
	
	/**  存放返回的任务结果. */
    private static HashMap<String,Object> result;
	
	/** 下载完成后的消息句柄. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
        	MicroTaskItem item = (MicroTaskItem)msg.obj; 
        	if(item.getListener() instanceof MicroTaskListListener){
        		((MicroTaskListListener)item.getListener()).update((List<?>)result.get(item.toString())); 
        	}else if(item.getListener() instanceof MicroTaskObjectListener){
        		((MicroTaskObjectListener)item.getListener()).update(result.get(item.toString())); 
        	}else{
        		item.getListener().update(); 
        	}
        	result.remove(item.toString());
        } 
    }; 
    
	
	/**
	 * 构造线程池.
	 */
    private MicroTaskPool() {
        result = new HashMap<String,Object>();
        mExecutorService = MicroThreadFactory.getExecutorService();
    } 
	
	/**
	 * 单例构造图片下载器.
	 *
	 * @return single instance of AbHttpPool
	 */
    public static MicroTaskPool getInstance() { 
    	if (abTaskPool == null) { 
    		abTaskPool = new MicroTaskPool(); 
        } 
        return abTaskPool;
    } 
    
    /**
     * 执行任务.
     * @param item the item
     */
    public void execute(final MicroTaskItem item) {   
    	mExecutorService.execute(new Runnable() { 
    		public void run() {
    			try {
    				//定义了回调
                    if (item.getListener() != null) { 
                        if(item.getListener() instanceof MicroTaskListListener){
                            result.put(item.toString(), ((MicroTaskListListener)item.getListener()).getList());
                        }else if(item.getListener() instanceof MicroTaskObjectListener){
                            result.put(item.toString(), ((MicroTaskObjectListener)item.getListener()).getObject());
                        }else{
                        	item.getListener().get();
                            result.put(item.toString(), null);
                        }
                        
                    	//交由UI线程处理 
                        Message msg = handler.obtainMessage(); 
                        msg.obj = item; 
                        handler.sendMessage(msg); 
                    }                              
    			} catch (Exception e) { 
    				e.printStackTrace();
    			}                         
    		}                 
    	});                 
    	
    }
	
}
