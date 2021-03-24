package com.badlogic.Jetpack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.kotlin.KotlinActivity;
import com.badlogic.ui.R;
import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.badlogic.utils.ALog._b;
import static com.badlogic.utils.ALog._i;


public class MainActivity extends AppCompatActivity {

    MyViewModel myViewModel;
    TextView showTv ;
    TextView addOneTv;
    TextView addTwoTv;
    ///----
    LinearLayout contentLayout;

    private ScreenBroadcastReceiver screenBroadcastReceiver = null;
    private Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.main_activ);
        setStatusBarTransparent();
        contentLayout = this.findViewById(R.id.contentLayout);
        contentLayout.removeAllViews();
        ///----
        addTextView("ViewModelBindingActivity",new Runnable(){
            @Override
            public void run() {
                Intent mIntent=new Intent(MainActivity.this, ViewModelBindingActivity.class);
                MainActivity.this.startActivity(mIntent);
            }
        });
        addTextView("LiveDataActivity",new Runnable(){
            @Override
            public void run() {
                Intent mIntent=new Intent(MainActivity.this, LiveDataActivity.class);
                MainActivity.this.startActivity(mIntent);
            }
        });
        addTextView("RoomDatabase",new Runnable(){
            @Override
            public void run() {
                Intent mIntent=new Intent(MainActivity.this, com.example.roombasic.MainActivity.class);
                MainActivity.this.startActivity(mIntent);
            }
        });
        addTextView("ListAdapter",new Runnable(){
            @Override
            public void run() {
                Intent mIntent=new Intent(MainActivity.this, com.teaphy.diffutildemo.MainActivity.class);
                MainActivity.this.startActivity(mIntent);
            }
        });
        addTextView("navigation",new Runnable(){
            @Override
            public void run() {
                Intent mIntent=new Intent(MainActivity.this, com.example.android.codelabs.navigation.MainActivity.class);
                MainActivity.this.startActivity(mIntent);
            }
        });
        addTextView("KotlinActivity",new Runnable(){
            @Override
            public void run() {
                KotlinActivity.startMe(MainActivity.this);
            }
        });



        JSONObject object2 =new JSONObject();
        try {
            String null_str = null;
            object2.put("baseUrl","baseUrl" );
            object2.put("from_title","from_title" );
            object2.put("from_title",null_str );
            ALog.i("MainActivity-onCreate-JSONObject组装json串，'null'会被忽略-->"+object2.toString());

        }catch (Exception e){

        }

        ALog.i(_i?_b: "MainActivity-onCreate-JSONObject组装json串，'null'会被" +
                "忽略-->"+object2.toString());

        ALog.i(_i?_b: "210206t-MainActivity-onCreate-1111-->" +
                "-test2->" + Object.class + "\n"+
                "-test2->" + 2222222 + "\n"+
                "-test2->" + 3333333 +
                "");



        ALog.i(()-> "210206t-MainActivity-onCreate-1111-->" + object2.toString());
        ALog.ii(() -> {

        });
        ALog.i2(() -> {
            String ddd = object2.toString();
            return "210206t-MainActivity-onCreate-1111-ddd->"+ddd;
        });

    }

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res  原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath, String encoding) {
        boolean flag = true;
        File distFile = new File(filePath);
        if (!distFile.getParentFile().exists()) {
            distFile.getParentFile().mkdirs();
        }
        /*
         todo 实现Closeable的类声明
         */
        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(res));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(distFile), encoding))) {
            char buf[] = new char[2048];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
        } catch (Exception e) {
            return flag;
        }
        return flag;
    }

    public static boolean string2File2(String res, String filePath, String encoding) {
        boolean flag = true;
        File distFile = new File(filePath);
        if (!distFile.getParentFile().exists()) {
            distFile.getParentFile().mkdirs();
        }
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(distFile), encoding));
            char buf[] = new char[2048];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
        } catch (Exception e) {
            return flag;
        } finally {
            try {
                if(bufferedReader!=null){
                    bufferedReader.close();
                }
                if(bufferedWriter!=null){
                    bufferedWriter.close();
                }
            } catch (IOException e2) {
                //...
            }
        }
        return flag;
    }

    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                window.setStatusBarColor(0xFFFFCB00);
            }
        }
        ///------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);//将状态栏设置成透明色
            getWindow().setNavigationBarColor(Color.TRANSPARENT);//将导航栏设置为透明色
        }
        int statusBarHeight = Tools.getStatusBarHeight1(this);
        this.findViewById(R.id.OutScrollView).setPadding(0,statusBarHeight,0,0);
    }

    /*private void setStatusBarTransparent_2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        } else {
            window.statusBarColor = 0xFF_FF_CB_00.toInt()
        }
    }*/

    public static void setStatusBarColor(Activity activity, int color, boolean fullScreen) {
        if (Build.VERSION.SDK_INT >= 21) {
            if(activity == null || activity.isDestroyed())return;
            Window window = activity.getWindow();
            if (fullScreen) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Color.WHITE == color) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    MIUISetStatusBarLightMode(window, true);
                    FlymeSetStatusBarLightMode(window, true);
                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
            } else {
                if (Color.WHITE == color && !fullScreen) {
                    window.setStatusBarColor(Color.parseColor("#A3A3A3"));
                }
            }
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    private void addTextView(String text,Runnable hRunnable){
        TextView hTextView = new TextView(this);
        hTextView.setText(text);
        hTextView.setGravity(Gravity.CENTER);
        hTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hRunnable != null) {
                    hRunnable.run();
                }
            }
        });
        LinearLayout.LayoutParams hParam = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                Tools.dip2px(this,36));
        hParam.bottomMargin = Tools.dip2px(this,10);
        hTextView.setBackground(Tools.getCornerDrawable(0x99000000,0));
        hTextView.setTextColor(Color.WHITE);
        hTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
        contentLayout.addView(hTextView,hParam);

    }

    ///========================================================================


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        //注册这个广播
        registerScreenBroadcastReceiver();
    }

    private void registerScreenBroadcastReceiver() {
        screenBroadcastReceiver = new ScreenBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);//当屏幕锁屏的时候触发
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);//当屏幕解锁的时候触发
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);//当用户重新唤醒手持设备时触发
        context.registerReceiver(screenBroadcastReceiver, intentFilter);
        Log.i("screenBR", "screenBroadcastReceiver注册了");
    }

    /*重写广播
    首先我们再次强调ACTION_SCREEN_ON和ACTION_SCREEN_OFF只能通过动态注册的方式
    （代码内context.register和unregister），而ACTION_USER_PRESENT则是动态、静态注册两种方式都可以。
    那么我们的突破口便是：我们可以动态地注册一个关于屏幕解锁后（ACTION_USER_PRESENT）的广播者，
    并且在这个广播的onReceive方法中实现我们要做的一些操作。例如我们可以开启一个Service服务，
    用于注册我们所想要的这个Broadcast Receiver
     */
    class ScreenBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String strAction = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(strAction)){
                //屏幕锁屏
                Log.i("screenBR", "屏幕锁屏：ACTION_SCREEN_OFF触发");
                Toast.makeText(context, "锁屏了", Toast.LENGTH_SHORT).show();
            }else if (Intent.ACTION_SCREEN_ON.equals(strAction)){
                //屏幕解锁(实际测试效果，不能用这个来判断解锁屏幕事件)
                //【因为这个是解锁的时候触发，而解锁的时候广播还未注册】
                Log.i("screenBR", "屏幕解锁：ACTION_SCREEN_ON触发");
                Toast.makeText(context, "解锁了", Toast.LENGTH_SHORT).show();
            }else if (Intent.ACTION_USER_PRESENT.equals(strAction)){
                //屏幕解锁(该Action可以通过静态注册的方法注册)
                //在解锁之后触发的，广播已注册
                Log.i("screenBR", "屏幕解锁：ACTION_USER_PRESENT触发");
                Toast.makeText(context, "解锁了", Toast.LENGTH_SHORT).show();
            }else{
                //nothing
            }
        }

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        context.unregisterReceiver(screenBroadcastReceiver);
        Log.i("screenBR", "screenBroadcastReceiver取消注册了");
    }






}
