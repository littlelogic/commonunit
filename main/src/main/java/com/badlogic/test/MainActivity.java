package com.badlogic.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ALog.i("wjw","--->"+ Tools.getVersion(this));
        ///005e
    }


}
