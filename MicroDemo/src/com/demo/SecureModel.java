package com.demo;

import android.content.Context;

import com.micro.utils.L;
import com.micro.utils.M;



/**
 * 
 * @author sufun
 * 2015年2月9日 14:53:52
 */
public class SecureModel  {

	private Context mContext ;
	/**
	 * 时间撮
	 */
	public String TimesTamp="";
	/**
	 * 加密结果
	 */
	public String Sig="";
	/**
	 * 登陆状态
	 */
	public String Login_Code_Sig = "";
	public SecureModel()
	{
		
	}
	
	public SecureModel getInstance()
	{
		String timetamp=System.currentTimeMillis()/1000+"";
		this.TimesTamp=timetamp;
		this.Sig=M.getMd5Value(Constant.appSDK+timetamp).toLowerCase();
		return this;
	}
	/**
	 * 取得带得防冲突登陆的Sig值
	 * @return
	 */
	public SecureModel getSecureLoginInstance()
	{
		String timetamp=System.currentTimeMillis()/1000+"";		
		this.TimesTamp=timetamp;
		
		LocalUserModel user=new LocalUserModel();
		
		
		String login_state=user.getLogin_State();
		
		L.I("loginState  "+login_state);
		
		if(LocalUserModel.LONGIN_STATE_ONLINE.equals(login_state))
		{
			this.Login_Code_Sig=M.getMd5Value(Constant.appSDK+timetamp+user.getLoginCode()).toLowerCase();
		}
		else
		{
			this.Login_Code_Sig=M.getMd5Value(Constant.appSDK+timetamp).toLowerCase();
		}
		return this;
	}
	
}
