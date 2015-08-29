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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * 
 * @ClassName: CircleImageView
 * @Description: 自定义的圆角ImageView，可以直接当组件在布局xml中使用
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:38:53
 * @version V1.0
 *
 */
public class CircleImageView extends BaseImageView {  

	/**
	 *  注意一定要是src，不能是background，在代码中设置的话 用img.setImageResource(R.drawable.headpic);
	 *  重写CircleImage，继承ImageView
	 *  Bitmap裁剪，使其变成圆形，这步最关键  
	 */
	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {   
		Bitmap sbmp;   
		if(bmp.getWidth() != radius || bmp.getHeight() != radius)   
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);   
		else   
			sbmp = bmp;   

		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);   
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());   

		Paint paint = new Paint();   
		paint.setAntiAlias(true);   
		paint.setFilterBitmap(true);   
		paint.setDither(true);         
		paint.setColor(Color.parseColor("#BAB399"));   

		Canvas c = new Canvas(output);           
		c.drawARGB(0, 0, 0, 0);   
		c.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f, sbmp.getWidth() / 2+0.1f, paint);   
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));   
		c.drawBitmap(sbmp, rect, rect, paint);   

		return output;   
	}   

	public CircleImageView(Context context) {   
		super(context);   
	}   

	public CircleImageView(Context context, AttributeSet attrs) {   
		super(context, attrs);  

	}   

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {   
		super(context, attrs, defStyle);
	}   
	@Override   
	protected void onDraw(Canvas canvas) {   
		Drawable drawable = getDrawable();   
		if (drawable == null) {   
			return;   
		}   

		if (getWidth() == 0 || getHeight() == 0) {   
			return;    
		}   
		Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;   
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);         

		Bitmap roundBitmap =  getCroppedBitmap(bitmap, getWidth());   
		canvas.drawBitmap(roundBitmap, 0, 0, null);   
		//drawCircleBorder(canvas, 180, Color.RED);
	}   

}
