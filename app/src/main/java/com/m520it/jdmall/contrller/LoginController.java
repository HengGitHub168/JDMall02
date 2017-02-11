package com.m520it.jdmall.contrller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.bean.UserBean;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.db.UserDao;
import com.m520it.jdmall.util.NetworkUtil;

public class LoginController extends BaseController {

	public LoginController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
		case IDiyMessage.ACTION_LOGIN:
			RResult loginResult = login((String)values[0],(String)values[1]);
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_LOGIN_RESULT, loginResult);
			break;
		case IDiyMessage.ACTION_REGIST:
			RResult registResult = regist((String)values[0],(String)values[1]);
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_LOGIN_RESULT, registResult);
			break;
		case IDiyMessage.ACTION_RESET_PASSWORD:
			RResult resetResult = resetPassword((String)values[0]);
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_RESET_PASSWORD_RESULT, resetResult);
			break;
		case IDiyMessage.ACTION_GET_REMEMBER_USER:
			mModelChangeListener.onModelChanged(
					IDiyMessage.ACTION_GET_REMEMBER_USER_RESULT, getRememberUser());
			break;
		case IDiyMessage.ACTION_DELETE_REMEMBER_USER:
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_DELETE_REMEMBER_USER_RESULT, deleteRememberUser());
			break;
			
		}
	}

	private boolean deleteRememberUser() {
		UserDao dao=new UserDao(mContext);
		return dao.deleteUsers();
	}

	private UserBean getRememberUser() {
		UserDao dao=new UserDao(mContext);
		return dao.getRecentLoginUser();
	}

	private RResult resetPassword(String username) {
		String jsonStr = NetworkUtil.doGet(NetworkConst.RESET+"username="+username);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	private RResult login(String username,String pwd) {
		String jsonStr = NetworkUtil.doGet(NetworkConst.LOGIN+"username="+username+"&pwd="+pwd);
		System.out.println(jsonStr);
		return JSON.parseObject(jsonStr,RResult.class);
	}
	
	private RResult regist(String username,String pwd) {
		String jsonStr = NetworkUtil.doGet(NetworkConst.REGIST+"username="+username+"&pwd="+pwd);
		return JSON.parseObject(jsonStr,RResult.class);
	}

}
