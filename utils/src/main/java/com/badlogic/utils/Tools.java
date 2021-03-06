package com.badlogic.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
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
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.os.Vibrator;
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
import android.view.DisplayCutout;
import android.view.Surface;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {


    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) size = size + getFolderSize(fileList[i]);
                else size = size + fileList[i].length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static long getFolderSize(String filePath) {
        long size = 0;
        try {
            File file = new File(filePath);
            return getFolderSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     * @param deleteThisPath
     * @return
     */
    public static  void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ///=========================

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

    /*
    Tools.tintDrawable(((TextView)findViewById(R.id.unlock_hint)).getBackground(), ColorStateList.valueOf(0xffAA00FF));
    此方法设置.9图，
    setTint(0xff304FFE); 方法设置.9图， 会崩溃
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
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

    public static String readText(String filePath) {
        try {
            File hFile = new File(filePath);
            return readFile(hFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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

    public static int getAndroidSDK_INT(){
        ///-android.os.Build.VERSION_CODES.O;
        return  android.os.Build.VERSION.SDK_INT;
    }

   ///=========================

    public static void printIntArray(int[] array){
        if (array == null) {
            System.out.print("{}");
            return;
        }
        for (int i = 0; i < array.length; i++ ) {
            if (i == 0) {
                System.out.print("{ ");
            }
            if (i == array.length - 1) {
                System.out.print(array[i]);
                System.out.print(" }");
            } else {
                System.out.print(array[i] + ",");
            }
        }
        System.out.println();
    }

    public static void printIntArray(ArrayList<Integer> list){
        if (list == null) {
            System.out.print("{}");
            return;
        }
        for (int i = 0; i < list.size(); i++ ) {
            if (i == 0) {
                System.out.print("{ ");
            }
            if (i == list.size() - 1) {
                System.out.print(list.get(i));
                System.out.print(" }");
            } else {
                System.out.print(list.get(i) + ",");
            }
        }
    }

    public static void showLongToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
    }

    private static Vibrator vibrator;

    /**
     * 震动
     */
    public static void vibrate(Context hContext) {
        try {
            if(vibrator == null) {
                vibrator = (Vibrator) hContext.getSystemService(Service.VIBRATOR_SERVICE);
            }
            vibrator.vibrate(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void vibrate(Context hContext,long milliseconds) {
        try {
            if(vibrator == null) {
                vibrator = (Vibrator) hContext.getSystemService(Service.VIBRATOR_SERVICE);
            }
            vibrator.vibrate(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void vibrate() {
        vibrate(Tools.getApplication());
    }

    public static void vibrate(long milliseconds) {
        vibrate(Tools.getApplication(),milliseconds);
    }

    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public int  getTargetSdkVersion(Context mContext){
        final int targetSdkVersion = mContext.getApplicationInfo().targetSdkVersion;
        return targetSdkVersion;
    }

    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getHost(String url) {
        String domain = "";
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            try {
                String host = Uri.parse(url).getHost();
                return host;
            } catch (Exception ex) {
            }
        }
        return domain;
    }

    private static String app_id = "";

    public static void setAppId(String mark){
        app_id = mark;
    }

    public static String getAppId(){
        return app_id;
    }


    public static String getRuntimeClassName() {
        try {
            // 获得当前类名
            String clazz = Thread.currentThread() .getStackTrace()[1].getClassName();
            return clazz;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String getRuntimeMethodName() {
        try {
            // 获得当前方法名
            ///String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            String method = Thread.currentThread() .getStackTrace()[2+1].getMethodName();
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String getRuntimeCallMethodName() {
        try {
            // 获得当前方法名
            ////String method = Thread.currentThread() .getStackTrace()[2].getMethodName();
            String method = Thread.currentThread() .getStackTrace()[2+2].getMethodName();
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static boolean checkAppInstalled(Context context, String pkgName) {
        if (pkgName== null || pkgName.isEmpty()) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if(info == null || info.isEmpty())
            return false;
        for ( int i = 0; i < info.size(); i++ ) {
            if(pkgName.equals(info.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }

    public static void openBrowser(Context context, String url){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
//        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        context.startActivity(intent);
    }
    //=================================================================================

    public static boolean isChinese(char c) {
        return isChineseByBlock(c);
        /*Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
             || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
             || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

             || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
             || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
             || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;*/
    }

    public static boolean check_AZaz09_(String s) {
        String regEx = "^[A-Za-z0-9_]+$";
        return s.matches(regEx);
    }

    public static boolean have09(String s) {
        String regEx = "^[0-9]+$";
        return s.matches(regEx);
    }
    public static boolean haveAZaz(String s) {
        String regEx = "^[A-Za-z]+$";
        return s.matches(regEx);
    }

    public static boolean checkAZaz09(String s) {
        String regEx = "^[A-Za-z0-9]+$";
        return s.matches(regEx);
    }

    public static boolean check_af_x_09(String s) {
        String regEx = "^[a-f0-9]+$";
        return s.matches(regEx);
    }

    public static boolean check09doc(String s) {
        String regEx = "^[0-9.]+$";
        return s.matches(regEx);
    }

    //------------------------------------------------------------------------------------

    //使用UnicodeBlock方法判断
    public static boolean isChineseByBlock(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        } else {
            return false;
        }
    }

    // 根据UnicodeBlock方法判断中文标点符号
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS) {
            return true;
        } else {
            return false;
        }
    }
    //=================================================================================

    public static void measureView(View child) {
        ViewGroup.LayoutParams mLayoutParams = child.getLayoutParams();
        if (mLayoutParams == null) {
            mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, mLayoutParams.width);
        int lpHeight = mLayoutParams.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    //网络连接是否好用
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if(network != null){
            return network.isAvailable();
        }
        return false;
    }

    public static enum NetWorkStateValue {
        NETWORK_NONE ,
        NETWORK_MOBILE,
        NETWORK_WIFI
    }

    public static NetWorkStateValue getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NetWorkStateValue.NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NetWorkStateValue.NETWORK_MOBILE;
            }
        } else {
            return NetWorkStateValue.NETWORK_NONE;
        }
        return NetWorkStateValue.NETWORK_NONE;
    }

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

    public static void writeTextToFile(final File file,final String log) {
        if (file == null || !file.exists()) {
            return;
        }

        boolean bSuccess = false;
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write(log);
            writer.close();
            bSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(!bSuccess){
                writeSDFile(file,log);
            }
        }
    }

    public static void writeSDFile(File file, String content) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMD5ByPath(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
            return bi.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static String getMD5ByInputStream(InputStream in) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            in.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }

    ///=========================

    public static boolean showSoftInput(EditText et) {
        try {
            InputMethodManager inputManager =(InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputManager.showSoftInput(et, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }catch (Throwable e) {
            e.getStackTrace();
        }
        return false;
    }

    public static void hideSoftInput(EditText et) {
        try {
            InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }catch (Throwable e) {}
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

    public static String date2TimeStamp(long time,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String dateString = sdf.format(time);
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String handleDate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        Date old = null;
        Date now = null;

        try {
            old = sdf.parse(sdf.format(date));
            now = sdf.parse(sdf.format(new Date()));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long oldTime = old.getTime();
        long nowTime = now.getTime();

        long day = (nowTime - oldTime) / (24 * 60 * 60 * 1000);

        if (day < 1) {  //今天
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(date);
        } else if (isThisMonth(time)) {   //是本月
            SimpleDateFormat format = new SimpleDateFormat("MM.dd");
            return  format.format(date);
        } else {    //可依次类推
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
            return format.format(date);
        }
    }
    //判断选择的日期是否是本月
    public static boolean isThisMonth(long time) {
        return isThisTime(time, "yyyy-MM");
    }

    public static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
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

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    public static String getModifyTime(File file) throws FileNotFoundException {
        String modify_time = "";
        try {
            modify_time = file.lastModified() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modify_time;
    }

    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }


    public static boolean copyAssetsToSdCard(AssetManager assetManager, String assetsPath, String sdcardPath){
        boolean b = false;
        InputStream in;
        OutputStream out;
        try {
            in = assetManager.open(assetsPath);
            if (in != null) {
                String newFileName = sdcardPath;
                out = new FileOutputStream(newFileName);
                if (out != null) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    out.flush();
                    out.close();
                    b = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

    public static void getMediaInfor(String mUri) {
        if (mUri != null && !mUri.equals("")) {
        } else {
            return;
        }
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        long duration;
        try {
            mmr.setDataSource(mUri);
            duration = Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            int width = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int height = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            if (width == 0 || height == 0) {
                duration = 0;
            }
        } catch (Exception ex) {
            ALog.i("LightVideoHelper","Exception:" + ex);
        } finally {
            mmr.release();
        }
        return ;
    }

    public static long getMediaDuring(String mUri) {
        if (mUri != null && !mUri.equals("")) {
        } else {
            return 0;
        }
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        long duration = 0;
        try {
            mmr.setDataSource(mUri);
            duration = Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            ///int width = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            //int height = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            /*if (width == 0 || height == 0) {
                duration = 0;
            }*/
        } catch (Exception ex) {
            ALog.i("LightVideoHelper","Exception:" + ex);
        } finally {
            mmr.release();
        }
        return duration;
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

    public static boolean isApkDebugable() {
        if (mApplication == null) {
            return false;
        }
        return isApkDebugable(mApplication.getBaseContext());
    }

    private static Application mApplication;

    public static void  setApplication(Application hApplication_){
        mApplication = hApplication_;
    }

    public static Application  getApplication(){
        return mApplication ;
    }

    ///==========================


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

    public static void removeGlobalOnLayoutListener(ViewTreeObserver mViewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener listener) {
        try {
            if (mViewTreeObserver == null || listener == null) {
                return;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                mViewTreeObserver.removeGlobalOnLayoutListener(listener);
            } else {
                mViewTreeObserver.removeOnGlobalLayoutListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static boolean isMainThread_2() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static boolean isMainThread_3() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }

    /**
     * path转uri
     */
    public static Uri getUri(Context mContext,String path){
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

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    //================================

    // 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )
    public static double pointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
        if (c+b == a) {//点在线段上
            space = 0;
            return space;
        }
        if (a <= 0.000001) {//不是线段，是一个点
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) { //组成直角三角形或钝角三角形，(x1,y1)为直角或钝角
            System.out.println("组成直角三角形或钝角三角形，(x1,y1)为直角或钝角  ");
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {//组成直角三角形或钝角三角形，(x2,y2)为直角或钝角
            System.out.println("组成直角三角形或钝角三角形，(x2,y2)为直角或钝角 ");
            space = c;
            return space;
        }
        //组成锐角三角形，则求三角形的高
        System.out.println("组成锐角三角形，则求三角形的高  ");
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;
    }

    //计算两点之间的距离
    public static double lineSpace(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }

    /**
     * 一点点（x0,y0）到直线的距离 ，（x2,y2）为直线上一点，angle_为直线的旋转<角度>
     * 假设对图片上任意点(x,y)，绕一个坐标点(rx0,ry0)旋转a角度后的新的坐标设为(x0, y0)，有公式：
     *         x0= (x - rx0)*cos(a) + (y - ry0)*sin(a) + rx0 ;
     *         y0= (y - ry0)*cos(a) - (x - rx0)*sin(a) + ry0;
     *         此时的旋转点事（0，0）
     *
     *         A(x1,y1) 绕 O(x0,y0) 旋转 角度 k 之后的点 B(x2,y2) 的java代码计算方法
     *         k=new Float(Math.toRadians(k));
     *         float x2=new Float((x1-x0)*Math.cos(k) +(y1-y0)*Math.sin(k)+x0);
     *         float y2=new Float(-(x1-x0)*Math.sin(k) + (y1-y0)*Math.cos(k)+y0);
     *
     *  @return 小于0 ，在直线线面，下面，左侧 ，大于0 在直线上面，右侧
     */
    public static double pointToLine(double x0, double y0, double x2, double y2, double angle_) {
        double angle = -angle_;
        ///double angle_radian = angle*Math.PI/180;
        double angle_radian = Math.toRadians(angle);

        double x0_trans = x0*Math.cos(angle_radian) + y0*Math.sin(angle_radian);
        double y0_trans = y0*Math.cos(angle_radian) - x0*Math.sin(angle_radian);
        double x2_trans = x2*Math.cos(angle_radian) + y2*Math.sin(angle_radian);
        double y2_trans = y2*Math.cos(angle_radian) - x2*Math.sin(angle_radian);
        ///return y2_trans - y0_trans;
        return y0_trans - y2_trans;
    }


    /**
     * 计算坐标系中两个点 坐在直线的<角度>
     * @return 返回<角度>
     */
    public static double getRotate(float x1,float y1,float x2,float y2) {
        double spanX = x1 - x2;
        double spanY = y1 - y2;
        return Math.toDegrees(Math.atan2(spanY, spanX));
    }

    public static double getRotate(float x1,float y1,float x2,float y2,float degree_last ) {
        double spanX = x1 - x2;
        double spanY = y1 - y2;
        double degree_now = Math.toDegrees(Math.atan2(spanY, spanX));
        double degree_now_b = degree_now;
        if (degree_now > 0) {
            degree_now_b = -360 + degree_now;
        } else if (degree_now < 0) {
            degree_now_b = 360 + degree_now;
        } else if (degree_now == 0) {
            degree_now_b = 360;
            double degree_now_c = -360;
            double differ_a = Math.abs(degree_now - degree_last);
            double differ_b = Math.abs(degree_now_b - degree_last);
            double differ_c = Math.abs(degree_now_c - degree_last);
            if (differ_a < differ_b && differ_a < differ_c) {
                return degree_now;
            } else if (differ_b < differ_a && differ_b < differ_c) {
                return differ_b;
            } else if (differ_c < differ_a && differ_c < differ_b) {
                return differ_c;
            } else {
                return degree_now;
            }
        }
        double differ_a = Math.abs(degree_now - degree_last);
        double differ_b = Math.abs(degree_now_b - degree_last);
        if (differ_a < differ_b) {
            return degree_now;
        } else {
            return degree_now_b;
        }
    }

    public static double getRotate__dd(float x1,float y1,float x2,float y2,float degree_last ) {
        double spanX = x1 - x2;
        double spanY = y1 - y2;
        double degree_now = Math.toDegrees(Math.atan2(spanY, spanX));
        // 0 --- 360
        double degree_differ = ((degree_last - degree_now) % 360 + 360) % 360;
        if (degree_differ > 350) {

        } else {
            return degree_now;
        }
        return Math.toDegrees(Math.atan2(spanY, spanX));
    }

    /**
     * 一点（x,y）是否在 一矩形中，左上的坐标，右下的坐标，矩形的角度
     * 平面坐标系中的点
     * @return
     */
    public static boolean pointInRectangle(float x,float y,float x_lt,float y_lt,float x_rb,float y_rb, float angle_) {
        double angle = -angle_;
        double angle_radian = Math.toRadians(angle);
        double x_trans = x*Math.cos(angle_radian) + y*Math.sin(angle_radian);
        double y_trans = y*Math.cos(angle_radian) - x*Math.sin(angle_radian);

        double x_lt_trans = x_lt*Math.cos(angle_radian) + y_lt*Math.sin(angle_radian);
        double y_lt_trans = y_lt*Math.cos(angle_radian) - x_lt*Math.sin(angle_radian);

        double x_rb_trans = x_rb*Math.cos(angle_radian) + y_rb*Math.sin(angle_radian);
        double y_rb_trans = y_rb*Math.cos(angle_radian) - x_rb*Math.sin(angle_radian);

        if ((   x_trans >= x_lt_trans) && (x_trans <= x_rb_trans)
                && (y_trans >= y_rb_trans) && (y_trans <= y_lt_trans)) {
            return true;
        }
        return false;
    }

    /**
     * 限制一点在 一矩形中活动 ，如超出，返回最近一点
     */
    public static double[] pointLimitRectangle(float x,float y,float x_lt,float y_lt,float x_rb,float y_rb, float angle_) {
        double angle = -angle_;
        double angle_radian = Math.toRadians(angle);
        double x_trans = x*Math.cos(angle_radian) + y*Math.sin(angle_radian);
        double y_trans = y*Math.cos(angle_radian) - x*Math.sin(angle_radian);

        double x_lt_trans = x_lt*Math.cos(angle_radian) + y_lt*Math.sin(angle_radian);
        double y_lt_trans = y_lt*Math.cos(angle_radian) - x_lt*Math.sin(angle_radian);

        double x_rb_trans = x_rb*Math.cos(angle_radian) + y_rb*Math.sin(angle_radian);
        double y_rb_trans = y_rb*Math.cos(angle_radian) - x_rb*Math.sin(angle_radian);

        boolean limit = false;
        if (x_trans < x_lt_trans) {
            x_trans = x_lt_trans;
            limit = true;
        } else if (x_trans > x_rb_trans) {
            x_trans = x_rb_trans;
            limit = true;
        }
        if (y_trans < y_rb_trans) {
            y_trans = y_rb_trans;
            limit = true;
        }else if (y_trans > y_lt_trans) {
            y_trans = y_lt_trans;
            limit = true;
        }

        if (limit) {
            angle_radian = -angle_radian;
            double x_original = x_trans*Math.cos(angle_radian) + y_trans*Math.sin(angle_radian);
            double y_original = y_trans*Math.cos(angle_radian) - x_trans*Math.sin(angle_radian);
            return new double[]{x_original,y_original};
        } else {
            return null;
        }
    }

    public static double[] pointToPointWithAngle(float x1,float y1,float x2,float y2, float angle_) {
        double angle = angle_;
        double angle_radian = Math.toRadians(angle);
        double x1_trans = x1*Math.cos(angle_radian) + y1*Math.sin(angle_radian);
        double y1_trans = y1*Math.cos(angle_radian) - x1*Math.sin(angle_radian);

        double x2_trans = x2*Math.cos(angle_radian) + y2*Math.sin(angle_radian);
        double y2_trans = y2*Math.cos(angle_radian) - x2*Math.sin(angle_radian);
        double[] differ = {x2_trans - x1_trans,y2_trans - y1_trans};
        return differ;
    }

    public static double[] getPointWithPointAngle(float x1,float y1,float differ1,float differ2, float angle_) {

        double angle = angle_;
        double angle_radian = Math.toRadians(angle);
        double x1_trans = x1*Math.cos(angle_radian) + y1*Math.sin(angle_radian);
        double y1_trans = y1*Math.cos(angle_radian) - x1*Math.sin(angle_radian);

        double x2_trans = differ1 + x1_trans;
        double y2_trans = differ2 + y1_trans;

        double x2 = x2_trans*Math.cos(-angle_radian) + y2_trans*Math.sin(-angle_radian);
        double y2 = y2_trans*Math.cos(-angle_radian) - x2_trans*Math.sin(-angle_radian);

        double[] xy = {x2,y2};
        return xy;
    }

    /**
     * 返回两点之间的距离，坐标系中的坐标
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double pointsDistance(float x1,float y1,float x2,float y2) {
        return Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
    }

    public static double[] analysisColor(String color_str){
        double[] color_rgb = new double[]{0d,0d,0d};
        try {
            int color_int = Color.parseColor(color_str);
            /*int color_b = color_int & 0xff;
            int color_g = (color_int & 0xff00) >> 8;
            int color_r = (color_int & 0xff0000) >> 16;
            ALog.i(ALog.Tag2,"191203s-Tools-analysisColor"
                    + "-color_r->"+color_r
                    + "-color_g->"+color_g
                    + "-color_b->"+color_b
            );*/

            color_rgb[2] = (color_int & 0xff) / 255d;
            color_rgb[1] = ((color_int & 0xff00) >> 8) / 255d;
            color_rgb[0] = ((color_int & 0xff0000) >> 16) / 255d;
            /*ALog.i(ALog.Tag2,"191203s-Tools-analysisColor-2"
                    + "-color_r->"+color_rgb[0]
                    + "-color_g->"+color_rgb[1]
                    + "-color_b->"+color_rgb[2]
            );*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color_rgb;
    }

    // 递归法求最大公约数
    public static int maxCommonDivisor(int m, int n) {
        if (m < n) {// 保证m>n,若m<n,则进行数据交换
            int temp = m;
            m = n;
            n = temp;
        }
        if (m % n == 0) {// 若余数为0,返回最大公约数
            return n;
        } else { // 否则,进行递归,把n赋给m,把余数赋给n
            return maxCommonDivisor(n, m % n);
        }
    }

    public static int argb(float alpha, float red, float green, float blue) {
        return ((int) (alpha * 255.0f + 0.5f) << 24) |
                ((int) (red   * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) <<  8) |
                (int) (blue  * 255.0f + 0.5f);
    }

    ////========================================



    ////========================================

    /**
     * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点
     * @param p2 终止点
     * @return t对应的点
     */
    public static PointF CalculateBezierPointForQuadratic(float t, PointF p0, PointF p1, PointF p2) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
        point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
        return point;
    }

    /**
     * B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3, t ∈ [0,1]
     *
     * @param t  曲线-时间-长度比例
     * @param p0 起始点
     * @param p1 控制点1
     * @param p2 控制点2
     * @param p3 终止点
     * @return t对应的点
     */
    public static PointF CalculateBezierPointForCubic(float t, PointF p0, PointF p1, PointF p2, PointF p3) {
        PointF point = new PointF();
        float temp = 1 - t;
        point.x = p0.x * temp * temp * temp + 3 * p1.x * t * temp * temp + 3 * p2.x * t * t * temp + p3.x * t * t * t;
        point.y = p0.y * temp * temp * temp + 3 * p1.y * t * temp * temp + 3 * p2.y * t * t * temp + p3.y * t * t * t;
        return point;
    }

    public static PointF CalculateBezierPointForCubicddd(float x, PointF p0, PointF p1, PointF p2, PointF p3) {
        PointF point = new PointF();
        float t = 0 ;
        x = p0.x * (1 - t) * (1 - t) * (1 - t) + 3 * p1.x * t * (1 - t) * (1 - t) + 3 * p2.x * t * t * (1 - t) + p3.x * t * t * t;



        float temp = 1 - t;
        point.x = p0.x * temp * temp * temp + 3 * p1.x * t * temp * temp + 3 * p2.x * t * t * temp + p3.x * t * t * t;
        point.y = p0.y * temp * temp * temp + 3 * p1.y * t * temp * temp + 3 * p2.y * t * t * temp + p3.y * t * t * t;
        return point;
    }

    //================================


}