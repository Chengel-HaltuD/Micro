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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.micro.cache.MicroAppConfig;
import com.micro.cache.MicroCacheHeaderParser;
import com.micro.cache.MicroCacheResponse;
import com.micro.cache.MicroDiskBaseCache;
import com.micro.cache.MicroDiskCache.Entry;
import com.micro.db.task.MicroTaskItem;
import com.micro.db.task.MicroTaskObjectListener;
import com.micro.db.task.thread.MicroTaskQueue;
import com.micro.utils.A;
import com.micro.utils.C;
import com.micro.utils.I;
import com.micro.utils.F;
import com.micro.utils.L;

// TODO: Auto-generated Javadoc

/**
 * 
 * @ClassName: AbImageLoader
 * @Description: 描述：下载图片并显示的工具类.
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:29:41
 * @version V1.0
 *
 */
public class MicroImageLoader { 

	/** 上下文. */
	private Context context = null;

	/** 单例对象. */
	private static MicroImageLoader imageLoader = null; 

	/** 缓存超时时间设置. */
	private int expiresTime;

	/** 请求队列. */
	private List<MicroTaskQueue> taskQueueList;

	/** 图片缓存. */
	private MicroImageBaseCache memCache;


	/** 磁盘缓存. */
	private MicroDiskBaseCache diskCache;

	/** 显示为下载中的图片. */
	private Drawable loadingImage;

	/** 显示为下载中的View. */
	private View loadingView;

	/** 显示下载失败的图片. */
	private Drawable errorImage;

	/** 图片未找到的图片. */
	private Drawable emptyImage;

	/**
	 * 构造图片下载器.
	 *
	 * @param context the context
	 */
	public MicroImageLoader(Context context) {
		this.context = context;
		this.expiresTime = MicroAppConfig.IMAGE_CACHE_EXPIRES_TIME;
		this.taskQueueList = new ArrayList<MicroTaskQueue>();
		PackageInfo info = A.getPackageInfo(context);
		File cacheDir = null;
		if(!F.isCanUseSD()){
			cacheDir = new File(context.getCacheDir(), info.packageName);
		}else{
			cacheDir = new File(F.getCacheDownloadDir(context));
		}
		this.diskCache = new MicroDiskBaseCache(cacheDir);
		this.memCache = MicroImageBaseCache.getInstance();
	} 


	/**
	 * 
	 * 获得一个实例.
	 * @param context
	 * @return
	 */
	public static MicroImageLoader getInstance(Context context) {
		if (imageLoader == null) { 
			imageLoader = new MicroImageLoader(context); 
		}
		return imageLoader;
	}

	/**
	 * 
	 * 显示这个图片.
	 * @param imageView
	 * @param url
	 * @param desiredWidth
	 * @param desiredHeight
	 */
	public void display(ImageView imageView,String url,int desiredWidth, int desiredHeight){
		download(imageView,url,desiredWidth,desiredHeight,new OnImageListener() {

			@Override
			public void onSuccess(ImageView imageView, Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
			}

			@Override
			public void onLoading(ImageView imageView) {

			}

			@Override
			public void onError(ImageView imageView) {

			}

			@Override
			public void onEmpty(ImageView imageView) {

			}
		});
	}

	/**
	 * 
	 * 显示这个图片(按照原图尺寸).
	 * ImageView使用android:scaleType="fitXY"属性仍旧可以充满
	 * @param imageView
	 * @param url
	 */
	public void display(ImageView imageView,String url){
		download(imageView,url,-1,-1,new OnImageListener() {

			@Override
			public void onSuccess(ImageView imageView, Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
			}

			@Override
			public void onLoading(ImageView imageView) {

			}

			@Override
			public void onError(ImageView imageView) {

			}

			@Override
			public void onEmpty(ImageView imageView) {

			}
		});
	}

	/**
	 * 
	 * 显示这个图片.
	 * @param imageView
	 * @param loadingView
	 * @param url
	 * @param desiredWidth
	 * @param desiredHeight
	 */
	public void display(final ImageView imageView,final View loadingView,final String url,final int desiredWidth,final int desiredHeight){
		download(imageView,url,desiredWidth,desiredHeight,new OnImageListener() {

			public void onSuccess(ImageView imageView, Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
				imageView.setVisibility(View.VISIBLE);
				loadingView.setVisibility(View.GONE);
			}

			@Override
			public void onLoading(ImageView imageView) {
				imageView.setVisibility(View.GONE);
				loadingView.setVisibility(View.VISIBLE);
				imageView.setImageDrawable(loadingImage);
			}

			@Override
			public void onError(ImageView imageView) {
				imageView.setImageDrawable(errorImage);
			}

			@Override
			public void onEmpty(ImageView imageView) {
				imageView.setImageDrawable(emptyImage);
			}
		});
	}


	/**
	 * 
	 * 下载这个图片.
	 * @param imageView
	 * @param url
	 * @param desiredWidth
	 * @param desiredHeight
	 */
	public void download(final ImageView imageView,final String url,final int desiredWidth,final int desiredHeight,final OnImageListener onImageListener) { 

		if(TextUtils.isEmpty(url)){
			if(onImageListener!=null){
				onImageListener.onEmpty(imageView);
			}
			return;
		}

		final String cacheKey = memCache.getCacheKey(url, desiredWidth, desiredHeight);
		//先看内存
		Bitmap bitmap = memCache.getBitmap(cacheKey);
		L.I("从LRUCache中获取到的图片"+cacheKey+":"+bitmap);

		if(bitmap != null){
			if(onImageListener!=null){
				onImageListener.onSuccess(imageView, bitmap);
			}
		}else{

			if(onImageListener!=null){
				onImageListener.onLoading(imageView);
			}

			if(imageView != null){
				//设置标记,目的解决闪烁问题
				imageView.setTag(url);
			}

			MicroTaskItem item = new MicroTaskItem();
			item.setListener(new MicroTaskObjectListener(){

				@Override
				public <T> void update(T entity) {
					MicroBitmapResponse response = (MicroBitmapResponse)entity;
					if(response == null){
						if(onImageListener!=null){
							onImageListener.onError(imageView);
						}
					}else{
						Bitmap bitmap = response.getBitmap();
						//要判断这个imageView的url有变化，如果没有变化才set
						//有变化就取消，解决列表的重复利用View的问题
						if(bitmap==null){
							if(onImageListener!=null){
								onImageListener.onEmpty(imageView);

							}
						}else if(imageView == null || url.equals(imageView.getTag())){
							if(onImageListener!=null){
								onImageListener.onSuccess(imageView, bitmap);
							}
						}
						L.D("获取到图片："+bitmap);
					}

				}

				@Override
				public MicroBitmapResponse getObject() {
					return getBitmapResponse(url, desiredWidth, desiredHeight);
				}

			});

			add2Queue(item);
		}

	} 


	/**
	 * 
	 * 下载这个图片.
	 * @param url
	 * @param desiredWidth
	 * @param desiredHeight
	 */
	public void download(final String url,final int desiredWidth,final int desiredHeight,final OnImageListener2 onImageListener) { 

		if(TextUtils.isEmpty(url)){
			if(onImageListener!=null){
				onImageListener.onEmpty();
			}
			return;
		}

		final String cacheKey = memCache.getCacheKey(url, desiredWidth, desiredHeight);
		//先看内存
		Bitmap bitmap = memCache.getBitmap(cacheKey);
		L.D("从LRUCache中获取到的图片"+cacheKey+":"+bitmap);

		if(bitmap != null){
			if(onImageListener!=null){
				onImageListener.onSuccess(bitmap);
			}
		}else{

			if(onImageListener!=null){
				onImageListener.onLoading();
			}

			MicroTaskItem item = new MicroTaskItem();
			item.setListener(new MicroTaskObjectListener(){

				@Override
				public <T> void update(T entity) {
					MicroBitmapResponse response = (MicroBitmapResponse)entity;
					if(response == null){
						if(onImageListener!=null){
							onImageListener.onError();
						}
					}else{
						Bitmap bitmap = response.getBitmap();
						//要判断这个imageView的url有变化，如果没有变化才set
						//有变化就取消，解决列表的重复利用View的问题
						if(bitmap==null){
							if(onImageListener!=null){
								onImageListener.onEmpty();
							}
						}else {
							if(onImageListener!=null){
								onImageListener.onSuccess(bitmap);
							}
						}
						L.D("获取到图片："+bitmap);
					}

				}

				@Override
				public MicroBitmapResponse getObject() {
					try {
						return getBitmapResponse(url, desiredWidth, desiredHeight);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}

			});

			add2Queue(item);
		}

	} 

	/**
	 * 
	 * 获取AbBitmapResponse.
	 * @param url
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	public MicroBitmapResponse getBitmapResponse(String url,int desiredWidth,int desiredHeight){
		MicroBitmapResponse bitmapResponse = null;
		try {
			final String cacheKey = memCache.getCacheKey(url, desiredWidth, desiredHeight);
			Bitmap bitmap = null;
			//看磁盘
			Entry entry = diskCache.get(cacheKey);
			if(entry == null || entry.isExpired()){
				L.I("图片磁盘中没有，或者已经过期");

				MicroCacheResponse response = C.getCacheResponse(url,expiresTime);
				if(response!=null){
					bitmap =  I.getBitmap(response.data, desiredWidth, desiredHeight);
					if(bitmap!=null){
						memCache.putBitmap(cacheKey, bitmap);
						diskCache.put(cacheKey, MicroCacheHeaderParser.parseCacheHeaders(response));
					}
				}
			}else{
				//磁盘中有
				byte [] bitmapData = entry.data;
				bitmap =  I.getBitmap(bitmapData, desiredWidth, desiredHeight);
				memCache.putBitmap(cacheKey, bitmap);
			}

			bitmapResponse = new MicroBitmapResponse(url);
			bitmapResponse.setBitmap(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmapResponse;
	}


	/**
	 * 获取失效时间.
	 *
	 * @return the expires time
	 */
	public int getExpiresTime() {
		return expiresTime;
	}


	/**
	 * 设置失效时间.
	 *
	 * @param expiresTime the new expires time
	 */
	public void setExpiresTime(int expiresTime) {
		this.expiresTime = expiresTime;
	}

	/**
	 * 监听器
	 */
	public interface OnImageListener {

		/**
		 * On Empty.
		 * @param imageView
		 */
		public void onEmpty(ImageView imageView);

		/**
		 * On Loading.
		 * @param imageView
		 */
		public void onLoading(ImageView imageView);

		/**
		 * On Error.
		 * @param imageView
		 */
		public void onError(ImageView imageView);

		/**
		 * 
		 * On response.
		 * @param imageView
		 * @param bitmap
		 */
		public void onSuccess(ImageView imageView,Bitmap bitmap);
	}

	/**
	 * 监听器
	 */
	public interface OnImageListener2 {

		/**
		 * On Empty.
		 * @param imageView
		 */
		public void onEmpty();

		/**
		 * On Loading.
		 * @param imageView
		 */
		public void onLoading();

		/**
		 * On Error.
		 * @param imageView
		 */
		public void onError();

		/**
		 * 
		 * On response.
		 * @param imageView
		 * @param bitmap
		 */
		public void onSuccess(Bitmap bitmap);
	}

	/**
	 * 
	 * 增加到最少的队列中.
	 * @param item
	 */
	private void add2Queue(MicroTaskItem item){
		int minQueueIndex = 0;
		if(taskQueueList.size() == 0){
			MicroTaskQueue queue = MicroTaskQueue.newInstance();
			taskQueueList.add(queue);
			queue.execute(item);
		}else{
			int minSize = 0;
			for(int i=0;i<taskQueueList.size();i++){
				MicroTaskQueue queue = taskQueueList.get(i);
				int size = queue.getTaskItemListSize();
				if(i==0){
					minSize = size;
					minQueueIndex = i;
				}else{
					if(size < minSize){
						minSize = size;
						minQueueIndex = i;
					}
				}
			}
			if(taskQueueList.size() <5 && minSize > 2){
				MicroTaskQueue queue = MicroTaskQueue.newInstance();
				taskQueueList.add(queue);
				queue.execute(item);
			}else{
				MicroTaskQueue minQueue = taskQueueList.get(minQueueIndex);
				minQueue.execute(item);
			}

		}

		for(int i=0;i<taskQueueList.size();i++){
			MicroTaskQueue queue = taskQueueList.get(i);
			int size = queue.getTaskItemListSize();
			L.I("线程队列["+i+"]的任务数："+size);
		}

	}

	/**
	 * 
	 * 释放资源.
	 */
	public void cancelAll(){
		for(int i=0;i<taskQueueList.size();i++){
			MicroTaskQueue queue = taskQueueList.get(i);
			queue.cancel(true);
		}
		taskQueueList.clear();
	}


	public MicroImageBaseCache getMemCache() {
		return memCache;
	}


	public void setMemCache(MicroImageBaseCache memCache) {
		this.memCache = memCache;
	}


	public MicroDiskBaseCache getDiskCache() {
		return diskCache;
	}


	public void setDiskCache(MicroDiskBaseCache diskCache) {
		this.diskCache = diskCache;
	}



	/*********************************************************************************************************************************************************/



	/**
	 * 描述：设置下载中的图片.
	 *
	 * @param resID the new loading image
	 */
	public void setLoadingImage(int resID) {
		this.loadingImage = context.getResources().getDrawable(resID);
	}

	/**
	 * 描述：设置下载中的View，优先级高于setLoadingImage.
	 *
	 * @param view 放在ImageView的上边或者下边的View
	 */
	public void setLoadingView(View view) {
		this.loadingView = view;
	}

	/**
	 * 描述：设置下载失败的图片.
	 *
	 * @param resID the new error image
	 */
	public void setErrorImage(int resID) {
		this.errorImage = context.getResources().getDrawable(resID);
	}

	/**
	 * 描述：设置未找到的图片.
	 *
	 * @param resID the new empty image
	 */
	public void setEmptyImage(int resID) {
		this.emptyImage = context.getResources().getDrawable(resID);
	}


	/*public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}*/

}


