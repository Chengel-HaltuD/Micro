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
package com.micro.cache.image;



import com.micro.cache.MicroAppConfig;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: AbImageBaseCache
 * @Description: 描述：图片缓存
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:28:25
 * @version V1.0
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class MicroImageBaseCache implements MicroImageCache {

	/** LruCache. */
	private static LruCache<String, Bitmap> mCache;

	/** AbImageCache 实例. */
	private static MicroImageBaseCache mImageCache;

	
	/**
	 * 构造方法.
	 */
	public MicroImageBaseCache() {
		super();
		int maxSize = MicroAppConfig.MAX_CACHE_SIZE_INBYTES;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};
	}

	/**
	 * 
	 * 获取单例的AbImageCache.
	 * @return
	 */
	public static MicroImageBaseCache getInstance() {
		if(mImageCache == null){
			mImageCache = new MicroImageBaseCache();
		}
		return mImageCache;
	}
	
	

	/*********************************************************************************************************************************************************/
	
	
	
	/**
	 * 根据key获取缓存中的Bitmap.
	 *
	 * @param cacheKey the cache key
	 * @return the bitmap
	 */
	@Override
	public Bitmap getBitmap(String cacheKey) {
		return mCache.get(cacheKey);
	}

	/**
	 * 增加一个Bitmap到缓存中.
	 *
	 * @param cacheKey the cache key
	 * @param bitmap the bitmap
	 */
	@Override
	public void putBitmap(String cacheKey, Bitmap bitmap) {
		mCache.put(cacheKey, bitmap);
	}

	/**
	 * 获取用于缓存的Key.(缓存时候没用到,删除时候用的?)(首先得到一个cacheKey，这个是统一的规则)(获取一个请求的key,拼接规则就是使用#讲几个连接起来)
	 * 
	 *
	 * @param requestUrl the request url
	 * @param maxWidth the max width
	 * @param maxHeight the max height
	 * @return the cache key
	 */
	public String getCacheKey(String url, int maxWidth, int maxHeight) {
		return new StringBuilder(url.length() + 12).append("#W").append(maxWidth)
				.append("#H").append(maxHeight).append(url).toString();
	}

	/**
	 * 从缓存中删除一个Bitmap.
	 *
	 * @param requestUrl the request url
	 * @param maxWidth the max width
	 * @param maxHeight the max height
	 */
	@Override
	public void removeBitmap(String requestUrl, int maxWidth, int maxHeight) {
		mCache.remove(getCacheKey(requestUrl,maxWidth,maxHeight));
	}

	/**
	 * 释放所有缓存.
	 */
	public void clearBitmap() {
		mCache.evictAll();
	}

}
