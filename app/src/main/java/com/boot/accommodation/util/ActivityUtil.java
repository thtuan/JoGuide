package com.boot.accommodation.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boot.accommodation.JoCoApplication;

public class ActivityUtil {
	
	public static void switchActivity(Intent data) {
		ActivityUtil.switchActivity(JoCoApplication.getInstance().getAppContext(), data);
	}
	
	public static void switchActivity(Class<?> activityClass) {
		ActivityUtil.switchActivity(JoCoApplication.getInstance().getAppContext(), activityClass);
	}
	
	public static void switchActivity(Class<?> activityClass, Bundle data) {
		ActivityUtil.switchActivity(JoCoApplication.getInstance().getAppContext(), activityClass, data);
	}
	
	
	public static void switchActivity(Context con, Class<?> activityClass) {
		Intent intent = new Intent(con, activityClass);
		ActivityUtil.switchActivity(con, intent);
	}
	
	public static void switchActivity(Context con, Intent intent) {
		con.startActivity(intent);
	}
	
	public static void switchActivity(Context con, Class<?> activityClass, Bundle data) {
		Intent intent = new Intent(con, activityClass);
		intent.putExtras(data);
		ActivityUtil.switchActivity(intent);
	}
}
