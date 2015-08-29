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
package com.micro.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.AbListViewFooter;
import com.handmark.pulltorefresh.library.AbListViewHeader;

// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: V
 * @Description: V是AbViewUtil 描述：View工具类.常用单位转换的辅助类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:07:30
 * @version V1.0
 *
 */

public class V
{
	public static int UI_WIDTH = 720;

	public static int UI_HEIGHT = 1280;

	public static int UI_DENSITY = 2;
	public static final int INVALID = -2147483648;

	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	public static void setAbsListViewHeight(AbsListView absListView, int lineNumber, int verticalSpace)
	{
		int totalHeight = getAbsListViewHeight(absListView, lineNumber, 
				verticalSpace);
		ViewGroup.LayoutParams params = absListView.getLayoutParams();
		params.height = totalHeight;
		((ViewGroup.MarginLayoutParams)params).setMargins(0, 0, 0, 0);
		absListView.setLayoutParams(params);
	}

	public static int getAbsListViewHeight(AbsListView absListView, int lineNumber, int verticalSpace)
	{
		int totalHeight = 0;
		int w = View.MeasureSpec.makeMeasureSpec(0, 
				0);
		int h = View.MeasureSpec.makeMeasureSpec(0, 
				0);
		absListView.measure(w, h);
		ListAdapter mListAdapter = (ListAdapter)absListView.getAdapter();
		if (mListAdapter == null) {
			return totalHeight;
		}

		int count = mListAdapter.getCount();
		if ((absListView instanceof ListView)) {
			for (int i = 0; i < count; i++) {
				View listItem = mListAdapter.getView(i, null, absListView);
				listItem.measure(w, h);
				totalHeight += listItem.getMeasuredHeight();
			}
			if (count == 0) {
				totalHeight = verticalSpace;
			}
			else {
				totalHeight = totalHeight + 
						((ListView)absListView).getDividerHeight() * (count - 1);
			}
		}
		else if ((absListView instanceof GridView)) {
			int remain = count % lineNumber;
			if (remain > 0) {
				remain = 1;
			}
			if (mListAdapter.getCount() == 0) {
				totalHeight = verticalSpace;
			} else {
				View listItem = mListAdapter.getView(0, null, absListView);
				listItem.measure(w, h);
				int line = count / lineNumber + remain;
				totalHeight = line * listItem.getMeasuredHeight() + (line - 1) * 
						verticalSpace;
			}
		}

		return totalHeight;
	}

	public static void measureView(View view)
	{
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(-1, 
					-2);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0)
			childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 
					1073741824);
		else {
			childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 
					0);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	public static int getViewWidth(View view)
	{
		measureView(view);
		return view.getMeasuredWidth();
	}

	public static int getViewHeight(View view)
	{
		measureView(view);
		return view.getMeasuredHeight();
	}

	public static void removeSelfFromParent(View v)
	{
		ViewParent parent = v.getParent();
		if ((parent != null) && 
				((parent instanceof ViewGroup)))
			((ViewGroup)parent).removeView(v);
	}

	public static float dip2px(Context context, float dipValue)
	{
		DisplayMetrics mDisplayMetrics = A.getDisplayMetrics(context);
		return applyDimension(1, dipValue, mDisplayMetrics);
	}

	public static float px2dip(Context context, float pxValue)
	{
		DisplayMetrics mDisplayMetrics = A.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.density;
	}

	public static float sp2px(Context context, float spValue)
	{
		DisplayMetrics mDisplayMetrics = A.getDisplayMetrics(context);
		return applyDimension(2, spValue, mDisplayMetrics);
	}

	public static float px2sp(Context context, float pxValue)
	{
		DisplayMetrics mDisplayMetrics = A.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.scaledDensity;
	}

	public static int scaleValue(Context context, float value)
	{
		DisplayMetrics mDisplayMetrics = A.getDisplayMetrics(context);

		if (mDisplayMetrics.scaledDensity > UI_DENSITY)
		{
			if (mDisplayMetrics.widthPixels > UI_WIDTH)
				value *= (1.3F - 1.0F / mDisplayMetrics.scaledDensity);
			else if (mDisplayMetrics.widthPixels < UI_WIDTH) {
				value *= (1.0F - 1.0F / mDisplayMetrics.scaledDensity);
			}
		}
		return scale(mDisplayMetrics.widthPixels, 
				mDisplayMetrics.heightPixels, value);
	}

	public static int scaleTextValue(Context context, float value)
	{
		DisplayMetrics mDisplayMetrics = A.getDisplayMetrics(context);

		return scale(mDisplayMetrics.widthPixels, 
				mDisplayMetrics.heightPixels, value);
	}

	public static int scale(int displayWidth, int displayHeight, float pxValue)
	{
		if (pxValue == 0.0F) {
			return 0;
		}
		float scale = 1.0F;
		try {
			float scaleWidth = displayWidth / UI_WIDTH;
			float scaleHeight = displayHeight / UI_HEIGHT;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception localException) {
		}
		return Math.round(pxValue * scale + 0.5F);
	}

	public static float applyDimension(int unit, float value, DisplayMetrics metrics)
	{
		switch (unit) {
		case 0:
			return value;
		case 1:
			return value * metrics.density;
		case 2:
			return value * metrics.scaledDensity;
		case 3:
			return value * metrics.xdpi * 0.01388889F;
		case 4:
			return value * metrics.xdpi;
		case 5:
			return value * metrics.xdpi * 0.03937008F;
		}
		return 0.0F;
	}

	public static void scaleContentView(ViewGroup contentView)
	{
		scaleView(contentView);
		if (contentView.getChildCount() > 0)
			for (int i = 0; i < contentView.getChildCount(); i++) {
				View view = contentView.getChildAt(i);
				if ((view instanceof ViewGroup)) {
					if (isNeedScale(view))
						scaleContentView((ViewGroup)view);
				}
				else
					scaleView(contentView.getChildAt(i));
			}
	}

	public static void scaleContentView(View parent, int id)
	{
		ViewGroup contentView = null;
		View view = parent.findViewById(id);
		if ((view instanceof ViewGroup)) {
			contentView = (ViewGroup)view;
			scaleContentView(contentView);
		}
	}

	public static void scaleContentView(Context context, int id)
	{
		ViewGroup contentView = null;
		View view = ((Activity)context).findViewById(id);
		if ((view instanceof ViewGroup)) {
			contentView = (ViewGroup)view;
			scaleContentView(contentView);
		}
	}

	@SuppressLint({"NewApi"})
	public static void scaleView(View view)
	{
		if (!isNeedScale(view)) {
			return;
		}
		if ((view instanceof TextView)) {
			TextView textView = (TextView)view;
			setTextSize(textView, textView.getTextSize());
		}

		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (params != null) {
			int width = -2147483648;
			int height = -2147483648;
			if ((params.width != -2) && 
					(params.width != -1)) {
				width = params.width;
			}

			if ((params.height != -2) && 
					(params.height != -1)) {
				height = params.height;
			}

			setViewSize(view, width, height);

			setPadding(view, view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
		}

		if ((view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
			ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams)view
					.getLayoutParams();
			if (mMarginLayoutParams != null) {
				setMargin(view, mMarginLayoutParams.leftMargin, mMarginLayoutParams.topMargin, mMarginLayoutParams.rightMargin, mMarginLayoutParams.bottomMargin);
			}
		}

		if (Build.VERSION.SDK_INT >= 16)
		{
			int minWidth = scaleValue(view.getContext(), view.getMinimumWidth());
			int minHeight = scaleValue(view.getContext(), view.getMinimumHeight());
			view.setMinimumWidth(minWidth);
			view.setMinimumHeight(minHeight);
		}
	}

	public static boolean isNeedScale(View view)
	{
		if ((view instanceof AbListViewHeader)) {
			return false;
		}

		if ((view instanceof AbListViewFooter)) {
			return false;
		}
		return true;
	}

	public static void setSPTextSize(TextView textView, float size)
	{
		float scaledSize = scaleTextValue(textView.getContext(), size);
		textView.setTextSize(scaledSize);
	}

	public static void setTextSize(TextView textView, float sizePixels)
	{
		float scaledSize = scaleTextValue(textView.getContext(), sizePixels);
		textView.setTextSize(0, scaledSize);
	}

	public static void setTextSize(Context context, TextPaint textPaint, float sizePixels)
	{
		float scaledSize = scaleTextValue(context, sizePixels);
		textPaint.setTextSize(scaledSize);
	}

	public static void setTextSize(Context context, Paint paint, float sizePixels)
	{
		float scaledSize = scaleTextValue(context, sizePixels);
		paint.setTextSize(scaledSize);
	}

	public static void setViewSize(View view, int widthPixels, int heightPixels)
	{
		int scaledWidth = scaleValue(view.getContext(), widthPixels);
		int scaledHeight = scaleValue(view.getContext(), heightPixels);
		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (params == null) {
			L.E("setViewSize出错,如果是代码new出来的View，需要设置一个适合的LayoutParams");
			return;
		}
		if (widthPixels != -2147483648) {
			params.width = scaledWidth;
		}
		if (heightPixels != -2147483648) {
			params.height = scaledHeight;
		}
		view.setLayoutParams(params);
	}

	public static void setPadding(View view, int left, int top, int right, int bottom)
	{
		int scaledLeft = scaleValue(view.getContext(), left);
		int scaledTop = scaleValue(view.getContext(), top);
		int scaledRight = scaleValue(view.getContext(), right);
		int scaledBottom = scaleValue(view.getContext(), bottom);
		view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
	}

	public static void setMargin(View view, int left, int top, int right, int bottom)
	{
		int scaledLeft = scaleValue(view.getContext(), left);
		int scaledTop = scaleValue(view.getContext(), top);
		int scaledRight = scaleValue(view.getContext(), right);
		int scaledBottom = scaleValue(view.getContext(), bottom);

		if ((view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
			ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams)view
					.getLayoutParams();
			if (mMarginLayoutParams != null) {
				if (left != -2147483648) {
					mMarginLayoutParams.leftMargin = scaledLeft;
				}
				if (right != -2147483648) {
					mMarginLayoutParams.rightMargin = scaledRight;
				}
				if (top != -2147483648) {
					mMarginLayoutParams.topMargin = scaledTop;
				}
				if (bottom != -2147483648) {
					mMarginLayoutParams.bottomMargin = scaledBottom;
				}
				view.setLayoutParams(mMarginLayoutParams);
			}
		}
	}
}
