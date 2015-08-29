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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JxJsonUtils {
	
	/**
	 * 获得单个数据
	 * 
	 * @param <TF>
	 * @param in
	 *            输入流
	 * @param root
	 *            结点
	 * @param clazz
	 *            实体Class
	 * @return
	 * @throws Exception
	 */
	public static <TF> TF getBean(String json, List<String> jo, String ja,
			Class<TF> clazz) throws Exception {
		ArrayList<TF> list = new ArrayList<TF>();
		TF t = null;
		t = clazz.newInstance();
		// 取出bean里的所有方法
		Field[] fields = t.getClass().getDeclaredFields();
		List<String> listKey = new ArrayList<String>();
		for (Field field : fields) {
			listKey.add(field.getName());
		}
		HashMap<String, Object> map = getJSONParserResult(json, jo, ja, listKey);
		t = clazz.newInstance();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			Field field = t.getClass().getField((String) entry.getKey());
			field.set(t, entry.getValue().toString());
		}
		return t;
	}
	
	/**
	 * 获取list数据
	 * 
	 * @param <TF>
	 * @param in
	 *            输入流
	 * @param root
	 *            结点
	 * @param clazz
	 *            实体Class
	 * @return
	 * @throws Exception
	 */
	public static <TF> ArrayList<TF> getBeanList(String json, List<String> jo, String ja,Class<TF> clazz)
			throws Exception {
		ArrayList<TF> list = new ArrayList<TF>();
		TF t = null;
		t = clazz.newInstance();
		// 取出bean里的所有方法
		Field[] fields = t.getClass().getDeclaredFields();
		List<String> listKey = new ArrayList<String>();
		for (Field field : fields) {
			listKey.add(field.getName());
		}
		ArrayList<HashMap<String, String>> list1 = getJSONParserResultArray(
				json, jo, ja, listKey);
		for (HashMap<String, String> map : list1) {
			t = clazz.newInstance();
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				Field field = t.getClass().getField((String) entry.getKey());
				field.set(t, entry.getValue().toString());
			}
			list.add(t);
		}
		return list;
	}

	// 本类是用于解析数据返回的数据用的。
	// 返回数据的格式都是JSON，但是可以分为2种，一种是key、values的值对，另一种就是值对数组的数据，分别用两种方法进行解析。
	// 下面的方法是解析第一种的，传入参数为服务器返回的数据字符串和由数据key组成的数组，解析后返回值为
	// HashMap<String , Object>，就是我们需要的值对。
	public static HashMap<String, Object> getJSONParserResult(
			String JSONString, List<String> jo, String ja, List<String> key) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		try {
			// JSONObject result = new JSONObject(JSONString)
			// .getJSONObject("response");
			JSONObject result = new JSONObject(JSONString);
			if (jo != null && jo.size() > 0) {
				for (String s : jo) {
					result = result.getJSONObject(s);
				}
			}
			if (ja == null || ja == "") {
				for (int j = 0; j < key.size(); j++) {
					if (result.has(key.get(j))) {
						hashMap.put(key.get(j), result.get(key.get(j)));
					}
				}
			} else {
				JSONArray resultArray = result.getJSONArray(ja);
				JSONObject resultTemp = (JSONObject) resultArray.opt(0);
				hashMap = new HashMap<String, Object>();
				for (int j = 0; j < key.size(); j++) {
					if (resultTemp.has(key.get(j))) {
						hashMap.put(key.get(j), resultTemp.get(key.get(j)));
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
			
		}
		return hashMap;
	}
	
	// 下面的方法是解析第二种的，传入参数为服务器返回的数据字符串和由数据key组成的数组，解析后返回值为
		// ArrayList<HashMap<String , Object>>，就是我们需要的值对数组。
		public static ArrayList<HashMap<String, String>> getJSONParserResultArray(
				String JSONString, List<String> jo, String ja, List<String> listKey) {
			ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> hashMap;
			try {
				JSONObject result = new JSONObject(JSONString);
				if (jo != null && jo.size() > 0) {
					for (String s : jo) {
						result = result.getJSONObject(s);
					}
				}
				JSONArray resultArray = result.getJSONArray(ja);
				for (int i = 0; i < resultArray.length(); i++) {
					JSONObject resultTemp = (JSONObject) resultArray.opt(i);
					hashMap = new HashMap<String, String>();
					if (listKey != null) {
						for (int j = 0; j < listKey.size(); j++) {
							if (resultTemp.has(listKey.get(j))) {
								hashMap.put(listKey.get(j),
										resultTemp.get(listKey.get(j)).toString());
							}
						}
					} else {
						for (Iterator iter = resultTemp.keys(); iter.hasNext();) { // 先遍历整个
							String key = (String) iter.next();
							hashMap.put(key, resultTemp.getString(key));
						}
					}
					arrayList.add(hashMap);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return arrayList;
		}

	// 下面的方法是解析第二种的，传入参数为服务器返回的数据字符串和由数据key组成的数组，解析后返回值为
	// ArrayList<HashMap<String , Object>>，就是我们需要的值对数组。
	public static ArrayList<HashMap<String, String>> getJSONParserResultArray1(
			String JSONString, List<String> jo,String ja, List<String> listKey) {
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap;
		try {
			JSONObject result = new JSONObject(JSONString);
			if (jo != null && jo.size() > 0) {
				for (String s : jo) {
					result = result.getJSONObject(s);
				}
			}
			JSONArray resultArray = result.getJSONArray(ja);
			for (int i = 0; i < resultArray.length(); i++) {
				JSONObject resultTemp = (JSONObject) resultArray.opt(i);
				hashMap = new HashMap<String, String>();
				if (listKey != null) {
					for (int j = 0; j < listKey.size(); j++) {
						if (resultTemp.has(listKey.get(j))) {
							hashMap.put(listKey.get(j),
									resultTemp.get(listKey.get(j)).toString());
						}
					}
				} else {
					for (Iterator iter = resultTemp.keys(); iter.hasNext();) { // 先遍历整个
						String key = (String) iter.next();
						hashMap.put(key, resultTemp.getString(key));
					}
				}
				arrayList.add(hashMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	/**
	 * 
	  * 方法描述	判断接口返回的是不是json数据，不是则直接退出
	  * create: 2014-11-10上午11:51:52
	  * @param model 参数描述
	  * @return 返回值描述
	 */
	public void JsonException(String content){
		try {
//			com.alibaba.fastjson.JSONObject objectE =  JSON.parseObject(content);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
	}



}
