package com.m520it.jdmall.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.m520it.jdmall.R;
import com.m520it.jdmall.listener.IAlipayClickListener;

public class AlipayPopWindow implements IPopWindow, OnClickListener{
	

	private View mContentView;
	private EditText mAccountEt;
	private EditText mPwdEt;
	private EditText mPayPwdEt;
	private PopupWindow mPw;
	private Button mCancelBtn;
	private View mPayBtn;
	private IAlipayClickListener mListener;
	
	public void setListener(IAlipayClickListener mListener) {
		this.mListener = mListener;
	}

	public AlipayPopWindow(Context c) {
		initView(c);
	}
	
	@Override
	public void onShow(View anchor) {
		if (!mPw.isShowing()) {
			mPw.showAtLocation(anchor, Gravity.CENTER, 0, 0);
		}
	}

	@Override
	public void dismiss() {
		if (mPw.isShowing()) {
			mPw.dismiss();
		}
	}

	@Override
	public void initView(Context c) {
		
		mContentView=LayoutInflater.from(c).inflate(R.layout.pay_dialog, null);
		mAccountEt=(EditText) mContentView.findViewById(R.id.account_et);
		mPwdEt=(EditText) mContentView.findViewById(R.id.pwd_et);
		mPayPwdEt=(EditText) mContentView.findViewById(R.id.pay_pwd_et);
		
		mCancelBtn=(Button)mContentView.findViewById(R.id.cancel_btn);
		mCancelBtn.setOnClickListener(this);
		mPayBtn=(Button)mContentView.findViewById(R.id.pay_btn);
		mPayBtn.setOnClickListener(this);
		mPw=new PopupWindow(mContentView,-1,-2);
		mPw.setFocusable(true);
		mPw.setOutsideTouchable(true);
		mPw.setBackgroundDrawable(new BitmapDrawable());
		mPw.update();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel_btn:
			dismiss();
			break;
		case R.id.pay_btn:
			startPay();
			break;
		}
	}

	private void startPay() {
		String account=mAccountEt.getText().toString();
		String pwd=mPwdEt.getText().toString();
		String payPwd=mPayPwdEt.getText().toString();
		if (!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(payPwd)) {
			if (mListener!=null) {
				mListener.startPay(account, pwd, payPwd);
			}
		}
		dismiss();
	}


	
}
