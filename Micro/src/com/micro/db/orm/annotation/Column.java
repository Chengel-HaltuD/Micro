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
package com.micro.db.orm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc

/**
 * 
 * @ClassName: Column
 * @Description: 描述：表示列
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:26:08
 * @version V1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Column {
	
	/**
	 * 列名.
	 *
	 * @return the string
	 */
	public abstract String name();

	/**
	 * 列类型.
	 *
	 * @return the string
	 */
	public abstract String type() default "";

	/**
	 * 长度.
	 *
	 * @return the int
	 */
	public abstract int length() default 0;
	
}
