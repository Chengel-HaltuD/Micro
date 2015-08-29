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
package com.widget.image;



import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * @ClassName: BaseImageView
 * @Description: 图片显示控件
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:38:34
 * @version V1.0
 *
 */
public class BaseImageView extends ImageView {

	public BaseImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setScaleType(ScaleType.FIT_XY);
	}

	public BaseImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setScaleType(ScaleType.FIT_XY);
	}

	public BaseImageView(Context context) {
		super(context);
		this.setScaleType(ScaleType.FIT_XY);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 设置图片�?在的地址
	 */
	/*public void SetImageUrl(String url){
		if(url!=null&&url.contains("http://")){

			ImageLoader.getInstance().displayImage(url, this,BaseApplication.getInstance().options);	
			

		}else{

			if(url.indexOf("/")==-1){
				//Drawable的资源文�?

			}else{			    	

				ImageLoader.getInstance().displayImage("file://"+url, this,BaseApplication.getInstance().options);	

			}

		}

	}*/
	
	/**
	 * 自带有显示效果的option图片下载�?
	 * @param url
	 * @param opt
	 */
	/*public void SetImageUrl(String url,DisplayImageOptions opt){
		if (opt==null) {

			//MyApplication.getApplication().imageLoader.displayImage(url, this,MyApplication.getApplication().options);
			SetImageUrl(url);

		}else {

			BaseApplication.getInstance().imageLoader.displayImage(url, this,opt);

			if(url!=null&&url.contains("http://")){

				ImageLoader.getInstance().displayImage(url,this,opt);	
			}else{

				if(url.indexOf("/")==-1){
					//Drawable的资源文�?

				}else{		

					ImageLoader.getInstance().displayImage("file://"+url,this,opt);	

				}

			}
		}
	}*/
}
