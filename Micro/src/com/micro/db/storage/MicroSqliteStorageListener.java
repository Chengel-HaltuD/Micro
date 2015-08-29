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
package com.micro.db.storage;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroSqliteStorageListener
 * @Description: 描述：数据操作结构监听器
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:37:51
 * @version V1.0
 *
 */
public class MicroSqliteStorageListener {

	/**
	 * 描述：插入数据的监听.
	 */
	public static abstract interface AbDataInsertListener {

		/**
		 * On success.
		 *
		 * @param rowId the rowId
		 */
		public abstract void onSuccess(long rowId);

		/**
		 * On failure.
		 *
		 * @param errorCode the error code
		 * @param errorMessage the error message
		 */
		public abstract void onFailure(int errorCode, String errorMessage);
	}
	
	/**
	 * 描述：插入数据的监听.
	 */
	public static abstract interface AbDataInsertListListener {

		/**
		 * On success.
		 *
		 * @param rowIds rowIds
		 */
		public abstract void onSuccess(long[] rowIds);

		/**
		 * On failure.
		 *
		 * @param errorCode the error code
		 * @param errorMessage the error message
		 */
		public abstract void onFailure(int errorCode, String errorMessage);
	}

	/**
	 * 描述：查询数据的监听.
	 *
	 */
	public static abstract interface AbDataSelectListener {

		/**
		 * On success.
		 *
		 * @param paramList the param list
		 */
		public abstract void onSuccess(List<?> paramList);

		/**
		 * On failure.
		 *
		 * @param errorCode the error code
		 * @param errorMessage the error message
		 */
		public abstract void onFailure(int errorCode, String errorMessage);
	}

	/**
	 * 描述：修改数据的监听.
	 */
	public static abstract interface AbDataUpdateListener {
		
		/**
		 * On success.
		 *
		 * @param rows the rows
		 */
		public abstract void onSuccess(int rows);

		/**
		 * On failure.
		 *
		 * @param errorCode the error code
		 * @param errorMessage the error message
		 */
		public abstract void onFailure(int errorCode, String errorMessage);
	}
	
	/**
	 * 描述：删除数据的监听.
	 */
	public static abstract interface AbDataDeleteListener {
		
		/**
		 * On success.
		 *
		 * @param rows rows
		 */
		public abstract void onSuccess(int rows);

		/**
		 * On failure.
		 *
		 * @param errorCode the error code
		 * @param errorMessage the error message
		 */
		public abstract void onFailure(int errorCode, String errorMessage);
	}
}
