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
package com.utils;


/*
 * Created by Storm Zhang 2014-8-24.
 */

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Helper for finding and tweaking a view's children
 * 
 */
public class ViewFinders {


	/***************************************************************************************************************************/
	
	/**
	 * ����Window��װfinder
	 */
	private static class WindowWrapper implements FindWrapper {

		private final Window window;

		WindowWrapper(final Window window) {
			this.window = window;
		}

		public View findViewById(final int id) {
			return window.findViewById(id);
		}

		public Resources getResources() {
			return window.getContext().getResources();
		}
	}

	/**
	 * ����View��װfinder
	 */
	private static class ViewWrapper implements FindWrapper {

		private final View view;

		ViewWrapper(final View view) {
			this.view = view;
		}

		public View findViewById(final int id) {
			return view.findViewById(id);
		}

		public Resources getResources() {
			return view.getResources();
		}
	}


	/***************************************************************************************************************************/
	

	/**
	 * Create finder CallBack
	 * �ص�����
	 */
	private final FindWrapper wrapper;
	private static interface FindWrapper {

		View findViewById(int id);

		Resources getResources();
	}

	/**
	 * Create finder wrapping given view
	 * ����View��װfinder
	 *
	 * @param view
	 */
	public ViewFinders(final View view) {
		wrapper = new ViewWrapper(view);
	}

	/**
	 * Create finder wrapping given window
	 * ����Window��װfinder
	 *
	 * @param window
	 */
	public ViewFinders(final Window window) {
		wrapper = new WindowWrapper(window);
	}

	/**
	 * Create finder wrapping given activity
	 * ����Activity��װfinder
	 *
	 * @param activity
	 */
	public ViewFinders(final Activity activity) {
		this(activity.getWindow());//Ҳ�ǻ���Window��װ
	}


	/***************************************************************************************************************************/


	/**
	 * Find view with id
	 *
	 * @param id
	 * @return found view
	 */
	@SuppressWarnings("unchecked")
	public <V extends View> V find(final int id) {
		return (V) wrapper.findViewById(id);
	}

	/**
	 * Get image view with id
	 *
	 * @param id
	 * @return image view
	 */
	public ImageView imageView(final int id) {
		return find(id);
	}

	/**
	 * Get compound button with id
	 *
	 * @param id
	 * @return image view
	 */
	public CompoundButton compoundButton(final int id) {
		return find(id);
	}

	/**
	 * Get text view with id
	 *
	 * @param id
	 * @return text view
	 */
	public TextView textView(final int id) {
		return find(id);
	}


	/***************************************************************************************************************************/


	/**
	 * Set text of child view with given id
	 * ���ݸ����ַ��趨TextView ����
	 * 
	 * @param id
	 * @param content
	 * @return text view
	 */
	public TextView setText(final int id, final CharSequence content) {
		final TextView text = find(id);
		text.setText(content);
		return text;
	}

	/**
	 * Set text of child view with given id
	 * ���ݸ����ַ�ID�趨TextView ����
	 *
	 * @param id
	 * @param content
	 * @return text view
	 */
	public TextView setText(final int id, final int content) {
		return setText(id, wrapper.getResources().getString(content));
	}

	/**
	 * Set drawable on child image view
	 * ���ݸ�����ԴID�趨ImageView drawable
	 *
	 * @param id
	 * @param drawable
	 * @return image view
	 */
	public ImageView setDrawable(final int id, final int drawable) {
		ImageView image = imageView(id);
		image.setImageDrawable(image.getResources().getDrawable(drawable));
		return image;
	}

	/**
	 * Set drawable on child image view with given id
	 * ���ݸ���drawable�趨ImageView drawable
	 *
	 * @param id
	 * @param content
	 * @return text view
	 */
	public ImageView setDrawable(final int id, final Drawable drawable) {
		ImageView image = imageView(id);
		image.setImageDrawable(drawable);
		return image;
	}
	

	/***************************************************************************************************************************/


	/**
	 * Register on click listener to child view with given id
	 * ע��������ID������ͼ���
	 *
	 * @param id
	 * @param listener
	 * @return view registered with listener
	 */
	public View onClick(final int id, final OnClickListener listener) {
		View clickable = find(id);
		clickable.setOnClickListener(listener);
		return clickable;
	}

	/**
	 * Register runnable to be invoked when child view with given id is clicked
	 * ע��������ID������ͼ���(����ʱ)
	 *
	 * @param id
	 * @param runnable
	 * @return view registered with runnable
	 */
	public View onClick(final int id, final Runnable runnable) {
		return onClick(id, new OnClickListener() {

			public void onClick(View v) {
				runnable.run();
			}
		});
	}

	/**
	 * Register on click listener with all given child view ids
	 * ע��������ID������ͼ���(����)
	 *
	 * @param ids
	 * @param listener
	 */
	public void onClick(final OnClickListener listener, final int... ids) {
		for (int id : ids)
			find(id).setOnClickListener(listener);
	}

	/**
	 * Register runnable to be invoked when all given child view ids are clicked
	 * ע��������ID������ͼ���(��������ʱ)
	 *
	 * @param ids
	 * @param runnable
	 */
	public void onClick(final Runnable runnable, final int... ids) {
		onClick(new OnClickListener() {

			public void onClick(View v) {
				runnable.run();
			}
		}, ids);
	}


	/***************************************************************************************************************************/


	/**
	 * Register on checked change listener to child view with given id
	 * ע�����������ID������ͼ������鷢���ı������
	 *
	 * @param id
	 * @param listener
	 * @return view registered with listener
	 */
	public CompoundButton onCheck(final int id, final OnCheckedChangeListener listener) {
		CompoundButton checkable = find(id);
		checkable.setOnCheckedChangeListener(listener);
		return checkable;
	}

	/**
	 * Register runnable to be invoked when child view with given id is
	 * checked/unchecked
	 * ע�����������ID������ͼ������鷢���ı������(����ʱ)
	 *
	 * @param id
	 * @param runnable
	 * @return view registered with runnable
	 */
	public CompoundButton onCheck(final int id, final Runnable runnable) {
		return onCheck(id, new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				runnable.run();
			}
		});
	}

	/**
	 * Register on checked change listener with all given child view ids
	 * ע�����������ID������ͼ������鷢���ı������(����)
	 *
	 * @param ids
	 * @param listener
	 */
	public void onCheck(final OnCheckedChangeListener listener, final int... ids) {
		for (int id : ids)
			compoundButton(id).setOnCheckedChangeListener(listener);
	}

	/**
	 * Register runnable to be invoked when all given child view ids are
	 * checked/unchecked
	 * ע�����������ID������ͼ������鷢���ı������(��������ʱ)
	 *
	 * @param ids
	 * @param runnable
	 */
	public void onCheck(final Runnable runnable, final int... ids) {
		onCheck(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				runnable.run();
			}
		}, ids);
	}
}