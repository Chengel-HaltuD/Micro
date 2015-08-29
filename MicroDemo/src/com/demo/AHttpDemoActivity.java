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

import java.util.List;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.micro.cache.image.MicroImageLoader;
import com.micro.http.MicroRequestParams;
import com.micro.utils.L;
import com.micro.utils.S;
import com.micro.view.ViewUtils;
import com.micro.view.annotation.ContentView;
import com.micro.view.annotation.ViewInject;
import com.widget.image.BaseImageView;

/**
 * @ClassName: TestActivity
 * @Description: TODO
 * @Author：GeLe
 * @Date：2015-5-21 下午7:03:56
 * @version V1.0
 *
 */
@ContentView(R.layout.activity_layout_http_demo)
public class AHttpDemoActivity extends BaseActivity{

	/** 客户端二维码 **/
	@ViewInject(R.id.qrcodefragment_iv_img_qrcodeclient)
	private BaseImageView qrcodefragment_iv_img_qrcodeclient;
	/** 客户端扫描次数 **/
	@ViewInject(R.id.qrcodefragment_tv_qrcode_numclient)
	private TextView qrcodefragment_tv_qrcode_numclient;
	/** 服务端二维码 **/
	@ViewInject(R.id.qrcodefragment_iv_img_qrcodeservice)
	private BaseImageView qrcodefragment_iv_img_qrcodeservice;
	/** 服务端扫描次数 **/
	@ViewInject(R.id.qrcodefragment_tv_qrcode_numservice)
	private TextView qrcodefragment_tv_qrcode_numservice;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);

		initDataClient();

		initDataService();
	}

	private void initDataClient() {
		// TODO Auto-generated method stub
		MicroRequestParams params = new MicroRequestParams();
		params.put("UserId", "jryg741");

		TypeToken<List<DimensionModel>> typeToken = new TypeToken<List<DimensionModel>>(){};
		FinalFetch<DimensionModel> fetch = new FinalFetch<DimensionModel>(new IFetch<DimensionModel>() {

			@Override
			public void onSuccess(List<DimensionModel> datas) {
				// TODO Auto-generated method stub
				if (datas.size() > 0) {
					DimensionModel model = datas.get(0);

					if (model.Result != null&& model.Result.equals("1")) {
						// 执行取手机验证码的请求
						MicroImageLoader   mAbImageLoader = MicroImageLoader.getInstance(AHttpDemoActivity.this);
						mAbImageLoader.display(qrcodefragment_iv_img_qrcodeclient, model.QRCode);

						L.I("客户端二维码"+model.QRCode);
					} else {
						T.D("请求失败");
					}

				} else {
					T.D("请求失败");
				}
			}

			@Override
			public void onFail(ResultModel result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFetching(int proccess) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPrevious() {
				// TODO Auto-generated method stub

			}
		}, params,typeToken, "Other?");



		MicroRequestParams paramsCount = new MicroRequestParams();
		paramsCount.put("Uid", "jryg741");
		TypeToken<List<DimensionCountModel>> typeTokenCount = new TypeToken<List<DimensionCountModel>>(){};
		FinalFetch<DimensionCountModel> fetchCount = new FinalFetch<DimensionCountModel>(new IFetch<DimensionCountModel>() {

			@Override
			public void onSuccess(List<DimensionCountModel> datas) {
				// TODO Auto-generated method stub
				if (datas.size() > 0) {
					DimensionCountModel model = datas.get(0);

					if (model.Result != null&& model.Result.equals("1")) {

						S.put(AHttpDemoActivity.this, "Count", model.Count);
						String ss = (String) S.get(AHttpDemoActivity.this, "Count", "空");
						qrcodefragment_tv_qrcode_numclient.setText("你已分享"+ss+"次");

						T.D("客户端已分享"+ss+"次");

					} else {

						T.D("请求失败");
					}

				} else {

					T.D("请求失败");
				}
			}

			@Override
			public void onFail(ResultModel result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFetching(int proccess) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPrevious() {
				// TODO Auto-generated method stub

			}
		}, paramsCount,typeTokenCount, "Other?");
	}

	private void initDataService() {
		// TODO Auto-generated method stub

		MicroRequestParams params = new MicroRequestParams();

		params.put("Method", "ModQRCode");
		params.put("Type", "Guide.GuideInfoManage");
		params.put("Timestamp", "");
		params.put("Version", "1.1");
		params.put("Id", "3391");
		TypeToken<List<DimensionGuide>> typeToken = new TypeToken<List<DimensionGuide>>(){};
		FinalFetch<DimensionGuide> fetch = new FinalFetch<DimensionGuide>(new IFetch<DimensionGuide>() {

			@Override
			public void onSuccess(List<DimensionGuide> datas) {
				// TODO Auto-generated method stub
				if (datas.size() > 0) {
					DimensionGuide model = datas.get(0);

					if (model.getData().getResult() != null&& model.getData().getResult().equals("1")) {

						S.put(AHttpDemoActivity.this, "Count", model.getData().getCount());
						String ss = (String) S.get(AHttpDemoActivity.this, "Count", "空");
						qrcodefragment_tv_qrcode_numclient.setText("你已分享"+ss+"次");

						T.D("导游端已分享"+ss+"次");
						// 执行取手机验证码的请求
						MicroImageLoader   mAbImageLoader = MicroImageLoader.getInstance(AHttpDemoActivity.this);
						mAbImageLoader.display(qrcodefragment_iv_img_qrcodeservice, model.getData().getFilePath());

						L.I("服务端二维码"+model.getData().getFilePath());

					} else {

						T.D("请求失败");
					}

				} else {

					T.D("请求失败");
				}
			}

			@Override
			public void onFail(ResultModel result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFetching(int proccess) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPrevious() {
				// TODO Auto-generated method stub

			}
		}, params,typeToken, "",1);
	}

}
