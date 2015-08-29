/*
 * Copyright (C) 2013 Chengel_HaltuD
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
package com.demo;


/*import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;*/

import com.micro.utils.H;

import android.app.Application;

/**
 * 
 * @ClassName: BaseApplication
 * @Description: BaseApplication
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:42:42
 * @version V1.0
 *
 */
public class BaseApplication extends Application{

	/** BaseApplication **/
	private static BaseApplication mApp;
	/** 鍥剧墖涓嬭浇缂撳瓨澶勭悊鍣?  **/
	//public DisplayImageOptions options;
	/** 鍥剧墖涓嬭浇鍣?  **/
	//public ImageLoader imageLoader = null;

	@Override
	public void onCreate() {
		super.onCreate();

		// 鍒濆鍖栧浘鐗囦笅杞藉櫒
		//initImageLoader();

		mApp = this;


		// 初始化http的连接请求
		initHttpClient();
	}

	public static BaseApplication getInstance() {
		return mApp;
	}

	/**
	 * 鍥剧墖涓嬭浇鍣?
	 */
	/*public void initImageLoader() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(this, "gele/images");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
		.memoryCacheExtraOptions(480, 800)
		// max width, max height
		.threadPoolSize(2)
		.threadPriority(Thread.NORM_PRIORITY - 3)
		.denyCacheImageMultipleSizesInMemory()
		.memoryCache(new UsingFreqLimitedMemoryCache(1 * 1024 * 1024))
		// You can pass your own memory cache implementation
		.discCache(new UnlimitedDiscCache(cacheDir))
		// You can pass your own disc cache implementation
		.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		// Initialize ImageLoader with configuration
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)//瀛樻牴,搴旇鏄粯璁?
		.showImageForEmptyUri(R.drawable.ic_launcher)//url涓虹┖
		.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)//鍥剧墖鍔犺浇澶辫触
		.cacheOnDisc(true).build();
	}*/

	// -------------------------------------关于聊天的初始化数据---------------------------------------
	// -------------------------------------关于聊天的初始化数据---------------------------------------
	// -------------------------------------关于聊天的初始化数据---------------------------------------
	/**
	 * http 请求工具
	 */
	public H mAbHttpUtil = null;
	/**
	 * 初始化http请求客户端
	 */
	private void initHttpClient() {
		// 获取Http工具类
		mAbHttpUtil = H.getInstance(this);
		mAbHttpUtil.setTimeout(10000);
	}
}
