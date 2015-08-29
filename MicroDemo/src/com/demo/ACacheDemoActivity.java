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

import android.os.Bundle;

import com.micro.cache.image.MicroImageLoader;
import com.micro.view.ViewUtils;
import com.micro.view.annotation.ContentView;
import com.utils.F;
import com.widget.image.CircleImageView;
import com.widget.image.RoundImageView;

/**
 * @ClassName: CacheDemoActivity
 * @Description: TODO
 * @Author：GeLe
 * @Date：2015-5-27 下午7:27:42
 * @version V1.0
 *
 */
@ContentView(R.layout.activity_layout_cache_demo)
public class ACacheDemoActivity extends BaseActivity{
	
	private String URL = "http://i7.baidu.com/it/u=3682252563,3944035453&fm=96&s=2BEC7A2248B13FA125B6C15E010080A0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		
		F F = new F(this);
		
		//鍥剧墖鐨勪笅杞�
		MicroImageLoader   mAbImageLoader = MicroImageLoader.getInstance(this);
		mAbImageLoader.setLoadingImage(R.drawable.loadingimage);
		mAbImageLoader.setErrorImage(R.drawable.errorimage);
		mAbImageLoader.setEmptyImage(R.drawable.emptyimage);

		RoundImageView iv_round = F.F(R.id.iv_round);
		//iv_round.SetImageUrl(URL);
		mAbImageLoader.display(iv_round, URL);


		CircleImageView iv_circle = F.F(R.id.iv_circle);
		//iv_circle.SetImageUrl(URL);
		mAbImageLoader.display(iv_circle, URL);
	}
}
