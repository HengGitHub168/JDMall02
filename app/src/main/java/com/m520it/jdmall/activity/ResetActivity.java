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

public class ResetActivity extends BaseActivity implements IModelChangeListener {
	
	private EditText mUsernameEt;
	private LoginController mController;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_RESET_PASSWORD_RESULT:
				handleResetResult((RResult) msg.obj);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset);
		mController=new LoginController(this);
		mController.setIModelChangeListener(this);
		mUsernameEt=(EditText) findViewById(R.id.username_et);
	}
	
	protected void handleResetResult(RResult result) {
		if (!result.isSuccess()) {
			Toast.makeText(this, result.getErrorMsg(), 800).show();
			return;
		}
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
		Toast.makeText(this, "密码重置为123456!", 800).show();
		ActivityUtils.startActivity(this, LoginActivity.class, true);
	}

	public void resetClick(View view){
		String username = mUsernameEt.getText().toString();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, "请输入完整信息", 800).show();
			return ;
		}
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_RESET_PASSWORD, username);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
	
}
