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
package com.micro.db.task;




// TODO: Auto-generated Javadoc

/**
 * 
 * @ClassName: MicroTaskObjectListener
 * @Description: 描述：数据执行的接口.
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:41:23
 * @version V1.0
 *
 */
public abstract class MicroTaskObjectListener extends MicroTaskListener{
	
	/**
	 * 执行开始
	 * @return 返回的结果对象
	 */
    public abstract <T extends Object> T getObject();
    
    /**
     * 执行开始后调用.
     * @param obj
     */
    public abstract <T extends Object> void update(T obj); 
    
	
}
