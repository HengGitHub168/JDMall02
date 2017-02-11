
package com.m520it.jdmall.frag;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.jdmall.JDApplication;
import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.LoginActivity;
import com.m520it.jdmall.activity.OrderListActivity;
import com.m520it.jdmall.bean.RLogin;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.contrller.LoginController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.util.ActivityUtils;

/**
 * 主页-“我的”页
 */
public class MimeFragment extends BaseFragment implements OnClickListener, IModelChangeListener {

	private ImageView mUserIconIv;
	private TextView mUserNameTv;
	private TextView mUserLevel;

	private TextView mWaitPayTv;
	private TextView mWaitReceiveTv;
	private LinearLayout mAllOrderLl;
	private Button mLogoutBtn;
	private LinearLayout mWaitPayLl;
	private LinearLayout mWaitReceiveLl;
	private LoginController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			handleLogout(msg);
		}
		
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mine, null, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mController=new LoginController(getActivity());
		mController.setIModelChangeListener(this);
		mUserIconIv = (ImageView) getActivity().findViewById(R.id.user_icon_iv);
		mUserNameTv = (TextView) getActivity().findViewById(R.id.user_name_tv);
		mUserLevel = (TextView) getActivity().findViewById(R.id.user_level_tv);
		mWaitPayTv = (TextView) getActivity().findViewById(R.id.wait_pay_tv);
		mWaitReceiveTv = (TextView) getActivity().findViewById(R.id.wait_receive_tv);
		mWaitPayLl = (LinearLayout) getActivity().findViewById(R.id.wait_pay_ll);
		mWaitPayLl.setOnClickListener(this);
		mWaitReceiveLl = (LinearLayout) getActivity().findViewById(R.id.wait_receive_ll);
		mWaitReceiveLl.setOnClickListener(this);
		
		mAllOrderLl = (LinearLayout) getActivity().findViewById(R.id.mime_order);
		mAllOrderLl.setOnClickListener(this);
		initUserInfo();
		mLogoutBtn=(Button)getActivity().findViewById(R.id.logout_btn);
		mLogoutBtn.setOnClickListener(this);
	}

	private void initUserInfo() {
		RLogin userInfo = ((JDApplication) getActivity().getApplication()).mUserInfo;
		mUserNameTv.setText(userInfo.getUserName());
		switch (userInfo.getUserLevel()) {
			case 2:
				mUserLevel.setText("铜牌会员");
				break;
			case 3:
				mUserLevel.setText("银牌会员");
				break;
			case 4:
				mUserLevel.setText("金牌会员");
				break;
			case 5:
				mUserLevel.setText("钻石会员");
				break;
			case 1:
			default:
				mUserLevel.setText("注册会员");
				break;
		}
		mWaitPayTv.setText(userInfo.getWaitPayCount()+"");
		mWaitReceiveTv.setText(userInfo.getWaitReceiveCount()+"");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mime_order:
			case R.id.wait_pay_ll:
			case R.id.wait_receive_ll:
				ActivityUtils.startActivity(getActivity(),OrderListActivity.class, false);
				break;
			case R.id.logout_btn:
				mController.sendAsyncMessage(IDiyMessage.ACTION_DELETE_REMEMBER_USER, 0);
				break;
		}
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	private void handleLogout(android.os.Message msg) {
		if (msg.what==IDiyMessage.ACTION_DELETE_REMEMBER_USER_RESULT) {
			if ((Boolean) msg.obj) {
				ActivityUtils.startActivity(getActivity(), LoginActivity.class
						, true);
			}
		}
	}
	
}
