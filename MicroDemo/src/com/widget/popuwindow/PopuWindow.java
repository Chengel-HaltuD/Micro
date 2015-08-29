/*
 * Copyright (C) 2013 www.jryghq.com
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
package com.widget.popuwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * @ClassName: PopuWindow
 * @Description: TODO
 * @Author：GeLe
 * @Date：2015-6-8 上午10:44:28
 * @version V1.0
 *
 */
public class PopuWindow {


	private PopupWindow popupWindow;
	/**
	 * 
	 * @Title: showPopW 
	 * @Description: TODO 
	 * @param context 上下文
	 * @param parent 根据次布局来定位PopuWindow 
	 * @param resource 弹出PopuWindow 样式布局
	 * @param animationStyle 探出时的动画效果
	 * @return void
	 *
	 */
	public void showPopW(Context context,View parent,int resource,int animationStyle) {

		//1. LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater() 
		//2. LayoutInflater inflater = LayoutInflater.from(context);  
		//3. LayoutInflater inflater =  (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LayoutInflater inflater = LayoutInflater.from(context);

		LinearLayout contentView = (LinearLayout) inflater.inflate(resource, null);

		popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x90000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.setContentView(contentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		popupWindow.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		popupWindow.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		popupWindow.setAnimationStyle(animationStyle);
		popupWindow.setOutsideTouchable(true);
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		//int ypos = manager.getDefaultDisplay().getHeight()/2;
		popupWindow.showAsDropDown(parent, 0, 0);
		// popupWindow.showAtLocation(parent, Gravity.TOP, 20, ypos);

		popupWindow.update();

		/*ListView lv_list_pay = (ListView) contentView.findViewById(R.id.lv_list_pay);
		TextView bt_cancle_view = (TextView) contentView.findViewById(R.id.bt_cancle_view);



		bt_cancle_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});*/

	}
}
