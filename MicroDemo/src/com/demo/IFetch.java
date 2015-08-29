package com.demo;

import java.util.List;


/**
 * 单独取数据类
 * @author sufun
 *
 */
public interface IFetch<T> {

	void onSuccess(List<T> datas);
	
	void onFail(ResultModel result);
	
	void onFetching(int proccess);
	
	void onPrevious();
}
