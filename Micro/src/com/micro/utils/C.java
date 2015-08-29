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
package com.micro.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.text.TextUtils;

import com.micro.cache.MicroCacheResponse;



// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: C
 * @Description: C是AbCacheUtil 描述：缓存工具类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:05:17
 * @version V1.0
 *
 */
public class C {

	/**
	 * 从连接中获取响应信息.
	 *
	 * @param url the url
	 * @param expiresTime the expires time
	 * @return the cache response
	 */
	public static MicroCacheResponse getCacheResponse(String url,int expiresTime){
		URLConnection con = null;
		InputStream is = null;
		MicroCacheResponse response = null;
		try {
			URL imageURL = new URL(url);
			con = imageURL.openConnection();
			con.setDoInput(true);
			con.connect();
			is = con.getInputStream();
			byte [] data = Y.stream2bytes(is);
			// 获取所有响应头字段
			Map<String, List<String>> headers = con.getHeaderFields();////(关键在此)(服务器Header信息 ?)
			Map<String,String> mapHeaders = new HashMap<String,String>();
			//Set<Map.Entry<K,V>> entrySet() //返回此映射中包含的映射关系的 Set 视图。 Map.Entry表示映射关系。entrySet()：迭代后可以e.getKey()，e.getValue()取key和value。返回的是Entry接口 
			//Set<Map.Entry<K,V>> entrySet() //返回映射所包含的映射关系的Set集合（一个关系就是一个键-值对），就是把(key-value)作为一个整体一对一对地存放到Set集合当中的。
			for (Map.Entry<String, List<String>> entry : headers.entrySet()) { //最后说明下keySet()的速度比entrySet()慢了很多

				String key = entry.getKey();
				List<String> values = entry.getValue();
				if(TextUtils.isEmpty(key)){
					key = "andbase";////
				}
				mapHeaders.put(key, values.get(0));////

			}  

			//强制缓存
			if(!mapHeaders.containsKey("Cache-Control")){
				mapHeaders.put("Cache-Control", "max-age="+expiresTime);////
			}

			/*key = null and value = [HTTP/1.1 200 OK]
		    key = Accept-Ranges and value = [bytes]
			key = Connection and value = [Keep-Alive]
			key = Content-Length and value = [4357]
			key = Content-Type and value = [image/png]
			key = Date and value = [Thu, 02 Apr 2015 10:42:54 GMT]
			key = ETag and value = ["620e07-1105-4f5d6331a2300"]
			key = Keep-Alive and value = [timeout=15, max=97]
			key = Last-Modified and value = [Sun, 30 Mar 2014 17:23:56 GMT]
			key = Server and value = [Apache]
			key = X-Android-Received-Millis and value = [1427971373392]
			key = X-Android-Sent-Millis and value = [1427971373356]*/

			response = new MicroCacheResponse(data, mapHeaders);

		} catch (Exception e) {
			//e.printStackTrace();
			L.D( "" + e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

}
