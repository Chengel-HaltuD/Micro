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
package com.demo;



import android.app.Activity;
import android.os.Bundle;

import com.micro.utils.T;
import com.widget.dialog.P;


/**
 * 
 * @ClassName: BaseActivity
 * @Description: 描述：继承这个BaseActivity使你的Activity拥有一个程序框架.
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:42:25
 * @version V1.0
 *
 */
public class BaseActivity extends Activity{
	
	public P PDailog;
	public T T;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		PDailog = new P(this, 0);
		T = new T(this);
	}
}
