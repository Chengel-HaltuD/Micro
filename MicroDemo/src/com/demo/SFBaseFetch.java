package com.demo;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.micro.http.MicroRequestParams;
import com.micro.http.MicroStringHttpResponseListener;
import com.micro.utils.L;
import com.micro.utils.M;

/**
 * 
 * @author sufun 鏈�鍩虹鐨勭被
 * @param <T>
 */
public class SFBaseFetch<T> {

	private int current_index = 1;

	private IFetch<T> fetchCallbackDelegate = null;
	/**
	 * Api鐨勫熀纭�鍦板潃
	 */
	private String web_Api_url = Constant.webBaseApi;


	/**
	 * 瀵瑰簲鐨勫弽灏勫瓙绫�
	 */
	public Type type ;
	/**
	 * 璇锋眰鐨勫弬鏁版暟鎹�
	 */
	public MicroRequestParams params = new MicroRequestParams();
	/**
	 * 鎴戦渶瑕佸姞杞界殑鎵�鏈夌殑鏁版嵁
	 */
	List<T> mAllData = new ArrayList<T>();
	/**
	 * 鎺ュ彛action鍚嶇О
	 */
	private String action = "";
	/**
	 * 鍒嗛〉鐨勫弬鏁板悕绉�
	 */
	public String page_param_name = "pageNumber";

	public SFBaseFetch(IFetch<T> ifetch, MicroRequestParams para, String action) {
		this.params = para;
		this.action = action;
		this.fetchCallbackDelegate = ifetch;
		web_Api_url = web_Api_url + action;
		

		initLocalConstValue();

		SecureModel secure = new SecureModel();
		secure = secure.getInstance();

		
		List<BasicNameValuePair> values = params.getParamsList();
		
		boolean isContainSig = false;
		boolean isClientORGuide = false;
		String Method = "";
		String Type = "";
		String Timestamp = "";
		
		for (int i = 0; i < values.size(); i++) 
		{

			if ("Method".equals(values.get(i).getName()))// 鍒ゆ柇鏄鎴风杩樻槸鏈嶅姟绔�
			{
				isClientORGuide = true;
				Method = values.get(i).getValue();
			}
			if ("Type".equals(values.get(i).getName()))
			{
				Type = values.get(i).getValue();
			}
			if ("Timestamp".equals(values.get(i).getName()))
			{
				Timestamp = values.get(i).getValue();
			}
			if ("Sig".equals(values.get(i).getName()))// 瀛樺湪浜嗗彟澶栫殑璁＄畻鏂瑰紡
			{
				isContainSig = true;
			}

		}

		if (isClientORGuide) {
			web_Api_url = Constant.GuideWebBaseApi;
			String sig = M.MD5(Method+Type+"SDK-jryghq-010-001cb0a05ebb6ed38e912f5cb4372851dcc"+ Timestamp + "1.1");
			params.put("Uid", "SDK-jryghq-010-001");
			params.put("Pwd", "cb0a05ebb6ed38e912f5cb4372851dcc");
			params.put("Sig", sig);

		}else {
			if (isContainSig) {
				// 甯︽湁logincode鐨勮璁�
				secure = secure.getSecureLoginInstance();
				params.put("TimesTamp", secure.TimesTamp);
				params.put("Sig", secure.Login_Code_Sig);
			} else {
				//
				params.put("TimesTamp", secure.TimesTamp);
				params.put("Sig", secure.Sig);

				//娴嬭瘯
				params.put("Sig", "test");
			}
		}


	}

	/**
	 * Post鏁版嵁鐨勬彁浜�
	 */
	public void doPost() {
		params.put(page_param_name, current_index + "");
		L.I("post鍙傛暟锛�:" + "    params:" + web_Api_url + params.toString());
		BaseApplication.getInstance().mAbHttpUtil.post(web_Api_url, params,
				new MicroStringHttpResponseListener() {

			// 鑾峰彇鏁版嵁鎴愬姛浼氳皟鐢ㄨ繖閲�
			@Override
			public void onSuccess(int statusCode, String content) {
				L.I("杩斿洖鏁版嵁" + content);
				List<T> mdata = new ArrayList<T>();
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						// 濡傛灉杩斿洖鐨勬槸鍗曚竴涓�鏉℃暟鎹紝鍒欏皢鏁版嵁杞寲涓哄瓧绗︽暟缁�
						if (content.indexOf("[") != 0) {
							content = "[" + content + "]";
						}
						mdata = gson.fromJson(content, type);
						if (mdata.size() > 0) {
							current_index++;
							mAllData.addAll(mdata);
							fetchCallbackDelegate.onSuccess(mAllData);
						} else {
							ResultModel result = new ResultModel();
							result.result_code = "-1";
							result.content = "error: 宸茬粡娌℃湁鏁版嵁浜�--->"
									+ content;
							fetchCallbackDelegate.onFail(result);
						}
					} catch (Exception ex) {
						mdata = new ArrayList<T>();
						ResultModel result = new ResultModel();
						result.result_code = "-1";
						result.content = "error: 杩斿洖鏁版嵁鏍煎紡鍑虹幇闂--->"
								+ content;
						fetchCallbackDelegate.onFail(result);
						L.I("鏁版嵁杞寲鍑虹幇闂" + ex.toString());
						// subPageNum();
					}
				}

			};

			// 寮�濮嬫墽琛屽墠
			@Override
			public void onStart() {
				// OnPrepare();
				fetchCallbackDelegate.onPrevious();
			}

			// 澶辫触锛岃皟鐢�
			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				ResultModel result = new ResultModel();
				result.result_code = statusCode + "";
				result.content = "error:" + content;
				fetchCallbackDelegate.onFail(result);
			}

			// 瀹屾垚鍚庤皟鐢紝澶辫触锛屾垚鍔�
			@Override
			public void onFinish() {

			};

		});
	}

	/**
	 * get鏁版嵁鐨勬彁浜�
	 */
	public void doGet() {
		params.put(page_param_name, current_index + "");
		L.I("get鍙傛暟锛�:" + "    params:" + web_Api_url + params.toString());
		BaseApplication.getInstance().mAbHttpUtil.get(web_Api_url, params,
				new MicroStringHttpResponseListener() {

			// 鑾峰彇鏁版嵁鎴愬姛浼氳皟鐢ㄨ繖閲�
			@Override
			public void onSuccess(int statusCode, String content) {
				L.I("杩斿洖鏁版嵁" + content);
				List<T> mdata = new ArrayList<T>();
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						// 濡傛灉杩斿洖鐨勬槸鍗曚竴涓�鏉℃暟鎹紝鍒欏皢鏁版嵁杞寲涓哄瓧绗︽暟缁�

						if (content.indexOf("[") != 0) {

							content = "[" + content + "]";
						}
						mdata = gson.fromJson(content, type);
						if (mdata.size() > 0) {
							current_index++;
							mAllData.addAll(mdata);
							fetchCallbackDelegate.onSuccess(mAllData);
						} else {
							ResultModel result = new ResultModel();
							result.result_code = "-1";
							result.content = "error: 宸茬粡娌℃湁鏁版嵁浜�--->"
									+ content;
							fetchCallbackDelegate.onFail(result);
						}
					} catch (Exception ex) {
						mdata = new ArrayList<T>();
						ResultModel result = new ResultModel();
						result.result_code = "-1";
						result.content = "error: 杩斿洖鏁版嵁鏍煎紡鍑虹幇闂--->"
								+ content;
						fetchCallbackDelegate.onFail(result);
						L.I("鏁版嵁杞寲鍑虹幇闂" + ex.toString());
						// subPageNum();
					}
				}

			};

			// 寮�濮嬫墽琛屽墠
			@Override
			public void onStart() {
				// OnPrepare();
				fetchCallbackDelegate.onPrevious();
			}

			// 澶辫触锛岃皟鐢�
			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				ResultModel result = new ResultModel();
				result.result_code = statusCode + "";
				result.content = "error:" + content;
				fetchCallbackDelegate.onFail(result);

			}

			// 瀹屾垚鍚庤皟鐢紝澶辫触锛屾垚鍔�
			@Override
			public void onFinish() {

			};

		});
	}

	/**
	 * 鍒濆鍖栨湰鍦版渶鍩烘湰鐨勬暟鎹紝鎴栬�呮槸涓�浜涘父閲忔暟鎹�
	 */
	public void initLocalConstValue() {

	}

	/**
	 * 鍒锋柊
	 */
	public void onRefresh() {
		doPost();
		doGet();
	}

	/**
	 * 鍔犺浇鏇村鏁版嵁
	 */
	public void onLoadMore() {
		doPost();
		doGet();
	}

}
