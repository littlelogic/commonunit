package com.badlogic.Jetpack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.ui.R;
import com.badlogic.utils.Tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity {

    MyViewModel myViewModel;
    TextView showTv ;
    TextView addOneTv;
    TextView addTwoTv;
    ///----
    LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        int statusBarHeight = Tools.getStatusBarHeight1(this);
        this.findViewById(R.id.OutScrollView).setPadding(0,statusBarHeight,0,0);
    }

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


}
