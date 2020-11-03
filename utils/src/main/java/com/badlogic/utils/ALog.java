package com.badlogic.utils;

import android.util.Log;

public class ALog {

	public static final boolean able = false;
	//-------
	public static final String Tag1 = "wjw01";
	public static final String Tag2 = "wjw02";
	public static final String Tag3 = "wjw03";
	public static final String Tag4 = "wjw04";
	public static final String Tag5 = "wjw05";
	public static final String Tag6 = "wjw06";
	public static final String Error = "error";

	public static void i(String string1, String string2) {
		if (able) {
			return;
		}
		Log.i(string1, string2);     //Green
	}

	public static void e(String string1, String string2) {
		if (able) {
			return;
		}
		Log.e(string1, string2);     //Red
	}

	public static void d(String string1, String string2) {
		if (able) {
			return;
		}
		Log.i(string1, string2);     //蓝色-blue
	}

	public static void v(String string1, String string2) {
		if (able) {
			return;
		}
		Log.i(string1, string2);     //黑色-black
	}

	public static void w(String string1, String string2) {
		if (able) {
			return;
		}
		Log.i(string1, string2);     //黄色-
	}

	public static void first(String string1, String string2) {
		if (able) {
			return;
		}
		Log.e(string1, string2);     //Green
	}

	public static void is(String arg1, String arg2, Object... params) {
		if (able) {
			return;
		}
		String content_str = "" + arg2;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				content_str = content_str + params[i];
			}
		}
		i(arg1, content_str);
	}

	public static void es(String arg1, String arg2, Object... params) {
		if (able) {
			return;
		}
		String content_str = "" + arg2;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				content_str = content_str + params[i];
			}
		}
		e(arg1, content_str);
	}

	public static void ds(String arg1, String arg2, Object... params) {
		if (able) {
			return;
		}
		String content_str = "" + arg2;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				content_str = content_str + params[i];
			}
		}
		d(arg1, content_str);
	}

	public static void vs(String arg1, String arg2, Object... params) {
		if (able) {
			return;
		}
		String content_str = "" + arg2;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				content_str = content_str + params[i];
			}
		}
		v(arg1, content_str);
	}

	public static void ws(String arg1, String arg2, Object... params) {
		if (able) {
			return;
		}
		String content_str = "" + arg2;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				content_str = content_str + params[i];
			}
		}
		w(arg1, content_str);

//       com.airbnb.lottie.LottieAnimationView   fff;

	}
}