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
package com.micro.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

// TODO: Auto-generated Javadoc

/**
 * 
 * @ClassName: MicroBasicDBDao
 * @Description: 描述：普通的基础操作类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:42:06
 * @version V1.0
 *
 */
public class MicroBasicDBDao {
	
	/**
	 * 得到列值.
	 * @param columnName the column name
	 * @param cursor the cursor
	 * @return the string column value
	 */
	public String getStringColumnValue(String columnName, Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}
	
	/**
	 * 得到列值.
	 * @param columnName the column name
	 * @param cursor the cursor
	 * @return the int column value
	 */
	public int getIntColumnValue(String columnName, Cursor cursor) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}
	
	/**
	 * 描述：关闭数据库与游标.
	 * @param cursor the cursor
	 * @param db the db
	 */
	public void closeDatabase(Cursor cursor, SQLiteDatabase db) {
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		if (db != null && db.isOpen()) {
			db.close();
			db = null;
		}
	}
	
	/**
	 * 描述：关闭游标.
	 * @param cursor the cursor
	 */
	public void closeCursor(Cursor cursor) {
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}
}
