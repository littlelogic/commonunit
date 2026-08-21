package com.badlogic.utils;

import android.content.Context;
import android.util.Log;

public class ALog {

	public static boolean mark = false;
	public static boolean i = false;
	public static boolean l = false;
	public static String d = null;
	public static String r = null;

	public static final String _b = "";
	public static boolean _i = true;
	public static boolean _1 = true;
	public static boolean _$ = true;
	public static boolean _￥ = true;
	//-------
	public static final String Tag1 = "wjw01";
	public static final String Tag2 = "wjw02";
	public static final String Tag3 = "wjw03";
	public static final String Tag4 = "wjw04";
	public static final String Tag5 = "wjw05";
	public static final String Tag6 = "wjw06";
	public static final String Error = "error";


	///--------------------

	private static LogSocketIntf logSocketIntf;

	public static void setMark(boolean mark_){
		mark = mark_;
		l = i = _i = _1 = _$ = _￥ = mark;
		if (mark_) {
			d = "";
			r = null;
		} else {
			d = null;
			r = "";
		}

        try {
            Class<?> holderClass = Class.forName("com.badlogic.socket.LogWebSocketHelper$SingletonHolder");
            java.lang.reflect.Field field = holderClass.getDeclaredField("instance");
            field.setAccessible(true);
            logSocketIntf = (LogSocketIntf) field.get(null);
        } catch (Exception e) {
			e.printStackTrace();
        }

    }


	private static Context appContext;
	private static boolean logWebSocketMark = false;

	public static void setLogWebSocket(Context context){
		logWebSocketMark = true;
		appContext = context.getApplicationContext();
		if (logSocketIntf != null) {
			logSocketIntf.launch(context);
		}
	}

	public static void setLogWebSocket(Context context, int port_out){
		logWebSocketMark = true;
		appContext = context.getApplicationContext();
		if (logSocketIntf != null) {
			logSocketIntf.launch(context,port_out);
		}
	}

	private static void sendLogToWebSocket(String text){
		if (logWebSocketMark && appContext != null) {
			if (logSocketIntf != null) {
				logSocketIntf.sendLog(text);
			}
		}
	}

	private static void sendLogToWebSocket(String level, String tag, String message){
		if (logWebSocketMark && appContext != null) {
			if (logSocketIntf != null) {
				logSocketIntf.sendStructuredLog(level,tag,message);
			}
		}
	}

	///--------------------

	private void test(){
		ALog.i(_i?_b: "210206t-MainActivity-onCreate-1111->" +
				"-test2->" + 2222222 + "\n"+
				"-test2->" + 3333333 +
				"");

		ALog.i(_i?_b: "210206t-MainActivity-onCreate-1111-->" +
				"-test2->" + Object.class + "\n"+
				"-test2->" + 2222222 + "\n"+
				"-test2->" + 3333333 +
				"");
	}

	///--------------------

	public interface RunLog {
		String log();
	}

	public  static  void  ii(Runnable run) {
		if (mark && run != null) {
//			Log.i(Tag2, hIrunLog.log());     //Green
		}
	}

	public  static  void  i2(RunLog run) {
		if (mark && run != null) {
//			Log.i(Tag2, hIrunLog.log());     //Green
		}
	}


	///--------------------

	public interface IfLog {
		String log();
	}

	public  static  void  i(IfLog hILog) {
		if (mark && hILog != null) {
			Log.i(Tag2, hILog.log());     //Green
			sendLogToWebSocket(Tag2,"INFO",hILog.log());
		}
	}
	public  static  void  e(IfLog hILog) {
		if (mark && hILog != null) {
			Log.e(Tag2, hILog.log());     //Red
			sendLogToWebSocket(Tag2,"ERROR",hILog.log());
		}
	}
	public  static  void  d(IfLog hILog) {
		if (mark && hILog != null) {
			Log.d(Tag2, hILog.log());     //蓝色-blue
			sendLogToWebSocket(Tag2,"DEBUG",hILog.log());
		}
	}
	public  static  void  v(IfLog hILog) {
		if (mark && hILog != null) {
			Log.v(Tag2, hILog.log());     //黑色-black
			sendLogToWebSocket(Tag2,"INFO",hILog.log());
		}
	}
	public  static  void  w(IfLog hILog) {
		if (mark && hILog != null) {
			Log.w(Tag2, hILog.log());     //黄色-
			sendLogToWebSocket(Tag2,"WARN",hILog.log());
		}
	}

	public  static  void  i(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.i(string1, hILog.log());     //Green
			sendLogToWebSocket(string1,"INFO",hILog.log());
		}
	}
	public  static  void  e(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.e(string1, hILog.log());     //Red
			sendLogToWebSocket(string1,"ERROR",hILog.log());
		}
	}
	public  static  void  d(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.d(string1, hILog.log());     //蓝色-blue
			sendLogToWebSocket(string1,"DEBUG",hILog.log());
		}
	}
	public  static  void  v(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.v(string1, hILog.log());     //黑色-black
			sendLogToWebSocket(string1,"INFO",hILog.log());
		}
	}
	public  static  void  w(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.w(string1, hILog.log());     //黄色-
			sendLogToWebSocket(string1,"WARN",hILog.log());
		}
	}
	public  static  void  first(String string1,IfLog hILog){
		if (mark && hILog != null) {
			Log.e(string1, hILog.log());     //Green
			sendLogToWebSocket(string1,"ERROR",hILog.log());
		}
	}

	public  static  void  i(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.i(hILog1.log(), hILog.log());     //Green
			sendLogToWebSocket(hILog1.log(),"INFO",hILog.log());
		}
	}
	public  static  void  e(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.e(hILog1.log(), hILog.log());     //Red
			sendLogToWebSocket(hILog1.log(),"ERROR",hILog.log());
		}
	}
	public  static  void  d(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.d(hILog1.log(), hILog.log());     //蓝色-blue
			sendLogToWebSocket(hILog1.log(),"DEBUG",hILog.log());
		}
	}
	public  static  void  v(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.v(hILog1.log(), hILog.log());     //黑色-black
			sendLogToWebSocket(hILog1.log(),"INFO",hILog.log());
		}
	}
	public  static  void  w(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.w(hILog1.log(), hILog.log());     //黄色-
			sendLogToWebSocket(hILog1.log(),"WARN",hILog.log());
		}
	}
	public  static  void  first(IfLog hILog1,IfLog hILog){
		if (mark && hILog != null && hILog1 != null) {
			Log.e(hILog1.log(), hILog.log());     //Green
			sendLogToWebSocket(hILog1.log(),"ERROR",hILog.log());
		}
	}

	///--------------------

	public  static  void  i(String i){
		if (mark) {
			Log.i(Tag2, i);     //Green
			sendLogToWebSocket(Tag2,"INFO",i);
		}
	}
	public  static  void  e(String string2){
		if (mark) {
			Log.e(Tag2, string2);     //Red
			sendLogToWebSocket(Tag2,"ERROR",string2);
		}
	}
	public  static  void  d(String string2){
		if (mark) {
			Log.d(Tag2, string2);     //蓝色-blue
			sendLogToWebSocket(Tag2,"DEBUG",string2);
		}
	}
	public  static  void  v(String string2){
		if (mark) {
			Log.v(Tag2, string2);     //黑色-black
			sendLogToWebSocket(Tag2,"INFO",string2);
		}
	}
	public  static  void  w(String string2){
		if (mark) {
			Log.w(Tag2, string2);     //黄色-
			sendLogToWebSocket(Tag2,"WARN",string2);
		}
	}

	public  static  void  i(String string1,String string2){
		if (mark) {
			Log.i(string1, string2);     //Green
			sendLogToWebSocket(string1,"INFO",string2);
		}
	}
	public  static  void  e(String string1,String string2){
		if (mark) {
			Log.e(string1, string2);     //Red
			sendLogToWebSocket(string1,"ERROR",string2);
		}
	}
	public  static  void  d(String string1,String string2){
		if (mark) {
			Log.d(string1, string2);     //蓝色-blue
			sendLogToWebSocket(string1,"DEBUG",string2);
		}
	}
	public  static  void  v(String string1,String string2){
		if (mark) {
			Log.v(string1, string2);     //黑色-black
			sendLogToWebSocket(string1,"INFO",string2);
		}
	}
	public  static  void  w(String string1,String string2){
		if (mark) {
			Log.w(string1, string2);     //黄色-
			sendLogToWebSocket(string1,"WARN",string2);
		}
	}
	public  static  void  first(String string1,String string2){
		if (mark) {
			Log.e(string1, string2);     //Green
			sendLogToWebSocket(string1,"ERROR",string2);
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