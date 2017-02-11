package com.m520it.jdmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.util.ActivityUtils;

/**
 * 启动界面
 */
public class SplashActivity extends BaseActivity {

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_START_MAIN_AC:
				ActivityUtils.startActivity(SplashActivity.this,
						MainActivity.class, true);
				break;
			case IDiyMessage.ACTION_START_LOGIN_AC:
				ActivityUtils.startActivity(SplashActivity.this,
						LoginActivity.class, true);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		alphaAnim();
		startApp();
	}

	private void startApp() {
		new Thread() {
			public void run() {
				SystemClock.sleep(2000);
				mHandler.sendEmptyMessage(IDiyMessage.ACTION_START_LOGIN_AC);
			}
		}.start();
	}

	private void alphaAnim() {
		Animation alphaAnim = new AlphaAnimation(0.2f, 1.0f);
		alphaAnim.setDuration(2000);
		alphaAnim.setFillAfter(true);
		ImageView logoIv = (ImageView) findViewById(R.id.logo_iv);
		logoIv.startAnimation(alphaAnim);
	}
}
