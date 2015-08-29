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
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.micro.utils.L;
import com.micro.view.ViewUtils;
import com.micro.view.annotation.ContentView;
import com.micro.view.annotation.ViewInject;
import com.micro.view.annotation.event.OnClick;
import com.utils.F;
import com.widget.custombutton.CustomButton;
import com.widget.custombutton.CustomButton.OnChangedListener;
import com.widget.dialog.AlertDialog;
import com.widget.edittext.ClearEditText;
import com.widget.edittext.ClearEditTextAutoComplete;
import com.widget.toast.CToast;


/**
 * @ClassName: CustomDemoActivity
 * @Description: TODO
 * @Author：GeLe
 * @Date：2015-5-27 下午8:29:14
 * @version V1.0
 *
 */
@ContentView(R.layout.activity_layout_custom_demo)
public class ACustomDemoActivity extends BaseActivity implements OnClickListener{
	Handler handler=new Handler();
	/*Handler handler=new Handler(){ 
        @Override 
        public void handleMessage(Message msg) { 
            int what=msg.what; 
            switch (what) { 
            case 1: 
                T.Thread("Toast鍦ㄥ叾浠栫嚎绋嬩腑璋冪敤鏄剧ず");
                break; 
            default: 
                break; 
            } 

            super.handleMessage(msg); 
        } 
    };*/
	
	CToast mCToast;
	
	@ViewInject(R.id.wandoujia)
	private Button wandoujia;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		F F = new F(this);

		ClearEditText clearEditText = F.F(R.id.clearedittext);

		ClearEditTextAutoComplete autocomplete = F.F(R.id.cleareautocompletedittext);

		CustomButton customButton = F.F(R.id.custombutton);
		customButton.setState(true);
		customButton.setOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(boolean CheckState) {
				// TODO Auto-generated method stub
				if (CheckState) {

					T.I("寮�==鍐呭祵鍥剧墖鏁堟灉Toast", 500, 500);
					L.I("寮�");
					PDailog.OperationIng("姝ｅ湪鍔犺浇");
					final AlertDialog ad=new AlertDialog(ACustomDemoActivity.this);
					ad.setTitle("鍝堝搱");
					ad.setMessage("宸插皢鎮ㄧ殑淇℃伅鎺ㄩ�佺粰瀹㈡埛绛夊緟瀹㈡埛纭鏀粯");
					ad.setPositiveButton("纭", new OnClickListener() {
						@Override
						public void onClick(View v) {
							ad.dismiss();
							L.I("我是shui".length()+"长度");

						}
					});
				}else {

					T.O("鍏�==澶栧甫鍥剧墖娑堟伅Toast", 200, 200);
					T.V( "鍛靛懙",R.layout.toast_xml);
					L.I("鍏�");
					PDailog.OperationSuccess("加载成功");
					handler.post(new Runnable() { 
						@Override 
						public void run() { 
							T.Thread("Toast鍦ㄥ叾浠栫嚎绋嬩腑璋冪敤鏄剧ず"); 
						} 
					});

					/*Intent intent = new Intent(MainActivity.this,TestToastActivity.class);
					startActivity(intent);*/
				}
			}
		});

		F.onClick(R.id.showtoast, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(null!=mCToast)
				{
					mCToast.hide();
				}
				int time=10000;
				mCToast=CToast.makeText(getApplicationContext(), "鎴戞潵鑷狢Toast!",time);
				mCToast.show();
			}
		});
		F.onClick(R.id.closetoast, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(null!=mCToast)
				{
					mCToast.hide();
				}
			}
		});
		
		F.onClick(R.id.xlistview,new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ACustomDemoActivity.this,AListViewActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@OnClick(R.id.wandoujia)
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.wandoujia:
			
			Intent intent = new Intent(ACustomDemoActivity.this,AWanDouJiaActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
