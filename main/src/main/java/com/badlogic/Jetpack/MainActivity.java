package com.badlogic.Jetpack;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.ui.R;
import com.badlogic.utils.Tools;


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
