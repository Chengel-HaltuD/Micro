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
package com.widget.toast;



import com.demo.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @ClassName: CToast
 * @Description: 自定义时长的Toast
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:41:04
 * @version V1.0
 *
 */
public class CToast {

	public static CToast makeText(Context context, CharSequence text, int duration) 
	{
		CToast result = new CToast(context);

		LinearLayout mLayout=new LinearLayout(context);
		TextView tv = new TextView(context);
		tv.setText(text);
		tv.setTextColor(Color.WHITE);
		tv.setGravity(Gravity.CENTER);
		mLayout.setBackgroundResource(R.drawable.ic_launcher);

		int w=context.getResources().getDisplayMetrics().widthPixels / 2;
		int h=context.getResources().getDisplayMetrics().widthPixels / 10;
		mLayout.addView(tv, w, h);
		result.mNextView = mLayout;
		result.mDuration = duration;

		return result;
	}

	public static final int LENGTH_SHORT = 2000;
	public static final int LENGTH_LONG = 3500;

	private final Handler mHandler = new Handler();    
	private int mDuration=LENGTH_SHORT;
	private int mGravity = Gravity.CENTER;
	private int mX, mY;
	private float mHorizontalMargin;
	private float mVerticalMargin;
	private View mView;
	private View mNextView;

	private WindowManager mWM;
	private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();


	public CToast(Context context) {
		init(context);
	}

	/**
	 * Set the view to show.
	 * @see #getView
	 */
	 public void setView(View view) {
		mNextView = view;
	}

	/**
	 * Return the view.
	 * @see #setView
	 */
	 public View getView() {
		 return mNextView;
	 }

	 /**
	  * Set how long to show the view for.
	  * @see #LENGTH_SHORT
	  * @see #LENGTH_LONG
	  */
	 public void setDuration(int duration) {
		 mDuration = duration;
	 }

	 /**
	  * Return the duration.
	  * @see #setDuration
	  */
	 public int getDuration() {
		 return mDuration;
	 }

	 /**
	  * Set the margins of the view.
	  *
	  * @param horizontalMargin The horizontal margin, in percentage of the
	  *        container width, between the container's edges and the
	  *        notification
	  * @param verticalMargin The vertical margin, in percentage of the
	  *        container height, between the container's edges and the
	  *        notification
	  */
	 public void setMargin(float horizontalMargin, float verticalMargin) {
		 mHorizontalMargin = horizontalMargin;
		 mVerticalMargin = verticalMargin;
	 }

	 /**
	  * Return the horizontal margin.
	  */
	 public float getHorizontalMargin() {
		 return mHorizontalMargin;
	 }

	 /**
	  * Return the vertical margin.
	  */
	 public float getVerticalMargin() {
		 return mVerticalMargin;
	 }

	 /**
	  * Set the location at which the notification should appear on the screen.
	  * @see android.view.Gravity
	  * @see #getGravity
	  */
	 public void setGravity(int gravity, int xOffset, int yOffset) {
		 mGravity = gravity;
		 mX = xOffset;
		 mY = yOffset;
	 }

	 /**
	  * Get the location at which the notification should appear on the screen.
	  * @see android.view.Gravity
	  * @see #getGravity
	  */
	 public int getGravity() {
		 return mGravity;
	 }

	 /**
	  * Return the X offset in pixels to apply to the gravity's location.
	  */
	 public int getXOffset() {
		 return mX;
	 }

	 /**
	  * Return the Y offset in pixels to apply to the gravity's location.
	  */
	 public int getYOffset() {
		 return mY;
	 }

	 /**
	  * schedule handleShow into the right thread
	  */
	 public void show() {
		 mHandler.post(mShow);

		 if(mDuration>0)
		 {
			 mHandler.postDelayed(mHide, mDuration);
		 }
	 }

	 /**
	  * schedule handleHide into the right thread
	  */
	 public void hide() {
		 mHandler.post(mHide);
	 }

	 private final Runnable mShow = new Runnable() {
		 public void run() {
			 handleShow();
		 }
	 };

	 private final Runnable mHide = new Runnable() {
		 public void run() {
			 handleHide();
		 }
	 };

	 private void init(Context context)
	 {	
		 final WindowManager.LayoutParams params = mParams;
		 params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		 params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		 params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				 | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				 | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		 params.format = PixelFormat.TRANSLUCENT;
		 params.windowAnimations = android.R.style.Animation_Toast;
		 params.type = WindowManager.LayoutParams.TYPE_TOAST;
		 params.setTitle("Toast");

		 mWM = (WindowManager) context.getApplicationContext()
				 .getSystemService(Context.WINDOW_SERVICE);
	 }


	 private void handleShow() {

		 if (mView != mNextView) {
			 // remove the old view if necessary
			 handleHide();
			 mView = mNextView;
			 //            mWM = WindowManagerImpl.getDefault();
			 final int gravity = mGravity;
			 mParams.gravity = gravity;
			 if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) 
			 {
				 mParams.horizontalWeight = 1.0f;
			 }
			 if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) 
			 {
				 mParams.verticalWeight = 1.0f;
			 }
			 mParams.x = mX;
			 mParams.y = mY;
			 mParams.verticalMargin = mVerticalMargin;
			 mParams.horizontalMargin = mHorizontalMargin;
			 if (mView.getParent() != null) 
			 {
				 mWM.removeView(mView);
			 }
			 mWM.addView(mView, mParams);
		 }
	 }

	 private void handleHide() 
	 {
		 if (mView != null) 
		 {
			 if (mView.getParent() != null) 
			 {
				 mWM.removeView(mView);
			 }
			 mView = null;
		 }
	 }
}
