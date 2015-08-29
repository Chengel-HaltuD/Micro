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
package com.micro.http;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.micro.cache.MicroAppConfig;
import com.micro.http.entity.MultipartEntity;
import com.micro.http.ssl.EasySSLProtocolSocketFactory;
import com.micro.utils.A;
import com.micro.utils.F;
import com.micro.utils.L;


// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroHttpClient
 * @Description: 描述：Http客户端
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:51:19
 * @version V1.0
 *
 */
public class MicroHttpClient {
	
    /** 上下文. */
	private static Context mContext;
	
	/** 线程执行器. */
	public static Executor mExecutorService = null;
	
	/** 编码. */
	private String encode = HTTP.UTF_8;
	
	/** 用户代理. */
	private String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 BIDUBrowser/6.x Safari/537.31";
	
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";
    private static final String USER_AGENT = "User-Agent";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    
    /** CookieStore. */
    private CookieStore mCookieStore;  

    /** 最大连接数. */
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    
    /** 超时时间. */
    public static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    
    /** 重试次数. */
    private static final int DEFAULT_MAX_RETRIES = 2;
    
    /** 缓冲大小. */
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    
    /** 成功. */
    protected static final int SUCCESS_MESSAGE = 0;
    
    /** 失败. */
    protected static final int FAILURE_MESSAGE = 1;
    
    /** 和网络相关的失败. */
    protected static final int FAILURE_MESSAGE_CONNECT = 2;
    
    /** 和服务相关的失败. */
    protected static final int FAILURE_MESSAGE_SERVICE = 3;
    
    /** 开始. */
    protected static final int START_MESSAGE = 4;
    
    /** 完成. */
    protected static final int FINISH_MESSAGE = 5;
    
    /** 进行中. */
    protected static final int PROGRESS_MESSAGE = 6;
    
    /** 重试. */
    protected static final int RETRY_MESSAGE = 7;
    
    /** 超时时间. */
	private int mTimeout = DEFAULT_SOCKET_TIMEOUT;
	
	/** 通用证书. 如果要求HTTPS连接，则使用SSL打开连接*/
	private boolean mIsOpenEasySSL = true;
	
	/** HTTP Client*/
	private DefaultHttpClient mHttpClient = null;
	
	/** HTTP 上下文*/
	private HttpContext mHttpContext = null;
    
    /**
     * 初始化.
     *
     * @param context the context
     */
	public MicroHttpClient(Context context) {
	    mContext = context;
		mExecutorService =  MicroThreadFactory.getExecutorService();
		mHttpContext = new BasicHttpContext();
	}
	

	/**
	 * 描述：带参数的get请求.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	public void get(final String url,final MicroRequestParams params,final MicroHttpResponseListener responseListener) {
		
		responseListener.setHandler(new ResponderHandler(responseListener));
		mExecutorService.execute(new Runnable() { 
    		public void run() {
    			try {
    				doGet(url,params,responseListener);
    			} catch (Exception e) { 
    				e.printStackTrace();
    			}
    		}                 
    	});      
	}
	
	/**
	 * 描述：带参数的post请求.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	public void post(final String url,final MicroRequestParams params,
			final MicroHttpResponseListener responseListener) {
		responseListener.setHandler(new ResponderHandler(responseListener));
		mExecutorService.execute(new Runnable() { 
    		public void run() {
    			try {
    				doPost(url,params,responseListener);
    			} catch (Exception e) { 
    				e.printStackTrace();
    			}
    		}                 
    	});      
	}
	
	
	/**
	 * 描述：执行get请求.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	private void doGet(String url,MicroRequestParams params,MicroHttpResponseListener responseListener){
		  try {
			  
			  responseListener.sendStartMessage();
			  
			  if(!A.isNetworkAvailable(mContext)){
				    Thread.sleep(200);
					responseListener.sendFailureMessage(MicroHttpStatus.CONNECT_FAILURE_CODE,MicroAppConfig.CONNECT_EXCEPTION, new MicroAppException(MicroAppConfig.CONNECT_EXCEPTION));
			        return;
			  }
			  
			  //HttpGet连接对象  
			  if(params!=null){
				  if(url.indexOf("?")==-1){
					  url += "?";
				  }
				  url += params.getParamString();
			  }
			  HttpGet httpGet = new HttpGet(url);  
			  httpGet.addHeader(USER_AGENT, userAgent);
			  //压缩
			  httpGet.addHeader(ACCEPT_ENCODING, "gzip");
			  //取得默认的HttpClient
      	      HttpClient httpClient = getHttpClient();  
		      //取得HttpResponse
		      String response = httpClient.execute(httpGet,new RedirectionResponseHandler(url,responseListener),mHttpContext);  
			  L.I( "[HTTP GET]:"+url+",result："+response);
		} catch (Exception e) {
			e.printStackTrace();
			//发送失败消息
			responseListener.sendFailureMessage(MicroHttpStatus.UNTREATED_CODE,e.getMessage(),new MicroAppException(e));
		}finally{
			responseListener.sendFinishMessage();
		}
	}
	
	/**
	 * 描述：执行post请求.
	 *
	 * @param url the url
	 * @param params the params
	 * @param responseListener the response listener
	 */
	private void doPost(String url,MicroRequestParams params,MicroHttpResponseListener responseListener){
		  try {
			  responseListener.sendStartMessage();
			  
			  if(!A.isNetworkAvailable(mContext)){
				    Thread.sleep(200);
					responseListener.sendFailureMessage(MicroHttpStatus.CONNECT_FAILURE_CODE,MicroAppConfig.CONNECT_EXCEPTION, new MicroAppException(MicroAppConfig.CONNECT_EXCEPTION));
			        return;
			  }
			  
			  //HttpPost连接对象  
		      HttpPost httpPost = new HttpPost(url);  
		      httpPost.addHeader(USER_AGENT, userAgent);
			  //压缩
		      httpPost.addHeader(ACCEPT_ENCODING, "gzip");
		      //是否包含文件
		      boolean isContainFile = false;
		      if(params != null){
		    	  //使用NameValuePair来保存要传递的Post参数设置字符集 
			      HttpEntity httpentity = params.getEntity();
			      //请求httpRequest  
			      httpPost.setEntity(httpentity); 
			      if(params.getFileParams().size()>0){
			    	  isContainFile = true;
			      }
			  }
		      String  response = null;
		      //取得默认的HttpClient
		      DefaultHttpClient httpClient = getHttpClient();  
		      if(isContainFile){
		    	  httpPost.addHeader("connection", "keep-alive");
			      httpPost.addHeader("Content-Type", "multipart/form-data; boundary=" + params.boundaryString());
		    	  L.I( "[HTTP POST]:"+url+",包含文件域!");
		      }
		      //取得HttpResponse
		      response = httpClient.execute(httpPost,new RedirectionResponseHandler(url,responseListener),mHttpContext);  
		      L.I( "request："+url+",result："+response);
			  
		} catch (Exception e) {
			e.printStackTrace();
			L.I( "[HTTP POST]:"+url+",error："+e.getMessage());
			//发送失败消息
			responseListener.sendFailureMessage(MicroHttpStatus.UNTREATED_CODE,e.getMessage(),new MicroAppException(e));
		}finally{
			responseListener.sendFinishMessage();
		}
	}
	
	/**
     * 简单的请求,只支持返回的数据是String类型,不支持转发重定向
     * @param url
     * @param params
     * @return
     */
    public void doRequest(final String url, final MicroRequestParams params, final MicroStringHttpResponseListener responseListener) {
    	responseListener.setHandler(new ResponderHandler(responseListener));
		mExecutorService.execute(new Runnable() { 
    		public void run() {
    			HttpURLConnection urlConn = null;
    			try {
    				responseListener.sendStartMessage();
        			
        			if(!A.isNetworkAvailable(mContext)){
        				Thread.sleep(200);
    					responseListener.sendFailureMessage(MicroHttpStatus.CONNECT_FAILURE_CODE,MicroAppConfig.CONNECT_EXCEPTION, new MicroAppException(MicroAppConfig.CONNECT_EXCEPTION));
    			        return;
    			    }
        			
    				String resultString = null;
					URL requestUrl = new URL(url);
					urlConn = (HttpURLConnection) requestUrl.openConnection();
			        urlConn.setRequestMethod("POST");
			        urlConn.setConnectTimeout(mTimeout);
			        urlConn.setReadTimeout(mTimeout);
			        urlConn.setDoOutput(true);
			        
					if(params!=null){
						urlConn.setRequestProperty("connection", "keep-alive");
				        urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + params.boundaryString());
						MultipartEntity reqEntity = params.getMultiPart();
				        reqEntity.writeTo(urlConn.getOutputStream());
					}else{
						urlConn.connect();
					}
			       
		            if (urlConn.getResponseCode() == HttpStatus.SC_OK){
		            	resultString = readString(urlConn.getInputStream());
		            }else{
		            	resultString = readString(urlConn.getErrorStream());
		            }
		            resultString = URLEncoder.encode(resultString, encode);
		            urlConn.getInputStream().close();
		            responseListener.sendSuccessMessage(MicroHttpStatus.SUCCESS_CODE, resultString);
    			} catch (Exception e) { 
    				e.printStackTrace();
					L.I( "[HTTP POST]:"+url+",error："+e.getMessage());
					//发送失败消息
					responseListener.sendFailureMessage(MicroHttpStatus.UNTREATED_CODE,e.getMessage(),new MicroAppException(e));
    			} finally {
					if (urlConn != null)
						urlConn.disconnect();
					
					responseListener.sendFinishMessage();
				}
    		}                 
    	});      
	}
	
	
	/**
	 * 描述：写入文件并回调进度.
	 * 
	 * @param context the context
	 * @param entity the entity
	 * @param name the name
	 * @param responseListener the response listener
	 */
    public void writeResponseData(Context context,HttpEntity entity,String name,MicroFileHttpResponseListener responseListener){
        
    	if(entity == null){
        	return;
        }
    	
    	if(responseListener.getFile() == null){
    		//创建缓存文件
    		responseListener.setFile(context,name);
        }
    	
    	InputStream inStream = null;
    	FileOutputStream outStream = null;
        try {
	        inStream = entity.getContent();
	        long contentLength = entity.getContentLength();
	        outStream = new FileOutputStream(responseListener.getFile());
	        if (inStream != null) {
	          
	              byte[] tmp = new byte[4096];
	              int l, count = 0;
	              while ((l = inStream.read(tmp)) != -1 && !Thread.currentThread().isInterrupted()) {
	                  count += l;
	                  outStream.write(tmp, 0, l);
	                  responseListener.sendProgressMessage(count, (int) contentLength);
	              }
	        }
	        responseListener.sendSuccessMessage(200);
	    }catch(Exception e){
	        e.printStackTrace();
	        //发送失败消息
			responseListener.sendFailureMessage(MicroHttpStatus.RESPONSE_TIMEOUT_CODE,MicroAppConfig.SOCKET_TIMEOUT_EXCEPTION,e);
	    } finally {
        	try {
        		if(inStream!=null){
        			inStream.close();
        		}
        		if(outStream!=null){
        			outStream.flush();
    				outStream.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        
    }
    
    /**
     * 描述：转换为二进制并回调进度.
     *
     * @param entity the entity
     * @param responseListener the response listener
     */
    public void readResponseData(HttpEntity entity,MicroBinaryHttpResponseListener responseListener){
        
    	if(entity == null){
        	return;
        }
    	
        InputStream inStream = null;
        ByteArrayOutputStream outSteam = null; 
       
    	try {
    		inStream = entity.getContent();
			outSteam = new ByteArrayOutputStream(); 
			long contentLength = entity.getContentLength();
			if (inStream != null) {
				  int l, count = 0;
				  byte[] tmp = new byte[4096];
				  while((l = inStream.read(tmp))!=-1){ 
					  count += l;
			          outSteam.write(tmp,0,l); 
			          responseListener.sendProgressMessage(count, (int) contentLength);

			     } 
			  }
			 responseListener.sendSuccessMessage(HttpStatus.SC_OK,outSteam.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			//发送失败消息
			responseListener.sendFailureMessage(MicroHttpStatus.RESPONSE_TIMEOUT_CODE,MicroAppConfig.SOCKET_TIMEOUT_EXCEPTION,e);
		}finally{
			try {
				if(inStream!=null){
					inStream.close();
				}
				if(outSteam!=null){
					outSteam.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	
        
    }
    
    /**
     * 描述：设置连接超时时间.
     *
     * @param timeout 毫秒
     */
    public void setTimeout(int timeout) {
    	this.mTimeout = timeout;
	}

	/**
	 * © 2012 amsoft.cn
	 * 名称：ResponderHandler.java 
	 * 描述：请求返回
	 *
	 * @author 还如一梦中
	 * @version v1.0
	 * @date：2013-11-13 下午3:22:30
	 */
    private static class ResponderHandler extends Handler {
		
		/** 响应数据. */
		private Object[] response;
		
		/** 响应消息监听. */
		private MicroHttpResponseListener responseListener;
		

		/**
		 * 响应消息处理.
		 *
		 * @param responseListener the response listener
		 */
		public ResponderHandler(MicroHttpResponseListener responseListener) {
			this.responseListener = responseListener;
		}

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
			case SUCCESS_MESSAGE:
				response = (Object[]) msg.obj;
				
				if (response != null){
					if(responseListener instanceof MicroStringHttpResponseListener){
						if(response.length >= 2){
							((MicroStringHttpResponseListener)responseListener).onSuccess((Integer) response[0],(String)response[1]);
						}else{
							L.E( "SUCCESS_MESSAGE "+MicroAppConfig.MISSING_PARAMETERS);
						}
						
					}else if(responseListener instanceof MicroBinaryHttpResponseListener){
						if(response.length >= 2){ 
						    ((MicroBinaryHttpResponseListener)responseListener).onSuccess((Integer) response[0],(byte[])response[1]);
						}else{
							L.E( "SUCCESS_MESSAGE "+MicroAppConfig.MISSING_PARAMETERS);
						}
					}else if(responseListener instanceof MicroFileHttpResponseListener){
						
						if(response.length >= 1){ 
							MicroFileHttpResponseListener mAbFileHttpResponseListener = ((MicroFileHttpResponseListener)responseListener);
							mAbFileHttpResponseListener.onSuccess((Integer) response[0],mAbFileHttpResponseListener.getFile());
						}else{
							L.E( "SUCCESS_MESSAGE "+MicroAppConfig.MISSING_PARAMETERS);
						}
						
					}
				}
                break;
			case FAILURE_MESSAGE:
				 response = (Object[]) msg.obj;
                 if (response != null && response.length >= 3){
                	 //异常转换为可提示的
                	 MicroAppException exception = new MicroAppException((Exception) response[2]);
					 responseListener.onFailure((Integer) response[0], (String) response[1], exception);
                 }else{
                	 L.E( "FAILURE_MESSAGE "+MicroAppConfig.MISSING_PARAMETERS);
                 }
	             break;
			case START_MESSAGE:
				responseListener.onStart();
				break;
			case FINISH_MESSAGE:
				responseListener.onFinish();
				break;
			case PROGRESS_MESSAGE:
				 response = (Object[]) msg.obj;
	             if (response != null && response.length >= 2){
	            	 responseListener.onProgress((Long) response[0], (Long) response[1]);
			     }else{
			    	 L.E( "PROGRESS_MESSAGE "+MicroAppConfig.MISSING_PARAMETERS);
			     }
	             break;
			case RETRY_MESSAGE:
				responseListener.onRetry();
				break;
			default:
				break;
		   }
		}
		
	}
    
    /**
     * HTTP参数配置
     * @return
     */
    public BasicHttpParams getHttpParams(){
    	
    	BasicHttpParams httpParams = new BasicHttpParams();
        
        // 设置每个路由最大连接数
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(30);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
        HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);
		// 从连接池中取连接的超时时间，设置为1秒
	    ConnManagerParams.setTimeout(httpParams, mTimeout);
	    ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(DEFAULT_MAX_CONNECTIONS));
	    ConnManagerParams.setMaxTotalConnections(httpParams, DEFAULT_MAX_CONNECTIONS);
	    // 读响应数据的超时时间
	    HttpConnectionParams.setSoTimeout(httpParams, mTimeout);
	    HttpConnectionParams.setConnectionTimeout(httpParams, mTimeout);
	    HttpConnectionParams.setTcpNoDelay(httpParams, true);
	    HttpConnectionParams.setSocketBufferSize(httpParams, DEFAULT_SOCKET_BUFFER_SIZE);

	    HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setUserAgent(httpParams,userAgent);
	    //默认参数
        HttpClientParams.setRedirecting(httpParams, false);
        HttpClientParams.setCookiePolicy(httpParams,
                CookiePolicy.BROWSER_COMPATIBILITY);
        httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
		return httpParams;
	      
    }
    
    /**
     * 获取HttpClient，自签名的证书，如果想做签名参考AuthSSLProtocolSocketFactory类
     * @return
     */
    public DefaultHttpClient getHttpClient(){
    	
    	if(mHttpClient != null){
    		return mHttpClient;
    	}else{
    		return createHttpClient();
    	}
    }
    
    /**
     * 获取HttpClient，自签名的证书，如果想做签名参考AuthSSLProtocolSocketFactory类
     * @param httpParams
     * @return
     */
    public DefaultHttpClient createHttpClient(){
    	BasicHttpParams httpParams = getHttpParams();
    	if(mIsOpenEasySSL){
    		 // 支持https的   SSL自签名的实现类
    	     EasySSLProtocolSocketFactory easySSLProtocolSocketFactory = new EasySSLProtocolSocketFactory();
             SchemeRegistry supportedSchemes = new SchemeRegistry();
             SocketFactory socketFactory = PlainSocketFactory.getSocketFactory();
             supportedSchemes.register(new Scheme("http", socketFactory, 80));
             supportedSchemes.register(new Scheme("https",easySSLProtocolSocketFactory, 443));
             //安全的ThreadSafeClientConnManager，否则不能用单例的HttpClient
             ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(
                     httpParams, supportedSchemes);
             //取得HttpClient ThreadSafeClientConnManager
             mHttpClient = new DefaultHttpClient(connectionManager, httpParams);
    	}else{
    		 //线程安全的HttpClient
    		 mHttpClient = new DefaultHttpClient(httpParams);
    	}
    	//自动重试
    	mHttpClient.setHttpRequestRetryHandler(mRequestRetryHandler);
    	mHttpClient.setCookieStore(mCookieStore);
 	    return mHttpClient;
    }

    /**
     * 是否打开ssl 自签名
     */
    public boolean isOpenEasySSL(){
        return mIsOpenEasySSL;
    }


    /**
     * 打开ssl 自签名
     * @param isOpenEasySSL
     */
    public void setOpenEasySSL(boolean isOpenEasySSL){
        this.mIsOpenEasySSL = isOpenEasySSL;
    }
    
    /**
     * 使用ResponseHandler接口处理响应,支持重定向
     */
    private class RedirectionResponseHandler implements ResponseHandler<String>{
        
    	private MicroHttpResponseListener mResponseListener = null;
    	private String mUrl = null;
    	
		public RedirectionResponseHandler(String url,
				MicroHttpResponseListener responseListener) {
			super();
			this.mUrl = url;
			this.mResponseListener = responseListener;
		}


		@Override
        public String handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException{
            HttpUriRequest request = (HttpUriRequest) mHttpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            //请求成功  
  			int statusCode = response.getStatusLine().getStatusCode();
  			HttpEntity entity = response.getEntity();
  			String responseBody = null;
            //200直接返回结果
            if (statusCode == HttpStatus.SC_OK) {
                
                // 不打算读取response body
                // 调用request的abort方法  
                // request.abort();  
                
                if (entity != null){
	  				  if(mResponseListener instanceof MicroStringHttpResponseListener){
	  					  //entity中的内容只能读取一次,否则Content has been consumed
	  					  //如果压缩要解压
	                      Header header = entity.getContentEncoding();
	                      if (header != null){
	                          String contentEncoding = header.getValue();
	                          if (contentEncoding != null){
	                              if (contentEncoding.contains("gzip")){
	                                  entity = new MicroGzipDecompressingEntity(entity);
	                              }
	                          }
	                      }
	                      String charset = EntityUtils.getContentCharSet(entity) == null ? encode : EntityUtils.getContentCharSet(entity);
	      	              responseBody = new String(EntityUtils.toByteArray(entity), charset);
	  					  
	      	              ((MicroStringHttpResponseListener)mResponseListener).sendSuccessMessage(statusCode, responseBody);
	  				  }else if(mResponseListener instanceof MicroBinaryHttpResponseListener){
	  					  responseBody = "Binary";
	  					  readResponseData(entity,((MicroBinaryHttpResponseListener)mResponseListener));
	  				  }else if(mResponseListener instanceof MicroFileHttpResponseListener){
	  					  //获取文件名
	  					  String fileName = F.getCacheFileNameFromUrl(mUrl, response);
	  					  writeResponseData(mContext,entity,fileName,((MicroFileHttpResponseListener)mResponseListener));
	  				  }
	      		      //资源释放!!!
	            	  try {
						  entity.consumeContent();
					  } catch (Exception e) {
						  e.printStackTrace();
					  }
	      			  return responseBody;
                }
                
            }
            //301 302进行重定向请求
            else if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY
                    || statusCode == HttpStatus.SC_MOVED_PERMANENTLY){
                // 从头中取出转向的地址
                Header locationHeader = response.getLastHeader("location");
                String location = locationHeader.getValue();
                if (request.getMethod().equalsIgnoreCase(HTTP_POST)){
                    doPost(location, null, mResponseListener);
                }
                else if (request.getMethod().equalsIgnoreCase(HTTP_GET)){
                    doGet(location, null, mResponseListener);
                }
            }else if(statusCode == HttpStatus.SC_NOT_FOUND){
            	//404
            	mResponseListener.sendFailureMessage(statusCode, MicroAppConfig.NOT_FOUND_EXCEPTION, new MicroAppException(MicroAppConfig.NOT_FOUND_EXCEPTION));
            }else if(statusCode == HttpStatus.SC_FORBIDDEN){
            	//403
            	mResponseListener.sendFailureMessage(statusCode, MicroAppConfig.FORBIDDEN_EXCEPTION, new MicroAppException(MicroAppConfig.FORBIDDEN_EXCEPTION));
            }else{
  				mResponseListener.sendFailureMessage(statusCode, MicroAppConfig.REMOTE_SERVICE_EXCEPTION, new MicroAppException(MicroAppConfig.REMOTE_SERVICE_EXCEPTION));
            }
            return null;
        }
    }
    
    /**
     * 自动重试处理
     */
    private HttpRequestRetryHandler mRequestRetryHandler = new HttpRequestRetryHandler(){
        
    	// 自定义的恢复策略
        public boolean retryRequest(IOException exception, int executionCount,
                HttpContext context){
            // 设置恢复策略，在发生异常时候将自动重试DEFAULT_MAX_RETRIES次
            if (executionCount >= DEFAULT_MAX_RETRIES){
                // 如果超过最大重试次数，那么就不要继续了
            	L.D( "超过最大重试次数，不重试");
                return false;
            }
            if (exception instanceof NoHttpResponseException){
                // 如果服务器丢掉了连接，那么就重试
            	L.D( "服务器丢掉了连接，重试");
                return true;
            }
            if (exception instanceof SSLHandshakeException){
                // SSL握手异常，不重试
            	L.D( "ssl 异常 不重试");
                return false;
            }
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
            if (!idempotent){
            	// 如果请求被认为是幂等的，那么就重试
            	L.D( "请求被认为是幂等的，重试");
                return true;
            }
            if (exception != null){
                return true;
            }
            return false;
        }
    };
    
	private String readString(InputStream is) {
		StringBuffer rst = new StringBuffer();
		
		byte[] buffer = new byte[1048576];
		int len = 0;
		
		try {
			while ((len = is.read(buffer)) > 0)
				for (int i = 0; i < len; ++i)
					rst.append((char)buffer[i]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rst.toString();
	}


    /**
     * 获取用户代理
     * @return
     */
    public String getUserAgent(){
        return userAgent;
    }


    /**
     * 设置用户代理
     * @param userAgent
     */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	
	/**
     * 获取编码
     * @return
     */
	public String getEncode() {
		return encode;
	}

	/**
	 * 设置编码
	 * @param encode
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}


	/**
	 * 关闭HttpClient
	 */
	public void shutdown(){
	    if(mHttpClient != null && mHttpClient.getConnectionManager() != null){
	    	mHttpClient.getConnectionManager().shutdown();
	    }
	}


	public CookieStore getCookieStore() {
		if(mHttpClient!=null){
			mCookieStore = mHttpClient.getCookieStore();
		}
		return mCookieStore;
	}


	public void setCookieStore(CookieStore cookieStore) {
		this.mCookieStore = cookieStore;
	}
	
}
