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
package com.micro.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 
 * @ClassName: MicroListView
 * @Description: TODO
 * @Author：Chengel_HaltuD
 * @Date：2015-8-29 下午2:22:41
 * @version V1.0
 *
 */
public class MicroListView extends ListView
{
	float lastX;
	float lastY;

	public MicroListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public MicroListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MicroListView(Context context)
	{
		super(context);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = View.MeasureSpec.makeMeasureSpec(
				536870911, -2147483648);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		boolean result = false;

		switch (ev.getAction())
		{
		case 0:
			this.lastX = ev.getX();
			this.lastY = ev.getY();
			break;
		case 2:
			int distanceX = (int)Math.abs(ev.getX() - this.lastX);
			int distanceY = (int)Math.abs(ev.getY() - this.lastY);

			if ((distanceX > distanceY) && (distanceX > 10))
				result = true;
			else {
				result = false;
			}
			break;
		case 1:
		}

		return result;
	}
}
