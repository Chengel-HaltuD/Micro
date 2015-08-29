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
package com.demo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.TextView;

import com.micro.view.ViewUtils;
import com.micro.view.annotation.ContentView;
import com.micro.view.annotation.ViewInject;
import com.micro.view.annotation.event.OnClick;

/**
 * 
 * @ClassName: MainActivity
 * @Description: MainActivity
 * @Author锛欸eLe
 * @Date锛�2015-5-21 涓嬪崍12:26:33
 * @version V1.0
 *
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnClickListener{

	@ViewInject(R.id.tv_Cache)
	private TextView tv_Cache;
	@ViewInject(R.id.tv_Db)
	private TextView tv_Db;
	@ViewInject(R.id.tv_Http)
	private TextView tv_Http;
	@ViewInject(R.id.tv_View)
	private TextView tv_View;
	@ViewInject(R.id.tv_Custom)
	private TextView tv_Custom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);

	}

	@OnClick({R.id.tv_Cache,R.id.tv_Db,R.id.tv_Http,R.id.tv_View,R.id.tv_Custom})
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (arg0.getId()) {
		case R.id.tv_Cache:
			intent = new Intent(MainActivity.this,ACacheDemoActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_Db:
			
			break;
		case R.id.tv_Http:
			intent = new Intent(MainActivity.this,AHttpDemoActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_View:
			
			break;
		case R.id.tv_Custom:
			intent = new Intent(MainActivity.this,ACustomDemoActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
