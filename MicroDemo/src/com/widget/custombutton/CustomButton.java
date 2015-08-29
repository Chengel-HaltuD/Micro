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
package com.widget.custombutton;




import com.demo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
 * @ClassName: CustomButton
 * @Description: 开关控件
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:37:09
 * @version V1.0
 *
 */
public class CustomButton extends View implements OnTouchListener {

	private Bitmap bg_on, bg_off, slip_btn,left,right,buttonbg;
	private Rect canBeSee;//可视区。
	private float timelyX;// 实时X坐标
	private boolean isSlipping = false;// 按钮是否在滑动
	private boolean currentState = false;// 当前开关状态
	private boolean isSetChangedListener = false;
	private OnChangedListener onChangedListener;
	private boolean isSetState;

	public CustomButton(Context context) {
		super(context);
		init();

	}

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();

	}

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	private void init() {
		bg_on = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_left_1);
		bg_off = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_right_1);
		slip_btn = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_1);
		left=BitmapFactory.decodeResource(getResources(),R.drawable.left);
		right=BitmapFactory.decodeResource(getResources(),R.drawable.right);

		canBeSee=new Rect(0,0,bg_off.getWidth(),bg_off.getHeight());
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.clipRect(canBeSee);
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//实际绘图坐标
		float x;
		float y=0;
		if(isSlipping){//正在滑动
			if (timelyX >= bg_on.getWidth())// 是否划出指定范围,不能让游标跑到外头,必须做这个判断

				x = bg_on.getWidth() - slip_btn.getWidth() / 2;// 减去游标1/2的长度...

			else if (timelyX < 0) {
				x = 0;
			} else {
				x = timelyX - slip_btn.getWidth() / 2;
			}
		}else{//停止滑动
			if(currentState){
				x=bg_off.getWidth()-slip_btn.getWidth();
			}else{
				x=0;
			}
		}
		if(isSetState){//是否设置状态
			x=bg_on.getWidth()-slip_btn.getWidth();
			isSetState=!isSetState;
		}
		if(x < 0){//touch指针移出左边界
			x = 0;
		}else if(x > bg_on.getWidth() - slip_btn.getWidth()){
			x = bg_on.getWidth() - slip_btn.getWidth();
		}

		canvas.drawBitmap(bg_on, x - bg_on.getWidth() + slip_btn.getWidth(), 0,
				paint);
		canvas.drawBitmap(bg_off, x, y, paint);

		canvas.drawBitmap(left,canBeSee.left,y, paint);
		canvas.drawBitmap(right, canBeSee.right - slip_btn.getWidth() / 2-3,
				0, paint);

		canvas.drawBitmap(slip_btn, x, y, paint);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			timelyX = event.getX();
			break;

		case MotionEvent.ACTION_DOWN:
			if (event.getX() > bg_on.getWidth()
					|| event.getY() > bg_on.getHeight())
				return false;
			isSlipping = true;
			timelyX = event.getX();
			break;

		case MotionEvent.ACTION_UP:
			isSlipping = false;
			boolean preStateUp = currentState;
			if (event.getX() >= (bg_on.getWidth() / 2)) {
				timelyX = bg_on.getWidth() - slip_btn.getWidth() / 2;
				currentState = true;
			} else {
				timelyX = slip_btn.getWidth() / 2;
				currentState = false;
			}
			if (isSetChangedListener && (preStateUp != currentState)) {
				onChangedListener.OnChanged(currentState);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			isSlipping = false;
			boolean preStateCancel = currentState;
			if (timelyX >= (bg_on.getWidth() / 2)) {
				timelyX = bg_on.getWidth() - slip_btn.getWidth() / 2;
				currentState = true;
			} else {
				timelyX = slip_btn.getWidth() / 2;
				currentState = false;
			}

			if (isSetChangedListener && (preStateCancel != currentState)) {
				onChangedListener.OnChanged(currentState);
			}
			break;
		}
		invalidate();
		return true;
	}

	public interface OnChangedListener {
		abstract void OnChanged(boolean CheckState);
	}

	public void setOnChangedListener(OnChangedListener listener) {
		isSetChangedListener = true;
		onChangedListener = listener;
	}

	public void setState(boolean isSetState) {
		this.isSetState = isSetState;
		currentState = isSetState;
	}

}
