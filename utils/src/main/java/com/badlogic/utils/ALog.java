package com.badlogic.utils;

import android.util.Log;

public class ALog {

	public static boolean mark = false;
	public static boolean i = false;
	public static boolean l = false;
	//-------
	public static final String Tag1 = "wjw01";
	public static final String Tag2 = "wjw02";
	public static final String Tag3 = "wjw03";
	public static final String Tag4 = "wjw04";
	public static final String Tag5 = "wjw05";
	public static final String Tag6 = "wjw06";
	public static final String Error = "error";

	///--------------------

	public interface IfLog {
		String log();
	}

	public  static  void  i(IfLog hILog) {
		if (mark && hILog != null) {
			Log.i(Tag2, hILog.log());     //Green
		}
	}
	public  static  void  e(IfLog hILog) {
		if (mark && hILog != null) {
			Log.e(Tag2, hILog.log());     //Red
		}
	}
	public  static  void  d(IfLog hILog) {
		if (mark && hILog != null) {
			Log.d(Tag2, hILog.log());     //蓝色-blue
		}
	}
	public  static  void  v(IfLog hILog) {
		if (mark && hILog != null) {
			Log.v(Tag2, hILog.log());     //黑色-black
		}
	}
	public  static  void  w(IfLog hILog) {
		if (mark && hILog != null) {
			Log.w(Tag2, hILog.log());     //黄色-
		}
	}

	public  static  void  i(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.i(string1, hILog.log());     //Green
		}
	}
	public  static  void  e(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.e(string1, hILog.log());     //Red
		}
	}
	public  static  void  d(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.d(string1, hILog.log());     //蓝色-blue
		}
	}
	public  static  void  v(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.v(string1, hILog.log());     //黑色-black
		}
	}
	public  static  void  w(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.w(string1, hILog.log());     //黄色-
		}
	}
	public  static  void  first(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.e(string1, hILog.log());     //Green
		}
	}

	public  static  void  i(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.i(hILog1.log(), hILog.log());     //Green
		}
	}
	public  static  void  e(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.e(hILog1.log(), hILog.log());     //Red
		}
	}
	public  static  void  d(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.d(hILog1.log(), hILog.log());     //蓝色-blue
		}
	}
	public  static  void  v(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.v(hILog1.log(), hILog.log());     //黑色-black
		}
	}
	public  static  void  w(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.w(hILog1.log(), hILog.log());     //黄色-
		}
	}
	public  static  void  first(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.e(hILog1.log(), hILog.log());     //Green
		}
	}

	///--------------------

	public  static  void  i(String i){
		if (mark) {
			Log.i(Tag2, i);     //Green
		}
	}
	public  static  void  e(String string2){
		if (mark) {
			Log.e(Tag2, string2);     //Red
		}
	}
	public  static  void  d(String string2){
		if (mark) {
			Log.d(Tag2, string2);     //蓝色-blue
		}
	}
	public  static  void  v(String string2){
		if (mark) {
			Log.v(Tag2, string2);     //黑色-black
		}
	}
	public  static  void  w(String string2){
		if (mark) {
			Log.w(Tag2, string2);     //黄色-
		}
	}

	public  static  void  i(String string1,String string2){
		if (mark) {
			Log.i(string1, string2);     //Green
		}
	}
	public  static  void  e(String string1,String string2){
		if (mark) {
			Log.e(string1, string2);     //Red
		}
	}
	public  static  void  d(String string1,String string2){
		if (mark) {
			Log.d(string1, string2);     //蓝色-blue
		}
	}
	public  static  void  v(String string1,String string2){
		if (mark) {
			Log.v(string1, string2);     //黑色-black
		}
	}
	public  static  void  w(String string1,String string2){
		if (mark) {
			Log.w(string1, string2);     //黄色-
		}
	}
	public  static  void  first(String string1,String string2){
		if (mark) {
			Log.e(string1, string2);     //Green
		}
	}

	public  static  void  is(String arg1,String arg2,Object... params){
		String content_str="" + arg2;
		if(params!=null){
			for(int i=0;i<params.length;i++){
				content_str=content_str+params[i];
			}
		}
		i(arg1,content_str);
	}

	public  static  void  es(String arg1,String arg2,Object... params){
		String content_str="" + arg2;
		if(params!=null){
			for(int i=0;i<params.length;i++){
				content_str=content_str+params[i];
			}
		}
		e(arg1,content_str);
	}

	public  static  void  ds(String arg1,String arg2,Object... params){
		String content_str="" + arg2;
		if(params!=null){
			for(int i=0;i<params.length;i++){
				content_str=content_str+params[i];
			}
		}
		d(arg1,content_str);
	}

	public  static  void  vs(String arg1,String arg2,Object... params){
		String content_str="" + arg2;
		if(params!=null){
			for(int i=0;i<params.length;i++){
				content_str=content_str+params[i];
			}
		}
		v(arg1,content_str);
	}

	public  static  void  ws(String arg1,String arg2,Object... params){
		String content_str="" + arg2;
		if(params!=null){
			for(int i=0;i<params.length;i++){
				content_str=content_str+params[i];
			}
		}
		w(arg1,content_str);
	}
}