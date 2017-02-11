package com.m520it.jdmall.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtils {
	
	public static void startActivity(Context c,Class<? extends Activity> clazz,boolean ifFinishSlef){
		Intent intent=new Intent(c,clazz);
		c.startActivity(intent);
		if (ifFinishSlef) {
			((Activity)c).finish();
		}
	}
	
}
