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
package com.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;



/**
 * 
 * @ClassName: LocalUserModel
 * @Description: 本地数据存储类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:43:25
 * @version V1.0
 *
 */
public class LocalUserModel  {

	public static String DEFAULT_VALUE = "DEFAULT_VALUE";

	public static String LONGIN_STATE_ONLINE = "ONLINE";

	public static String LOGIN_STATE_OFFLINE = "OFFLINE";

	public static String LOGIN_STATE_UNKNOW = "UNKNOW";
	
	public final String SP_NAME = "config";
	
	private Context mContext;
	/**
	 * 用户的登陆状态： ONLINE: 用户当前处于登陆督状态 OFFLINE: 用户当前处于下线状态 UNKNOW:
	 * 用户当前处于默认状态(或者是从来没有登陆过此应用)
	 */
	private String Login_State = "";
	private String LoginCode = "";
	private SharedPreferences sp;
	
	public LocalUserModel() {
		this.mContext = BaseApplication.getInstance();
		sp = mContext.getSharedPreferences(SP_NAME, 0);
	}

	
	
	public String getLogin_State() {
		return GetData("Login_State", DEFAULT_VALUE);
	}

	public void setLogin_State(String login_State) {

		if (isNullOrEmpty(login_State)) {
			return;
		}
		Login_State = login_State;
		SaveData("Login_State", login_State);
	}

	private void SaveData(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	private String GetData(String key, String defaultvalue) {
		return sp.getString(key, defaultvalue == null ? DEFAULT_VALUE: defaultvalue);
	}

	private boolean isNullOrEmpty(String content) {
		if (TextUtils.isEmpty(content)) {
			return true;
		} else {
			return false;
		}
	}

	public String getLoginCode() {
		return GetData("LoginCode", DEFAULT_VALUE);
	}

	public void setLoginCode(String loginCode) {
		this.LoginCode = loginCode;
		SaveData("LoginCode", LoginCode);
	}
}
