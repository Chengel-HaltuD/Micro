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
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * 
 * @ClassName: RoundImageView
 * @Description: 自定义的圆角矩形ImageView，可以直接当组件在布局xml中使用
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:39:09
 * @version V1.0
 *
 */
public class RoundImageView extends BaseImageView{

	private Paint paint;

	public RoundImageView(Context context) {  
		this(context,null);  
	}  

	public RoundImageView(Context context, AttributeSet attrs) {  
		this(context, attrs,0);  
	}  

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {  
		super(context, attrs, defStyle); 
		paint  = new Paint();
	}  

	/**
	 * 绘制圆角矩形图片
	 */
	@Override  
	protected void onDraw(Canvas canvas) {  

		Drawable drawable = getDrawable();  
		if (null != drawable) {  
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();  
			Bitmap b = getRoundBitmap(bitmap, 20);  
			final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());  
			final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
			paint.reset();  
			canvas.drawBitmap(b, rectSrc, rectDest, paint);  

		} else {  
			super.onDraw(canvas);  
		}  
	}  

	/**
	 * 获取圆角矩形图片方法
	 * @param bitmap
	 * @param roundPx,�?般设置成14
	 * @return Bitmap
	 * @author GeLe
	 */
	public Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {  
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),  
				bitmap.getHeight(), Config.ARGB_8888);  
		Canvas canvas = new Canvas(output);  

		final int color = 0xff424242;

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);  
		canvas.drawARGB(0, 0, 0, 0);  
		paint.setColor(color);  
		int x = bitmap.getWidth(); 

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
		canvas.drawBitmap(bitmap, rect, rect, paint);  
		return output;  

	}  
}  