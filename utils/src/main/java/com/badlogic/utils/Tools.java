package com.badlogic.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.service.autofill.Transformation;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.DisplayCutout;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {


    /**
     *
     * @param color
     * @param radius
     * @return
     */
    public static Drawable getCornerDrawable(int color, int radius) {
        float[] outerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape rrShape = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rrShape);
        drawable.getPaint().setColor(color);
        return drawable;
    }

    public static Drawable getCornerDrawableS(int color, int color_pressed, int radius) {
        float[] outerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape rrShape = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rrShape);
        drawable.getPaint().setColor(color);

        ShapeDrawable drawable_pressed = new ShapeDrawable(rrShape);
        drawable_pressed.getPaint().setColor(color_pressed);
        //-----------
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, drawable_pressed);
        // View.EMPTY_STATE_SET--没有任何状态时显示的图片，我们给它设置我空集合
        bg.addState(new int[]{}, drawable);
        return bg;
    }

    /**
     *
     * @param color
     * @param radius
     * @param widthStroke
     * @param colorStroke
     * @return
     */
    public static Drawable getCornerDrawable(int color, int radius, int widthStroke, int colorStroke) {
        int colors[] = {color, color};
        GradientDrawable mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);//
        mGradientDrawable.setCornerRadius(radius);
        mGradientDrawable.setStroke(widthStroke, colorStroke);
        mGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return mGradientDrawable;
    }

    /**
     *
     * @param color
     * @param radius -radii for each of the 4 corners. For each corner, the array
     *      * contains 2 values, <code>[X_radius, Y_radius]</code>. The corners are
     *      * ordered top-left, top-right, bottom-right, bottom-left.
     * @param widthStroke
     * @param colorStroke
     * @return
     */
    public static Drawable getCornerDrawable(int color, float radius[], int widthStroke, int colorStroke) {
        int colors[] = {color, color};
        GradientDrawable mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);//
        mGradientDrawable.setCornerRadii(radius);
        mGradientDrawable.setStroke(widthStroke, colorStroke);
        mGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return mGradientDrawable;
    }

    /**
     *
     * @param color
     * @param radius
     * @param widthStroke
     * @param colorStroke
     * @param dashWidth
     * @param dashGap
     * @return
     */
    public static Drawable getCornerDrawable(int color, int radius, int widthStroke, int colorStroke, float dashWidth, float dashGap) {
        int colors[] = {color, color};
        GradientDrawable mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);//
        mGradientDrawable.setCornerRadius(radius);
        mGradientDrawable.setStroke(widthStroke, colorStroke, dashWidth, dashGap);
        return mGradientDrawable;
    }

    ///=========================

    public static Drawable getDrawableBgCompat(Context context, int id){
        return ContextCompat.getDrawable(context, id);
    }

    public static Drawable getDrawableByCache(Context context, int id){
        return ContextCompat.getDrawable(context, id);
    }

    public static Drawable getDrawableByNew(Context context, int id){
        return ContextCompat.getDrawable(context,id).getConstantState().newDrawable().mutate();
    }

    public static Drawable getDrawableBgResId(Resources res, int id){
        return res.getDrawable(id);
    }

    public static Drawable getDrawableOriginalBgResId(Resources res, int id){
        return new BitmapDrawable(res,getBitmapOriginalBgResId(res,id));
    }

    public static Bitmap getBitmapOriginalBgResId(Resources res, int id){
        Bitmap bm = null;
        try {
            final TypedValue value = new TypedValue();
            final InputStream is = res.openRawResource(id, value);

            bm = decodeResourceStream(res, value, is, null, null);
            //--bm = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            /*  do nothing.
                If the exception happened on open, bm will be null.
                If it happened on close, bm is still valid.
            */
        }
        return bm;
    }

    public static Bitmap decodeResourceStream(Resources res, TypedValue value,
                                              InputStream is, Rect pad, BitmapFactory.Options opts) {
        if (opts == null) {
            opts = new BitmapFactory.Options();
        }

        if (opts.inDensity == 0 && value != null) {
            final int density = value.density;
            if (density == TypedValue.DENSITY_DEFAULT) {
                opts.inDensity = DisplayMetrics.DENSITY_DEFAULT;
            } else if (density != TypedValue.DENSITY_NONE) {
                opts.inDensity = density;
            }
        }

        if (opts.inTargetDensity == 0 && res != null) {
            opts.inTargetDensity = res.getDisplayMetrics().densityDpi;
            opts.inDensity = opts.inTargetDensity;
        }
        ///----BitmapFactory.decodeResource(BaseApplication.getContext().getResources(), imgId);
        return BitmapFactory.decodeStream(is, pad, opts);
    }

    public static Bitmap getBitmapFrom_AssetsFile(String path){
        String newPath=path;
        Bitmap image = null;
        boolean mark=false;
        try{
            FileInputStream inStream = new FileInputStream(newPath);
            image = BitmapFactory.decodeStream(inStream);
            inStream.close();
        }catch(FileNotFoundException e){
            mark=true;
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (OutOfMemoryError  e) {
            e.printStackTrace();
        }
        if(mark){

        }
        return image;
    }

    public static Bitmap getBitmapFrom_sd(String path){
        try {
            File mFile=new File(path);
            //若该文件存在
            if (mFile.exists()) {
                Bitmap bitmap=BitmapFactory.decodeFile(path);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFileContent(File file) {
        String content = "";
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
            if (file.getName().endsWith("txt")) {//文件格式为""文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
                                = new InputStreamReader(instream, "UTF-8");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line = "";
                        //分行读取
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();//关闭输入流
                    }
                } catch (java.io.FileNotFoundException e) {
                    Log.d("TestFile", "The File doesn't not exist.");
                } catch (IOException e) {
                    Log.d("TestFile", e.getMessage());
                }
            }
        }
        return content;
    }

    public static String readFile(File srcFile) {
        if (!srcFile.exists() || !srcFile.canRead()) {
            return "";
        }
        FileInputStream inputStream = null;
        StringBuilder sb = new StringBuilder("");
        try {
            //获得原文件流
            inputStream = new FileInputStream(srcFile);
            byte[] data = new byte[4096];
            //输出流开始处理流
            int byteCount = 0;
            while ((byteCount=inputStream.read(data)) != -1) {
                ALog.i(ALog.Tag2,"CopyAccoutFragment--getAccountData--byteCount->"+byteCount);
                sb.append(new String(data, 0, byteCount));
            }
        }catch (Throwable e) {
            return "";
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                }catch (Throwable e) {}
            }
        }
        return sb.toString();
    }

    public static  String readFromAssets(Context mContext,String path) {
        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder("");
        try {
            //获得原文件流
            inputStream = mContext.getAssets().open(path);
            byte[] data = new byte[4096];
            //输出流开始处理流
            int byteCount = 0;
            while ((byteCount=inputStream.read(data)) != -1) {
                sb.append(new String(data, 0, byteCount));
            }
        }catch (Throwable e) {
            return "";
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                }catch (Throwable e) {}
            }
        }
        return sb.toString();
    }

    /**
     * 从raw中读取txt
     */
    public static String readFromRaw(Context mContext,int res_id) {
        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder("");
        try {
            //获得原文件流
            inputStream = mContext.getResources().openRawResource(res_id);
            byte[] data = new byte[4096];
            //输出流开始处理流
            int byteCount = 0;
            while ((byteCount=inputStream.read(data)) != -1) {
                sb.append(new String(data, 0, byteCount));
            }
        }catch (Throwable e) {
            return "";
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                }catch (Throwable e) {}
            }
        }
        return sb.toString();
    }

    ///=========================

    public static void showLongToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
    }

    ///=========================

    public static boolean checkPermissionGranted(Context context, String permission) {
        // Android 6.0 以前，全部默认授权
        boolean result = true;
        int targetSdkVersion = 21;
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= 23, 使用Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < 23, 需要使用 PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    public static void applyPermissionGranted(final Context context, final String tintStr, final String permission[]) {
        new AlertDialog.Builder(context)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2016/11/10 打开系统设置权限
                        dialog.cancel();
                        //请求权限
                        try {
                            ActivityCompat.requestPermissions((Activity) context, permission, 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(tintStr+"")
                .show();
    }

    public static void appendLog(String file, String content) {
        try {
            File logFile = new File(file);
            File logParentFile = new File(logFile.getParent());
            if (!logParentFile.exists()) {
                logParentFile.mkdirs();
            }
            logFile.createNewFile();
            FileWriter fileWriter = new FileWriter(logFile, true);
            fileWriter.append("\n");
            fileWriter.append(content);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///=========================

    public static int[] getScreenSize(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return new int[]{width,height};
    }

    /**
     * 判断是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity == null) {
                //Log.w("Utility", "couldn't get connectivity manager");
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].isAvailable()) {
                            //Log.d("Utility", "network is available");
                            return true;
                        }
                    }
                }
            }
        }
        //Log.d("Utility", "network is not available");
        return false;
    }


    /**
     * 移除SharedPreference
     *
     * @param context
     * @param key
     */
    public static final void RemoveValue(Context context, String key) {
        Editor editor = getSharedPreference(context).edit();
        editor.remove(key);
        boolean result = editor.commit();
        if (!result) {
            //Log.e("移除Shared", "save " + key + " failed");
        }
    }

    private static final SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 获取SharedPreference 值
     *
     * @param context
     * @param key
     * @return
     */
    public static final String getValue(Context context, String key) {
        return getSharedPreference(context).getString(key, "");
    }

    public static final Boolean getBooleanValue(Context context, String key) {
        return getSharedPreference(context).getBoolean(key, false);
    }

    public static final void putBooleanValue(Context context, String key,
                                             boolean bl) {
        Editor edit = getSharedPreference(context).edit();
        edit.putBoolean(key, bl);
        edit.commit();
    }

    public static final int getIntValue(Context context, String key) {
        return getSharedPreference(context).getInt(key, 0);
    }

    public static final long getLongValue(Context context, String key,
                                          long default_data) {
        return getSharedPreference(context).getLong(key, default_data);
    }

    public static final boolean putLongValue(Context context, String key,
                                             Long value) {
        Editor editor = getSharedPreference(context).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static final Boolean hasValue(Context context, String key) {
        return getSharedPreference(context).contains(key);
    }

    /**
     * 设置SharedPreference 值
     *
     * @param context
     * @param key
     * @param value
     */
    public static final boolean putValue(Context context, String key,
                                         String value) {
        value = value == null ? "" : value;
        Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }

    /**
     * 设置SharedPreference 值
     *
     * @param context
     * @param key
     * @param value
     */
    public static final boolean putIntValue(Context context, String key,
                                            int value) {
        Editor editor = getSharedPreference(context).edit();
        editor.putInt(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }

    public static Date stringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = null;
        try {
            // Fri Feb 24 00:00:00 CST 2012
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getWidth(String mString, Paint mPaint) {
        Rect rect = new Rect();
        mPaint.getTextBounds(mString, 0, mString.length(), rect);
        return rect.width();
    }

    /**
     * 得到TextView设置此字号的高度
     *
     * @param fontSize
     * @return
     */
    public static int getFontHeight(float fontSize) {

//		float size =mPaint.measureText(mString);

        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
//		return (int) Math.ceil(fm.descent - fm.top) + 2;

//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.top->>"+fm.top);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.bottom->>"+fm.bottom);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.descent->>"+fm.descent);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.ascent->>"+fm.ascent);
        return (int) Math.ceil(fm.bottom - fm.top);

    }


    public static int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
//		return (int) Math.ceil(fm.descent - fm.top) + 2;

//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.top->>"+fm.top);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.bottom->>"+fm.bottom);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.descent->>"+fm.descent);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.ascent->>"+fm.ascent);
        return (int) Math.ceil(fm.bottom - fm.top);
    }

    public static Drawable Recycle(Drawable mDrawable) {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            ((BitmapDrawable) mDrawable).getBitmap().recycle();
            mDrawable = null;
        }
        return null;
    }

    public static Bitmap Recycle(Bitmap mBitmap) {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
        return null;
    }

    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean showSoft(Context mContext, EditText mEditText) {
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        //--然后获取焦点：
        mEditText.requestFocus();
        mEditText.requestFocusFromTouch();
        mEditText.findFocus();
        //-----------------------------------------------
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.restartInput(mEditText);
        boolean Mark = imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
        return Mark;
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static int getColor(Context mContext, int color_id) {
        return mContext.getResources().getColor(color_id);
    }

    public static boolean isDebugable;
    public static int isDebugable_mark = -1;

    public static boolean isApkDebugable(Context context) {
        if (isDebugable_mark == -1) {
            try {
                PackageInfo pkginfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                        PackageManager.GET_SIGNATURES);
                if (pkginfo != null) {
                    ApplicationInfo info = pkginfo.applicationInfo;
                    isDebugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
                    return isDebugable;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isDebugable = false;
            return false;
        } else {
            return isDebugable;
        }
    }


    private static final int HORIZONTAL_ALIGN_LEFT = 1;
    private static final int HORIZONTAL_ALIGN_RIGHT = 2;
    private static final int HORIZONTAL_ALIGN_CENTER = 3;
    private static final int VERTICAL_ALIGN_TOP = 1;
    private static final int VERTICAL_ALIGN_BOTTOM = 2;
    private static final int VERTICAL_ALIGN_CENTER = 3;

    public static boolean createTextBitmapShadowStroke(byte[] bytes, final String fontName, int fontSize,
                                                       int fontTintR, int fontTintG, int fontTintB, int fontTintA,
                                                       int alignment, int width, int height,
                                                       boolean shadow, float shadowDX, float shadowDY, float shadowBlur, float shadowOpacity,
                                                       boolean stroke, int strokeR, int strokeG, int strokeB, int strokeA, float strokeSize) {
        String string;
        if (bytes == null || bytes.length == 0) {
            return false;
        } else {
            string = new String(bytes);
        }

        Layout.Alignment hAlignment = Layout.Alignment.ALIGN_NORMAL;
        int horizontalAlignment = alignment & 0x0F;
        switch (horizontalAlignment) {
            case HORIZONTAL_ALIGN_CENTER:
                hAlignment = Layout.Alignment.ALIGN_CENTER;
                break;
            case HORIZONTAL_ALIGN_RIGHT:
                hAlignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            case HORIZONTAL_ALIGN_LEFT:
                break;
            default:
                break;
        }

        //--TextPaint paint = Cocos2dxBitmap.newPaint(fontName, fontSize);
        TextPaint paint = new TextPaint();
        if (stroke) {
            paint.setStyle(TextPaint.Style.STROKE);
            paint.setStrokeWidth(strokeSize);
        }

        int maxWidth = width;
        if (maxWidth <= 0) {
            maxWidth = (int) Math.ceil(StaticLayout.getDesiredWidth(string, paint));
        }
        StaticLayout staticLayout = new StaticLayout(string, paint, maxWidth, hAlignment, 1.0f, 0.0f, false);
        int layoutWidth = staticLayout.getWidth();
        int layoutHeight = staticLayout.getLineTop(staticLayout.getLineCount());

        int bitmapWidth = Math.max(layoutWidth, width);
        int bitmapHeight = layoutHeight;
        if (height > 0) {
            bitmapHeight = height;
        }

        if (bitmapWidth == 0 || bitmapHeight == 0) {
            return false;
        }

        int offsetX = 0;
        if (horizontalAlignment == HORIZONTAL_ALIGN_CENTER) {
            offsetX = (bitmapWidth - layoutWidth) / 2;
        } else if (horizontalAlignment == HORIZONTAL_ALIGN_RIGHT) {
            offsetX = bitmapWidth - layoutWidth;
        }

        int offsetY = 0;
        int verticalAlignment = (alignment >> 4) & 0x0F;
        switch (verticalAlignment) {
            case VERTICAL_ALIGN_CENTER:
                offsetY = (bitmapHeight - layoutHeight) / 2;
                break;
            case VERTICAL_ALIGN_BOTTOM:
                offsetY = bitmapHeight - layoutHeight;
                break;
        }

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(offsetX, offsetY);
        if (stroke) {
            paint.setARGB(strokeA, strokeR, strokeG, strokeB);
            staticLayout.draw(canvas);
        }
        paint.setStyle(TextPaint.Style.FILL);
        paint.setARGB(fontTintA, fontTintR, fontTintG, fontTintB);
        staticLayout.draw(canvas);

        //-Cocos2dxBitmap.initNativeObject(bitmap);
        return true;


    }


    /**
     * 算法可以优化--二分法
     * @param text
     * @param mPaint
     * @param desiredWidth
     * @return
     */
    public static String getStringByWidth(String text,TextPaint mPaint,int desiredWidth){
        if(text==null||mPaint==null){
            return"";
        }
        ALog.i("wjw02","--Tools--getStringByWidth--text-->>"+text);
        ALog.i("wjw02","--Tools--getStringByWidth--text.length()-->>"+text.length());
        float width_temp=0;
        String final_str="";
        for(int i=0;i<text.length();i++){
            width_temp+=StaticLayout.getDesiredWidth(text.charAt(i)+"", mPaint);
            if(width_temp>desiredWidth){
                return final_str;
            }
            final_str+=text.charAt(i);
        }
        return final_str;
    }

    public static String getStringWithEllipsis(String string, float width,float fontSize) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        TextPaint paint = new TextPaint();
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(fontSize);
        return TextUtils.ellipsize(string, paint, width, TextUtils.TruncateAt.END).toString();
    }

    public static byte[] getFileBytesWithRoundCorner(String filePath, float roundPx) {
        Bitmap Bitmap_old = BitmapFactory.decodeFile(filePath);
        if (Bitmap_old == null) {
            return null;
        }
        int width=Bitmap_old.getWidth();
        int height=Bitmap_old.getHeight();
        if (width<=0||height<=0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(Bitmap_old.getWidth(), Bitmap_old.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xffffffff);

        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(Bitmap_old, rect, rect, paint);

        Bitmap_old.recycle();
        Bitmap_old=null;
        //--return getPixels(output);
        return bitmap2Bytes(output,Bitmap.CompressFormat.PNG);
    }

    private void setEmojiToTextView(){
        int unicodeJoy = 0x1F602;
        String emojiString = getEmojiStringByUnicode(unicodeJoy);
        //myTextView.setText(emojiString);
    }

    private String getEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    private static byte[] getPixels(final Bitmap bitmap) {
        if (bitmap != null) {
            final byte[] pixels = new byte[bitmap.getWidth()
                    * bitmap.getHeight() * 4];
            final ByteBuffer buf = ByteBuffer.wrap(pixels);
            buf.order(ByteOrder.nativeOrder());
            bitmap.copyPixelsToBuffer(buf);
            return pixels;
        }
        return null;
    }

    public static byte[] bitmap2Bytes(Bitmap bm,Bitmap.CompressFormat format){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(format, 100, baos);
        return baos.toByteArray();
    }

    public static BitmapDrawable BitmapToDrawable(Context myContext,Bitmap  mBitmap){
        if (mBitmap != null) {
            return new BitmapDrawable (myContext.getResources(),mBitmap);
        }else{
            return null;
        }
    }

    public static int getDimensionPixelOffset(Context context, int id){
        return context.getResources().getDimensionPixelOffset(id);
    }

    public static float getgetDimension(Context context, int id){
        return context.getResources().getDimension(id);
    }

    /**
     * 单位是px的会出现问题，前置将数值转换为px
     * getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.
     * @param context
     * @param id
     * @return
     */
    private static float 和getDimensionPixelSize(Context context, int id){
        return context.getResources().getDimensionPixelSize(id);
    }

    public static String getMode_next(int measureSpec) {
        int Mode= View.MeasureSpec.getMode(measureSpec);
        if(Mode== View.MeasureSpec.UNSPECIFIED){
            return "UNSPECIFIED";
        }else if(Mode== View.MeasureSpec.EXACTLY){
            return "EXACTLY";
        }else if(Mode== View.MeasureSpec.AT_MOST){
            return "AT_MOST";
        }else{
            return "no_error";
        }
    }

    //================================================

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap getBitmapFrom_sd_JustSize(String path){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        final int width_origi = options.outWidth;
        final int height_origi = options.outHeight;
        int width_new,height_new;
        if (width_origi > height_origi) {
            height_new = height_origi;
            if (height_origi > 1080) {
                height_new = 1080;
            }
            width_new = (int)(height_new * (width_origi/(float)height_origi));
            if (width_new > 1920) {
                width_new = 1920;
                height_new = (int)(width_new * (height_origi/(float)width_origi));
            }
        } else {
            width_new = width_origi;
            if (width_new > 1080) {
                width_new = 1080;
            }
            height_new = (int)(width_new * (height_origi/(float)width_origi));
            if (height_new > 1920) {
                height_new = 1920;
                width_new = (int)(height_new * (width_origi/(float)height_origi));
            }
        }
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, width_new, height_new);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap srcBmp = BitmapFactory.decodeFile(path,options);
        return srcBmp;
    }

    //================================================

    public static Bitmap getBitmapOriginalBgResId_b(Resources res, int id){
        return BitmapFactory.decodeResource(res, id);
    }

    public static Bitmap getAssetsBitmap(Context context, String path){
        AssetManager am = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = am.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    public static String readStrById(Context hContext,@StringRes int resId) {
        String result = "";
        try{
            result = hContext.getResources().getString(resId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /*public static Bitmap getBitmapOriginalBgResId(Resources res, int id){
        Bitmap bm = null;
        try {
            final TypedValue value = new TypedValue();
            final InputStream is = res.openRawResource(id, value);

            bm = decodeResourceStream(res, value, is, null, null);
            //--bm = BitmapFactory.decodeStream(is);
            is.close();
        } catch (java.io.IOException e) {
            *//*  do nothing.
                If the exception happened on open, bm will be null.
                If it happened on close, bm is still valid.
            *//*
        }
        return bm;
    }*/

    /*public static Bitmap decodeResourceStream(Resources res, TypedValue value,
                                              InputStream is, Rect pad, BitmapFactory.Options opts) {
        if (opts == null) {
            opts = new BitmapFactory.Options();
        }

        if (opts.inDensity == 0 && value != null) {
            final int density = value.density;
            if (density == TypedValue.DENSITY_DEFAULT) {
                opts.inDensity = DisplayMetrics.DENSITY_DEFAULT;
            } else if (density != TypedValue.DENSITY_NONE) {
                opts.inDensity = density;
            }
        }

        if (opts.inTargetDensity == 0 && res != null) {
            opts.inTargetDensity = res.getDisplayMetrics().densityDpi;
            opts.inDensity = opts.inTargetDensity;
        }
        ///----BitmapFactory.decodeResource(BaseApplication.getContext().getResources(), imgId);
        return BitmapFactory.decodeStream(is, pad, opts);
    }*/


    // 获取照片的mine_type
    private static String getPhotoMimeType(String path) {
        String lowerPath = path.toLowerCase();
        if (lowerPath.endsWith("jpg") || lowerPath.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (lowerPath.endsWith("png")) {
            return "image/png";
        } else if (lowerPath.endsWith("gif")) {
            return "image/gif";
        }
        return "image/jpeg";
    }

    // 获取video的mine_type,暂时只支持mp4,3gp
    private static String getVideoMimeType(String path) {
        String lowerPath = path.toLowerCase();
        if (lowerPath.endsWith("mp4") || lowerPath.endsWith("mpeg4")) {
            return "video/mp4";
        } else if (lowerPath.endsWith("3gp")) {
            return "video/3gp";
        }
        return "video/3gp";
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public boolean isMainThread_2() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public boolean isMainThread_3() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }

    /**
     * path转uri
     */
    private Uri getUri(Context mContext,String path){
        Uri uri = null;
        if (path != null) {
            path = Uri.decode(path);
            Log.d("wjw02", "path2 is " + path);
            ContentResolver cr = mContext.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[] { MediaStore.Images.ImageColumns._ID },
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
            } else {
                Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                Log.d("wjw02", "uri_temp is " + uri_temp);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }


    public static int getStatusBarHeight1(Context hContext) {
        int statusBarHeight1 = -1;
        try {
            //获取status_bar_height资源的ID
            int resourceId = hContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight1 = hContext.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return statusBarHeight1;
    }

    public static int getStatusBarHeight2(Context hContext) {
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = hContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight2;
    }

    public static int getStatusBarHeight3(Activity hActivity) {
        /**
         * 获取状态栏高度——方法3
         * 应用区的顶端位置即状态栏的高度
         * *注意*该方法不能在初始化的时候用
         * */
        try {
            Rect rectangle= new Rect();
            hActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
            return rectangle.top;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getScreenRotationAngle(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return Surface.ROTATION_0;
        }

        Display display = wm.getDefaultDisplay();
        return display.getRotation();
    }

    /**
     * 是否有刘海屏
     *
     * @return
     */
    public static Boolean hasNotchInScreen(Activity activity) {
        // android  P 以上有标准 API 来判断是否有刘海屏
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                DisplayCutout displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
                if (displayCutout != null) {
                    // 说明有刘海屏
                    return true;
                }
            } else {
                // 通过其他方式判断是否有刘海屏  目前官方提供有开发文档的就 小米，vivo，华为（荣耀），oppo
                String manufacturer = Build.MANUFACTURER;

                if (manufacturer == null || manufacturer.equals("")) {
                    return false;
                } else if (manufacturer.equalsIgnoreCase("HUAWEI")) {
                    return hasNotchHw(activity);
                } else if (manufacturer.equalsIgnoreCase("xiaomi")) {
                    return hasNotchXiaoMi(activity);
                } else if (manufacturer.equalsIgnoreCase("oppo")) {
                    return hasNotchOPPO(activity);
                } else if (manufacturer.equalsIgnoreCase("vivo")) {
                    return hasNotchVIVO(activity);
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ALog.i("Tools-hasNotchInScreen-Exception->"+e.toString());
            return null;
        }
        return false;
    }

    /**
     * 判断vivo是否有刘海屏
     * https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchVIVO(Activity activity) {
        try {
            Class<?> c = Class.forName("android.util.FtFeature");
            Method get = c.getMethod("isFeatureSupport", int.class);
            return (boolean) (get.invoke(c, 0x20));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断oppo是否有刘海屏
     * https://open.oppomobile.com/wiki/doc#id=10159
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchOPPO(Activity activity) {
        return activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 判断xiaomi是否有刘海屏
     * https://dev.mi.com/console/doc/detail?pId=1293
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchXiaoMi(Activity activity) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("getInt", String.class, int.class);
            return (int) (get.invoke(c, "ro.miui.notch", 0)) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断华为是否有刘海屏
     * https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchHw(Activity activity) {

        try {
            ClassLoader cl = activity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            return (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static long getExternalAvailableSpaceInBytes() {
        long availableSpace = -1L;
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            availableSpace = (long) stat.getAvailableBlocksLong() * (long) stat.getBlockSizeLong();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableSpace;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isEnoughSpaceToDownload() {
        return getExternalAvailableSpaceInBytes() > 100 * 1024 * 1024;
    }





}