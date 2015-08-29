package com.demo;
/*package com.aaaaa.base;

import com.jryg.guide.R;
import com.jryg.guide.views.SFProcessDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public abstract class BaseFragment extends Fragment implements IFrameAction {

	*//**
	 * fragment跳转控制器
	 *//*
	public IFrameAction actionHandler;

	*//**
	 * 加载对话框
	 *//*
	public SFProcessDialog sfDialog;

	public TextView txt_title;
	public View view;
	public Context ct;
	public boolean isLoadSuccessData = false;
	public TextView tv_right;
	public ImageButton Btn_left ;
	public CheckBox imgbtn_right;
	TabHost tabHost;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);

	}

	*//**
	 * 初始化标题头
	 *//*
	public void initTitleView() {
		if (view != null) {
			txt_title = (TextView) F(R.id.txt_title);
			Btn_left = (ImageButton) F(R.id.Btn_left);
			imgbtn_right = (CheckBox) F(R.id.imgbtn_right);
		} 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ct = getActivity();
		sfDialog = new SFProcessDialog(getActivity(), 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = initView(inflater);
		return view;
	}

	public String TStr(int resId) {
		return this.getResources().getString(resId);
	}

	public View F(int id) {
		return view.findViewById(id);
	}

	public abstract View initView(LayoutInflater inflater);

	public abstract void initData(Bundle savedInstanceState);

	public void switchFragment(int res, Fragment fragment) {
		getFragmentManager().beginTransaction().replace(res, fragment).commit();
	}

	public String S(int id) {
		return ct.getResources().getString(id);
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub
		if (actionHandler != null) {
			actionHandler.goBack();
		}
	}

	@Override
	public void goPrevious() {
		// TODO Auto-generated method stub
		if (actionHandler != null) {
			actionHandler.goPrevious();
		}
	}

	*//**
	 * 向用户Toast对应的资源
	 * 
	 * @param resId
	 *//*
	public void T(int resId) {
		Toast.makeText(getActivity(),
				getActivity().getResources().getString(resId), 1000).show();
	}

	*//**
	 * 向用户返回对应的文本资源Toast
	 * 
	 * @param str
	 *//*
	public void T(String str) {
		Toast.makeText(getActivity(), str, 1000).show();
	}

	*//**
	 * 关闭软件键盘
	 *//*
	public void doHideSoftkey() {
		View view = getActivity().getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getActivity()
					.getSystemService(getActivity().INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

}
*/