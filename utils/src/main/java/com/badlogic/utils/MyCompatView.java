package com.badlogic.utils;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MyCompatView extends View {

	public MyCompatView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyCompatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyCompatView(Context context) {
		super(context);
		init(context);
	}
	
	//========================================================

	LinearLayout mLinearLayout;
	FrameLayout mFrameLayout;
	RelativeLayout mRelativeLayout;
	ListView mListView;

	private int mTouchSlop ;
	private int mMinimumVelocity;
	private int mMaximumVelocity;

	private int mOverscrollDistance;
	private int mOverflingDistance;

	public void init(Context context){
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop= configuration.getScaledTouchSlop();
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
		mOverscrollDistance = configuration.getScaledOverscrollDistance();
		mOverflingDistance = configuration.getScaledOverflingDistance();
		//---------------
		Log.i("wjw02","PushApplication--onCreate--Binder.getCallingPid()->"+ Binder.getCallingPid());
		Log.i("wjw02","PushApplication--onCreate--android.os.Process.myPid()->"+android.os.Process.myPid());
		Log.i("wjw02","PushApplication--onCreate--android.os.Process.myPid()->"+android.os.Process.myUid());


//		CPUFrameworkHelper kk;
	}

	
	public void study() {
		android.os.Debug.waitForDebugger();
	}
	
	public static void SetNetAlertDialog(final Context  context) {
		new AlertDialog.Builder(context)
	    .setTitle("提示")
		.setMessage("网络连接不上，请检查您的网络设置")
	    .setPositiveButton("网络设置", 
	    new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int which) {
			context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		}                   
		})
		
		.setNegativeButton("取消",
		new DialogInterface.OnClickListener(){
	    public void onClick(DialogInterface dialoginterface, int i){
	    	
	    }
		})
		.show();

		//--Bitmap mBitmap=null;mBitmap.recycle();
	}
	
	
	
	//========================================================
	
	/*
	 * 
	 
	 硬件加速不支持的操作：
	  Canvas
	clipPath()
	clipRegion()
	drawPicture()
	drawTextOnPath()
	drawVertices()
	Paint
	setLinearText()
	setMaskFilter()
	setRasterizer()
	Xfermodes
	AvoidXfermode
	PixelXorXfermode
	另外，一些操作在硬件加速后表现不一样：
	Canvas
	clipRect()：XOR, Difference和ReverseDifference剪切模式被忽略。3D转换不会应用于剪切矩形。
	drawBitmapMesh()：颜色数组被忽略
	Paint
	setDither()：忽略
	setFilterBitmap()：过滤器会一直开启
	setShadowLayer()：只有text能使用
	PorterDuffXfermode
	PorterDuff.Mode.DARKEN 当针对framebuffer时和SRC_OVER是等效的。
	PorterDuff.Mode.LIGHTEN 当针对framebuffer时和SRC_OVER是等效的。 
	PorterDuff.Mode.OVERLAY 当针对framebuffer时和SRC_OVER是等效的。 
	ComposeShader
	ComposeShader 只能包含不同类型的着色器
	ComposeShader 不能包含一个ComposeShader
	如果你的程序被任何缺少特性或者限制所影响，你可以在受影响的部分关闭硬件加速。
	
	*/
	
    /*	
     * 
     * 
	开启硬件加速之后，Android中的2D渲染管道可以支持绝大部分常用的Canvas的绘画操作函数以及那些很少被使用的操作
	函数。所有的那些用来呈现Android中的应用程序的绘画操作、默认的部件和布局以及常见的高
	级视觉效果（比如反射和纹理）都被支持。以下列表是已知的不被硬件加速所支持的操作：
	* Canvas
	     ** clipPath()
	     ** clipRegion()
	     ** drawPicture()
	     ** drawTextOnPath()
	     ** drawVertices()
	* Paint
	     ** setLinearText()
	     ** setMaskFilter()
	     ** setRasterizer()
	* Xfermodes
	     ** AvoidXfermode
	     ** PixelXorXfermode

	而且，有些操作的效果在硬件加速之后会有变化。
	* Canvas
	     ** clipRect(): XOR, Difference 和 ReverseDifference 这三种裁剪模式被忽略。3D转换不适用于修剪矩形
	     ** drawBitmapMesh(): 颜色矩阵被忽略
	* Paint
	     ** setDither(): 被忽略
	     ** setFilterBitmap(): 过滤一直处于开启状态
	     ** setShadowLayer(): 只能和文字一起使用
	* PorterDuffXfermode
	     ** PorterDuff.Mode.DARKEN 等价于 SRC_OVER 如果帧缓冲区不支持混合
	     ** PorterDuff.Mode.LIGHTEN 等价于 SRC_OVER 如果帧缓冲区不支持混合
	     ** PorterDuff.Mode.OVERLAY 等价于 SRC_OVER 如果帧缓冲区不支持混合
	* ComposeShader
	     ** ComposeShader 仅能包含不同类型的着色器(比如包含一个 BitmapShader 的实例和一个 Li
	    		 nearGradient的实例是允许的，包含两个 BitmapShader 的实例却是不行的)
	     ** ComposeShader 不能包含一个 ComposeShader

	如果你的应用程序使用了以上不被支持或者是受限制的操作，你可以在使用了上述操作的View
	中调用setLayerType(View.LAYER_TYPE_SOFTWARE, null)来关闭硬件加速。这样，你的程序在其他的地方依然可以享受硬件加速带来的好处。
	
	*/
	
	/***
	 * @SuppressLint("NewApi") 在系统的View中设置，才会自检，报错 兼容设置的最小的SDK版本
	 */
	@SuppressLint("NewApi")
	public static boolean get_HardwareAccelerated(android.view.View mView){
		if(android.os.Build.VERSION.SDK_INT>=11){//可以开启硬件加速
			return mView.isHardwareAccelerated();
		}else{
			return false;
		}
	}
	
	@SuppressLint("NewApi")
	public static void set_HARDWARE_ACCELERATED(Activity mActivity){
		if(android.os.Build.VERSION.SDK_INT>=11){//可以开启硬件加速
			mActivity.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
	}
	
	@SuppressLint("NewApi")
	public static void cannel_HARDWARE_ACCELERATED(android.view.View mView){
		if(android.os.Build.VERSION.SDK_INT>=11){//可以开启硬件加速
			mView.setLayerType(android.view.View.LAYER_TYPE_SOFTWARE,null);
		}
	}
	
	
	@SuppressLint("NewApi")
	public static void setMotionEventSplittingEnabled(ViewGroup mView){
		mView.setMotionEventSplittingEnabled(false);
		
		/*
		 <LinearLayout android:splitMotionEvents="false" ... >
		 ...限制一级子view的多点触控
		 </LinearLayout>
		*/
		
		
		/*
		 <style name="NoSplitMotionEvents" parent="android:Theme.Holo">
	     <item name="android:windowEnableSplitTouch">false</item>
	     ...
	     </style>
	    */
	    
	}

	//========================================================
	
	/**
	 * GridView,ListView
	 * 可以去掉划到顶端或底部，继续滑动的，渐变效果
	 * MyView.cannel_OverScrollMode(this);
	 * 
	 */
	@SuppressLint("NewApi")
	public static void cannel_OverScrollMode(AbsListView mView){
		if(android.os.Build.VERSION.SDK_INT>=9){
			try {
				//this.setOverScrollMode(2);//可以去掉划到顶端或底部，继续滑动的，渐变效果
				mView.setOverScrollMode(android.view.View.OVER_SCROLL_NEVER);
			} catch (Exception e) {
				e.printStackTrace();
			}       
		}
	}
	
	/**
	 * ViewPager,ScrollView
	 * @param mView
	 * MyView.cannel_OverScrollMode(this);
	 */
	@SuppressLint("NewApi")
	public static void cannel_OverScrollMode(android.view.View mView){
		if(android.os.Build.VERSION.SDK_INT>=9){
			try {
				//this.setOverScrollMode(2);//可以去掉划到顶端或底部，继续滑动的，渐变效果
				//--mView.setOverScrollMode(android.view.View.OVER_SCROLL_NEVER);
				//--mView.setOverScrollMode(android.view.View.OVER_SCROLL_ALWAYS);
			} catch (Exception e) {
				e.printStackTrace();
			}       
		}
	}
	
	//========================================================
	public static Drawable getDrawableFromXml(Resources res,int id){
		//--------
		XmlResourceParser xrp = null;
		try {
			xrp = res.getXml(id);
			//--有警告，设置成参数几好啦，xrp = res.getXml(333);
		} catch (Resources.NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Drawable drawable = Drawable.createFromXml(res, xrp);
			return drawable;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ColorStateList getColorStateListFromXml(Resources res, int id){
		XmlResourceParser xpp=res.getXml(id);
		//--XmlResourceParser xpp=Resources.getSystem().getXml(id);
		try {
			ColorStateList cs2= ColorStateList.createFromXml(res,xpp);
			return cs2;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ColorStateList getColorStateListFromXml_b(Resources res, int id){
		try {
			ColorStateList csl=(ColorStateList)res.getColorStateList(id);
			return csl;
		} catch (Resources.NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//========================================================
	
	public static class Build {
		public static class VERSION_CODES {
			 public static final int KITKAT = 19;
		}
	}

	public static class View {
		public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 0x00000100;
		public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 0x00000200;
		public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 0x00000400;
		public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 0x00000002;
		public static final int SYSTEM_UI_FLAG_FULLSCREEN = 0x00000004;
		public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
	}
	
	//========================================================
	//========================================================
	//========================================================
	//========================================================
	//========================================================
	//========================================================
	
	 public void add_selector_total_CodeFromXml(LinearLayout ParentView,String text_str,Context mContext){
	       /* TextView mTextView=new TextView(mContext);
	        mTextView.setText(text_str);
	        mTextView.setGravity(Gravity.CENTER);
	        //--mTextView.setBackgroundDrawable(mDrawable);
	        mTextView.setTextColor(Color.WHITE);
	        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 40);
	        mTextView.setLayoutParams(Params_b);
	        ParentView.addView(mTextView);
	        //--------
	        Resources res = ParentView.getResources();
	        XmlResourceParser xrp = null;
	        try {
	            xrp = res.getXml(R.drawable.bg_selector_total);
	        } catch (Resources.NotFoundException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            Drawable drawable = Drawable.createFromXml(getResources(), xrp);
	            mTextView.setBackgroundDrawable(drawable);
	        } catch (XmlPullParserException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }*/
	    }


	//========================================================
	//--@Subscribe(threadMode = ThreadMode.MAIN)
	public static void setTextViewId(TextView mView, int id){
		mView.setId(id);
	}

	@SuppressWarnings("unused")
	//--@Description
	/**
	 * @Description 获取相册图片集
	 */
	private void getThumbnailColumnData(Cursor cur) {

		long down_time= SystemClock.uptimeMillis();
		this.dispatchTouchEvent(
				MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
		this.dispatchTouchEvent(
				MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
	}

}