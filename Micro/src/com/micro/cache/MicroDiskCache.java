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

package com.micro.cache;

import java.util.Collections;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroDiskCache
 * @Description: 描述：缓存接口
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:32:09
 * @version V1.0
 *
 */
public interface MicroDiskCache {
	/**
	 * Retrieves an entry from the cache.(从缓存里获取一个入口?)
	 * @param key Cache key
	 * @return An {@link Entry} or null in the event of a cache miss
	 */
	public Entry get(String key);

	/**
	 * Adds or replaces an entry to the cache.(添加或者替换一个入口到缓存?)
	 * @param key Cache key
	 * @param entry Data to store and metadata for cache coherency, TTL, etc.
	 */
	public void put(String key, Entry entry);

	/**
	 * Performs any potentially long-running actions needed to initialize the cache;(执行任何长时间操作必需初始化缓存)
	 * will be called from a worker thread.(需从工作线程中调用)
	 */
	public void initialize();


	/**
	 * Removes an entry from the cache.
	 * @param key Cache key
	 */
	public void remove(String key);

	/**
	 * Empties the cache.(清空)
	 */
	public void clear();


	/*********************************************************************************************************************************************************/


	/**
	 * 缓存数据实体.
	 */
	public static class Entry {

		/** 缓存数据. */
		public byte[] data;

		/** ETag. */
		public String etag;

		/** 缓存时间 总毫秒数. */
		public long serverTimeMillis;

		/** 失效日期 总毫秒数. */
		public long expiredTimeMillis;

		/** 响应头信息. */
		public Map<String, String> responseHeaders = Collections.emptyMap();

		/**
		 *  是否记录已经失效.
		 *
		 * @return true, if is expired
		 */
		public boolean isExpired() {
			return this.expiredTimeMillis < System.currentTimeMillis();
		}

	}

}
