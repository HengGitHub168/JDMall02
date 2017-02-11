package com.m520it.jdmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.contrller.LoginController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.util.ActivityUtils;

public class RegistActivity extends BaseActivity implements IModelChangeListener {
	
	private EditText mUsernameEt;
	private EditText mPwdEt;
	private EditText mSurePwdEt;
	private LoginController mController;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_LOGIN_RESULT:
				handleRegistResult((RResult) msg.obj);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		
		mController=new LoginController(this);
		mController.setIModelChangeListener(this);
		
		mUsernameEt=(EditText) findViewById(R.id.username_et);
		mPwdEt=(EditText) findViewById(R.id.pwd_et);
		mSurePwdEt=(EditText) findViewById(R.id.surepwd_et);
		
	}
	
	protected void handleRegistResult(RResult result) {
		if (!result.isSuccess()) {
			Toast.makeText(this, result.getErrorMsg(), 800).show();
			return;
		}
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
		Toast.makeText(this, "恭喜您 注册成功!", 800).show();
		ActivityUtils.startActivity(this, LoginActivity.class, true);
	}

	public void registClick(View view){
		String username = mUsernameEt.getText().toString();
		String pwd = mPwdEt.getText().toString();
		String surepwd = mSurePwdEt.getText().toString();
		if (TextUtils.isEmpty(username)||
				TextUtils.isEmpty(pwd)||TextUtils.isEmpty(surepwd)) {
			Toast.makeText(this, "请输入完整信息", 800).show();
			return ;
		}else if (!pwd.equals(surepwd)) {
			Toast.makeText(this, "重复密码输入不正确", 800).show();
			return ;
		}
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_REGIST, username,pwd);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
	
}
