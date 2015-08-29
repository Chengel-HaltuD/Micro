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
package com.widget.dialog;



import com.demo.R;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * 
 * @ClassName: AlertDialog
 * @Description: 自定义对话框，导游点击接单以后的对话框
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:37:35
 * @version V1.0
 *
 */
public class AlertDialog{
	
	Context context;
	android.app.AlertDialog dialog;
	/** 对话框标题  **/
	private TextView titleView;
	/** 对话框内容  **/
	private TextView messageView;
	/** 对话框确认按钮  **/
	private TextView confrim;
	public AlertDialog(Context context) {
		
		this.context=context;
		dialog =new android.app.AlertDialog.Builder(context).create();
		dialog.setCancelable(false);
		/*ProgressDialog mpDialog = new ProgressDialog(OrderTable.this);  
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条   
		mpDialog.setTitle("提示");//设置标题   
		mpDialog.setIcon(R.drawable.icon);//设置图标   
		mpDialog.setMessage("这是一个圆形进度条");   
		mpDialog.setIndeterminate(false);//设置进度条是否为不明确   */
		dialog.show();
		//关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alertdialog_layout_alert_didlog);
		titleView=(TextView)window.findViewById(R.id.title);
		messageView=(TextView)window.findViewById(R.id.message);
		confrim = (TextView) window.findViewById(R.id.confrim);
		
		
		
	}

	/**
	 * 设置标题和内容
	 */
	public void setTitle(String title) {
		titleView.setText(title);
	}
	public void setMessage(String message)
	{
		messageView.setText(message);
	}

	/**
	 * 设置按钮
	 */
	public void setPositiveButton(String text,final View.OnClickListener listener)
	{
		confrim.setText(text);
		confrim.setOnClickListener(listener);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		dialog.dismiss();
	}
}
