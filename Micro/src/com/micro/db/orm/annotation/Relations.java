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
 * @ClassName: Relations
 * @Description: 描述：表示关联表
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:27:30
 * @version V1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Relations {
	
	/**
	 * 关联名,对象内唯一即可.
	 *
	 * @return the string
	 */
	public abstract String name();
	
	/**
	 * 外键.
	 *
	 * @return the string
	 */
	public abstract String foreignKey();
	
	/**
	 * 关联类型.
	 *
	 * @return the string  one2one  one2many many2many
	 */
	public abstract String type();
	
	/**
	 * 关联类型.
	 *
	 * @return the string  query insert query_insert
	 */
	public abstract String action() default "query_insert";
}
