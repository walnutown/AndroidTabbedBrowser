package com.taohu.android.browser;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class AppSettings {
	public final static String USER_PROFILE = "user_profile";
	// develop server, localhost
	public final static String HOST_DEV = "http://bitium.192.168.1.44.xip.io";
	// product server
	public final static String HOST_PRODUCTION = "https://www.bitium.com";
	
	public final static String USER_SUBSCRIPTION = "user_subscription";
	
	public final static long WEB_CACHE_MAX_SIZE = 1024*1024*8;
	
	public final static String TAB_TITLE = "tab_title";
	public final static String TAB_HAS_LOADED = "tab_has_loaded";
	
	/*
	 * Select host server
	 */
	public static String getHostServer() {
		//return HOST_DEV;
		 return HOST_PRODUCTION;
	}
	
	/*
	 * convert dp to px
	 */
	public static int dpToPx(int dp, Context ctx) {
		Resources r = ctx.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
	}
}
