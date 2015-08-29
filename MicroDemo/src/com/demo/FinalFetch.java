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

import com.google.gson.reflect.TypeToken;
import com.micro.http.MicroRequestParams;
/**
 * @ClassName: FinalFatch
 * @Description: TODO
 * @Author：GeLe
 * @Date：2015-5-26 上午11:02:39
 * @version V1.0
 * @param <T>
 *
 */
public class FinalFetch<T> extends SFBaseFetch<T> {

	public FinalFetch(IFetch<T> ifetch, MicroRequestParams para,TypeToken<List<T>> TypeToken) {
		// TODO Auto-generated constructor stub
		super(ifetch, para, "");
		
		type = TypeToken.getType();

		doGet();
	}
	public FinalFetch(IFetch<T> ifetch, MicroRequestParams para,TypeToken<List<T>> TypeToken,int Method) {
		// TODO Auto-generated constructor stub
		super(ifetch, para, "");
		
		type = TypeToken.getType();

		if (Method == 0) doGet();

		if (Method == 1) doPost();
	}


	public FinalFetch(IFetch<T> ifetch, MicroRequestParams para,TypeToken<List<T>> TypeToken, String action) {
		// TODO Auto-generated constructor stub
		super(ifetch, para, action==null?"":action);
		
		type = TypeToken.getType();

		doGet();
	}

	public FinalFetch(IFetch<T> ifetch, MicroRequestParams para,TypeToken<List<T>> TypeToken, String action,int Method) {
		// TODO Auto-generated constructor stub
		super(ifetch, para, action==null?"":action);
		
		type = TypeToken.getType();

		if (Method == 0) doGet();

		if (Method == 1) doPost();
	}
}
