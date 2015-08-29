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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: MicroAdapter
 * @Description: 关于列表界面的适配器
 * @Author：Chengel_HaltuD
 * @Date：2015-8-29 下午2:22:01
 * @version V1.0
 *
 */
public abstract class MicroAdapter<T> extends BaseAdapter
{
	public List<T> list = new ArrayList<T>();
	public Context context;

	public MicroAdapter(Context context, List<T> list)
	{
		this.list = list;
		this.context = context;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position)
	{
		return this.list.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public abstract View getView(int paramInt, View paramView, ViewGroup paramViewGroup);
}
