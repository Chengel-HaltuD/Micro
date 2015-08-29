package com.widget.dialog;


import com.demo.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * 
 * @ClassName: P
 * @Description: 进度加载条
 * @Author：Chengel_HaltuD
 * @Date：2015-7-3 上午11:44:32
 * @version V1.0
 *
 */
public class P extends Dialog {

	/**
	 * 加载进行的View
	 */
	private View OperationIngView;
	/**
	 * 加载成功的View
	 */
	private View OperationSuccessView;
	/**
	 * 网络问题的View
	 */
	private View OperationNetErrorView;
	/**
	 * 加载失败的View
	 */
	private View OperationFailView;
	/**
	 * 加载错误的View
	 */
	private View OperationErrorView;

	private Context context;
	private ISFProcessDialogDelegate delegate;
	public ISFProcessDialogDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(ISFProcessDialogDelegate delegate) {
		this.delegate = delegate;
	}

	public P(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initView();
	}

	public P(Context context, int theme) {
		super(context, R.style.dialog_progress);
		this.context = context;
		initView();
		setCanceledOnTouchOutside(false);
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		doCreateOperationIngView();
		doCreateOperationSuccessView();
		doCreateOperationNetErrorView();
		doCreateOperationFailView();
		doCreateOperationErrorView();
		
	}

	/**
	 * 消除加载的对话框
	 */
	public void DissmissDialog() {

	}


	/** 正在执行  **/
	public void OperationIng(String content) {
		try {
			TextView txt_content = (TextView) OperationIngView
					.findViewById(R.id.operation_view_txt_content);
			txt_content.setText(content);
			setContentView(OperationIngView);
			show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/** 数据加载成功  **/
	public void OperationSuccess(String content) {
		try {
			TextView txt_content = (TextView) OperationSuccessView
					.findViewById(R.id.operation_view_txt_content);
			txt_content.setText(content+"");
			setContentView(OperationSuccessView);
			show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dismiss();
				}
			}).start();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/** 网络出现问题  **/
	public void OperationNetError(String content) {
		try {
			TextView txt_content = (TextView) OperationNetErrorView
					.findViewById(R.id.operation_view_txt_content);
			txt_content.setText(content);
			setContentView(OperationNetErrorView);
			show();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/** 数据加载失败  **/
	public void OperationFail(String content) {
		try {
			TextView txt_content = (TextView) OperationFailView
					.findViewById(R.id.operation_view_txt_content);
			txt_content.setText(content+"");
			setContentView(OperationFailView);
			show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/** 数据加载错误  **/
	public void OperationError(String content) {
		try {
			TextView txt_content = (TextView) OperationErrorView
					.findViewById(R.id.operation_view_txt_content);
			txt_content.setText(content+"");
			setContentView(OperationErrorView);
			show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	/** 创建加载中的View **/
	private View doCreateOperationIngView() {
		OperationIngView = LayoutInflater.from(context).inflate(
				R.layout.dialog_layout_operation_view, null);
		ProgressBar progressBar = (ProgressBar) OperationIngView
				.findViewById(R.id.operation_view_progress_bar);
		progressBar.setVisibility(View.VISIBLE);
		ImageView image = (ImageView) OperationIngView
				.findViewById(R.id.operation_view_icon_operation);
		image.setImageResource(R.drawable.ic_load_success);
		image.setVisibility(View.GONE);
		return OperationIngView;
	}

	/** 创建加载成功View **/
	private View doCreateOperationSuccessView() {
		OperationSuccessView = LayoutInflater.from(context).inflate(
				R.layout.dialog_layout_operation_view, null);
		ProgressBar progressBar = (ProgressBar) OperationSuccessView
				.findViewById(R.id.operation_view_progress_bar);
		progressBar.setVisibility(View.GONE);
		ImageView image = (ImageView) OperationSuccessView
				.findViewById(R.id.operation_view_icon_operation);
		image.setImageResource(R.drawable.ic_load_success);
		image.setVisibility(View.VISIBLE);
		TextView txt_content = (TextView) OperationSuccessView
				.findViewById(R.id.operation_view_txt_content);
		txt_content.setText("加载成功");
		return OperationSuccessView;
	}

	/** 创建网络错误View **/
	private View doCreateOperationNetErrorView() {
		OperationNetErrorView = LayoutInflater.from(context).inflate(
				R.layout.dialog_layout_operation_view, null);
		ProgressBar progressBar = (ProgressBar) OperationNetErrorView
				.findViewById(R.id.operation_view_progress_bar);
		progressBar.setVisibility(View.GONE);
		ImageView image = (ImageView) OperationNetErrorView
				.findViewById(R.id.operation_view_icon_operation);
		image.setImageResource(R.drawable.ic_load_sigh);
		image.setVisibility(View.VISIBLE);
		TextView txt_content = (TextView) OperationNetErrorView
				.findViewById(R.id.operation_view_txt_content);
		txt_content.setText("无法连接网络");
		return OperationNetErrorView;
	}

	/** 创建加载失败View **/
	private View doCreateOperationFailView() {
		OperationFailView = LayoutInflater.from(context).inflate(
				R.layout.dialog_layout_operation_view, null);
		ProgressBar progressBar = (ProgressBar) OperationFailView
				.findViewById(R.id.operation_view_progress_bar);
		progressBar.setVisibility(View.GONE);
		ImageView image = (ImageView) OperationFailView
				.findViewById(R.id.operation_view_icon_operation);
		image.setImageResource(R.drawable.ic_load_notopen);
		image.setVisibility(View.VISIBLE);
		TextView txt_content = (TextView) OperationFailView
				.findViewById(R.id.operation_view_txt_content);
		txt_content.setText("数据请求失败");
		return OperationFailView;
	}

	/** 创建加载错误View **/
	public View doCreateOperationErrorView() {
		OperationErrorView = LayoutInflater.from(context).inflate(
				R.layout.dialog_layout_operation_view, null);
		ProgressBar progressBar = (ProgressBar) OperationErrorView
				.findViewById(R.id.operation_view_progress_bar);
		progressBar.setVisibility(View.GONE);
		ImageView image = (ImageView) OperationErrorView
				.findViewById(R.id.operation_view_icon_operation);
		TextView txt_content = (TextView) OperationErrorView
				.findViewById(R.id.operation_view_txt_content);
		image.setImageResource(R.drawable.ic_load_failure);
		image.setVisibility(View.VISIBLE);
		txt_content.setText("数据请求错误");
		return OperationErrorView;
	}

	public interface ISFProcessDialogDelegate {
		void OnReload();

		void OnCancel();
	}

}
