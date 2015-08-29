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


import java.util.ArrayList;

import android.text.TextUtils;




// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroStorageQuery
 * @Description: 描述：条件过滤实体
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:38:15
 * @version V1.0
 *
 */
public class MicroStorageQuery {
	
	/** where 子句. */
	private String whereClause = null;
	
	/** where 子句的绑定参数. */
	private ArrayList<String> whereArgs = null;
	
	/** having 子句. */
	private String having = null;
	
	/** groupBy 子句. */
	private String groupBy = null;
	
	/** orderBy 子句. */
	private String orderBy = null;
	
	/** limit 值. */
	private int limit = -1;
	
	/** offset 值. */
	private int offset = -1;
	
	/**
	 * Instantiates a new ab storage query.
	 */
	public MicroStorageQuery() {
		super();
		whereClause = "";
		whereArgs = new ArrayList<String>();
	}
	
	/**
	 * 描述：相等语句.
	 *
	 * @param paramString the param string
	 * @param paramObject the param object
	 * @return the ab storage query
	 */
	public MicroStorageQuery equals(String paramString, Object paramObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		whereClause += " "+(paramString+" = ? ");
		whereArgs.add(paramObject.toString());
		return this;
	}
	
	/**
	 * 描述：不相等语句.
	 *
	 * @param paramString the param string
	 * @param paramObject the param object
	 * @return the ab storage query
	 */
	public MicroStorageQuery notEqual(String paramString, Object paramObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		whereClause += " "+(paramString+" <> ? ");
		whereArgs.add(paramObject.toString());
		return this;
	}
	
	/**
	 * 描述：相似语句.
	 *
	 * @param paramString the param string
	 * @param paramObject the param object
	 * @return the ab storage query
	 */
	public MicroStorageQuery like(String paramString, Object paramObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		whereClause += " "+(paramString+"like ? ");
		whereArgs.add("'%"+paramObject.toString()+"%'");
		return this;
	}
	
	/**
	 * 描述：大于语句.
	 *
	 * @param paramString the param string
	 * @param paramObject the param object
	 * @return the ab storage query
	 */
	public MicroStorageQuery greaterThan(String paramString, Object paramObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		whereClause += " "+(paramString+" > ? ");
		whereArgs.add(paramObject.toString());
	    return this;
	}

	/**
	 * 描述：小于语句.
	 *
	 * @param paramString the param string
	 * @param paramObject the param object
	 * @return the ab storage query
	 */
	public MicroStorageQuery lessThan(String paramString, Object paramObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		whereClause += " "+(paramString+" < ? ");
		whereArgs.add(paramObject.toString());
	    return this;
	}
	
	/**
	 * 描述：大于等于语句.
	 *
	 * @param paramString the param string
	 * @param paramObject the param object
	 * @return the ab storage query
	 */
	public MicroStorageQuery greaterThanEqualTo(String paramString, Object paramObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		whereClause += " "+(paramString+" >= ? ");
		whereArgs.add(paramObject.toString());
	    return this;
	}

	/**
	 * 描述：小于等于语句.
	 *
	 * @param paramString the param string
	 * @param paramObject the param object
	 * @return the ab storage query
	 */
	public MicroStorageQuery lessThanEqualTo(String paramString, Object paramObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		whereClause += " "+(paramString+" <= ? ");
		whereArgs.add(paramObject.toString());
	    return this;
	}
	
	/**
	 * 描述：包含语句.
	 *
	 * @param paramString the param string
	 * @param paramArrayOfObject the param array of object
	 * @return the ab storage query
	 */
	public MicroStorageQuery in(String paramString, Object[] paramArrayOfObject){
		if(!TextUtils.isEmpty(whereClause)){
			whereClause += " and ";
		}
		if(paramArrayOfObject!=null && paramArrayOfObject.length>0){
			whereClause += " "+(paramString+" in ( ");
			for(int i=0;i<paramArrayOfObject.length;i++){
				if(i!=0){
					whereClause += " , ";
				}
				whereClause += " ? ";
			}
			whereClause += " ) ";
			
			for(Object str:paramArrayOfObject){
				whereArgs.add((String)str);
			}
			
		}else{
			whereClause += " "+paramString;
		}
		
	    return this;
	}

	/**
	 * 描述：不包含语句.
	 *
	 * @param paramString the param string
	 * @param paramArrayOfObject the param array of object
	 * @return the ab storage query
	 */
	public MicroStorageQuery notIn(String paramString, Object[] paramArrayOfObject) {
		if(paramArrayOfObject!=null && paramArrayOfObject.length>0){
			whereClause += " "+(paramString+" not in ( ");
			for(int i=0;i<paramArrayOfObject.length;i++){
				if(i!=0){
					whereClause += " , ";
				}
				whereClause += " ? ";
			}
			whereClause += " ) ";
			whereArgs.add(paramArrayOfObject.toString());
		}else{
			whereClause += " "+paramString;
		}
		
	    return this;
	}
	
	/**
	 * 描述：和and语句.
	 *
	 * @param storageQuery the storage query
	 * @return the ab storage query
	 */
	public MicroStorageQuery and(MicroStorageQuery storageQuery){
		whereClause += " and "+"("+storageQuery.getWhereClause()+")";
		for(String args:storageQuery.getWhereArgs()){
			this.whereArgs.add(args);
		}
		return this;
	}
	
	/**
	 * 描述：或者or语句.
	 *
	 * @param storageQuery the storage query
	 * @return the ab storage query
	 */
	public MicroStorageQuery or(MicroStorageQuery storageQuery){
		whereClause += " or "+"("+storageQuery.getWhereClause()+")";
		for(String args:storageQuery.getWhereArgs()){
			this.whereArgs.add(args);
		}
		return this;
	}
	
	
	/**
	 * 描述：设置一个完整的sql语句.
	 *
	 * @param whereClause 如 user_name = ?  或者 user_name = 'xiao'
	 * @param whereArgs the where args
	 */
	public void setWhereClause(String whereClause,String[] whereArgs) {
		this.whereClause = whereClause;
		for(String args:whereArgs){
			this.whereArgs.add(args);
		}
	}
	
	/**
	 * 描述：获取当前的查询sql语句.
	 *
	 * @return the where clause
	 */
	public String getWhereClause() {
		return whereClause;
	}
	
    /**
     * 描述：获得绑定变量的参数.
     *
     * @return the where args
     */
	public String[] getWhereArgs() {
		String[] argsArray = new String[whereArgs.size()];
		for(int i=0;i<whereArgs.size();i++){
			String args = whereArgs.get(i);
			argsArray[i] = args;
		}
		return argsArray;
	}
	
	/**
	 * 描述：排序语句.
	 *
	 * @param paramString the param string
	 * @param paramSortOrder the param sort order
	 * @return the ab storage query
	 */
	public MicroStorageQuery addSort(String paramString, SortOrder paramSortOrder){
	    if(TextUtils.isEmpty(orderBy)){
	    	orderBy = " " + paramString + " " + paramSortOrder;
	    }else{
	    	orderBy += " , " + paramString + " " + paramSortOrder;
	    }
	    return this;
	}

	/**
	 * Gets the having.
	 *
	 * @return the having
	 */
	public String getHaving() {
		return having;
	}

	/**
	 * Sets the having.
	 *
	 * @param having the new having
	 */
	public void setHaving(String having) {
		this.having = having;
	}

	/**
	 * Gets the group by.
	 *
	 * @return the group by
	 */
	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * Sets the group by.
	 *
	 * @param groupBy the new group by
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * Gets the order by.
	 *
	 * @return the order by
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the order by.
	 *
	 * @param orderBy the new order by
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Gets the limit.
	 *
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Sets the limit.
	 *
	 * @param limit the new limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets the offset.
	 *
	 * @param offset the new offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	
	
	/**
	 * 描述：排序.
	 */
	public static enum SortOrder{
	    
    	/** The asc. */
    	ASC, 
    	
		/** The desc. */
		DESC;
	}
	
	
	/**
	 * Prints the log.
	 *
	 * @param mAbStorageQuery the m ab storage query
	 */
	public static void printLog(MicroStorageQuery mAbStorageQuery){
		System.out.println("where "+mAbStorageQuery.getWhereClause());
		if(!TextUtils.isEmpty(mAbStorageQuery.getOrderBy())){
			System.out.println("order by "+mAbStorageQuery.getOrderBy());
		}
		
		System.out.print("参数:[");
		for(int i=0;i<mAbStorageQuery.getWhereArgs().length;i++){
			if(i!=0){
				System.out.print(",");
			}
			System.out.print(mAbStorageQuery.getWhereArgs()[i]);
		}
		System.out.print("]");
		System.out.println(" ");
		System.out.println("－－－－－－－－－－－－－－－－－－－－－－－－－");
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//测试1
		MicroStorageQuery mAbStorageQuery1 = new MicroStorageQuery();
		
		//第一组条件
		mAbStorageQuery1.equals("u_id","1");
		mAbStorageQuery1.equals("face_u_id","2");
		
		//第二组条件
		MicroStorageQuery mAbStorageQuery2 = new MicroStorageQuery();
		mAbStorageQuery2.equals("face_u_id","3");
		mAbStorageQuery2.equals("u_id","4");
		
		//组合查询
		MicroStorageQuery mAbStorageQuery = mAbStorageQuery1.or(mAbStorageQuery2);
		
		printLog(mAbStorageQuery);
		
		//测试2
		MicroStorageQuery mAbStorageQuery3 = new MicroStorageQuery();
		MicroStorageQuery mAbStorageQuery4 = new MicroStorageQuery();
		mAbStorageQuery3.equals("u_id","1");
		mAbStorageQuery4.equals("face_u_id","3");
		MicroStorageQuery mAbStorageQuery5 = mAbStorageQuery3.and(mAbStorageQuery4);
		printLog(mAbStorageQuery5);
		
		//测试3
		MicroStorageQuery mAbStorageQuery6 = new MicroStorageQuery();
		MicroStorageQuery mAbStorageQuery7 = new MicroStorageQuery();
		mAbStorageQuery6.lessThan("u_id","1");
		mAbStorageQuery7.greaterThanEqualTo("face_u_id","3");
		MicroStorageQuery mAbStorageQuery8 = mAbStorageQuery6.and(mAbStorageQuery7);
		printLog(mAbStorageQuery8);
		
		//测试4
		MicroStorageQuery mAbStorageQuery9 = new MicroStorageQuery();
		mAbStorageQuery9.in("name", new String []{"1","2","3","4"});
		mAbStorageQuery9.addSort("time", SortOrder.ASC);
		mAbStorageQuery9.addSort("state", SortOrder.DESC);
		printLog(mAbStorageQuery9);
		
	}
	
  
}
