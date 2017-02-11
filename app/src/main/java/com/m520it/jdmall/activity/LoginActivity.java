package com.m520it.jdmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.JDApplication;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RLogin;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.bean.UserBean;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.contrller.LoginController;
import com.m520it.jdmall.db.UserDao;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.util.AESUtils;
import com.m520it.jdmall.util.ActivityUtils;

public class LoginActivity extends BaseActivity implements IModelChangeListener{
	
	private EditText mNameEt;
	private EditText mPwdEt;
	private LoginController mController;
	
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_LOGIN_RESULT:
				handleLoginResult((RResult) msg.obj);
				break;
			case IDiyMessage.ACTION_GET_REMEMBER_USER_RESULT:
				handleLoadingRememberUser(msg.obj);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mNameEt=(EditText) findViewById(R.id.name_et);
		mPwdEt=(EditText) findViewById(R.id.pwd_et);
		mController=new LoginController(this);
		mController.setIModelChangeListener(this);
		
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_REMEMBER_USER, 0);
	}
	
	protected void handleLoadingRememberUser(Object obj) {
		if (obj!=null&&obj instanceof UserBean) {
			try {
				UserBean bean=(UserBean) obj;
				mNameEt.setText(bean.getName());
				mPwdEt.setText(AESUtils.decrypt(bean.getPwd()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void handleLoginResult(RResult result) {
		if (!result.isSuccess()) {
			Toast.makeText(this, result.getErrorMsg(), 800).show();
		}else {
			RLogin userInfo=JSON.parseObject(result.getResult(),RLogin.class);
			rememberNameAndPwd();
			((JDApplication)getApplication()).mUserInfo=userInfo;
			ActivityUtils.startActivity(this, MainActivity.class,true);
		}
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
	}

	private void rememberNameAndPwd() {
		new Thread(){
			public void run() {
				try {
					String name=mNameEt.getText().toString();
					String pwd=mPwdEt.getText().toString();
					UserDao dao=new UserDao(LoginActivity.this);
					dao.saveUser(name, AESUtils.encrypt(pwd));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void loginClick(View view){
		String name=mNameEt.getText().toString();
		String pwd=mPwdEt.getText().toString();
		if (name==null||pwd==null) {
			Toast.makeText(this, "账号密码不能为空!", 800).show();
			return;
		}
		mController.sendAsyncMessage(IDiyMessage.ACTION_LOGIN,name,pwd);
		mLodingDailog.show();
	}
	
	public void registClick(View view){
		ActivityUtils.startActivity(this, RegistActivity.class, false);
	}
	
	public void resetPwdClick(View view){
		ActivityUtils.startActivity(this, ResetActivity.class, false);
	}
	
	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
	
}
