package com.badlogic.utils;

// com.badlogic.utils.LinearGradientTextView
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LinearGradientTextView extends TextView {

    public LinearGradientTextView(Context context) {
        super(context);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {

    }


    private int[] mColors= new int[]{
            Color.parseColor("#808080"),
            Color.parseColor("#CC423C")};

    public void setColors(int ... mColors_){
        mColors = mColors_;
    }
    /*public void setColors(int[] mColors_){
        mColors = mColors_;
    }*/

    int width_last;
    int height_last;

    @Override
    protected void onDraw(Canvas canvas) {

        if (this.getPaint() != null) {
            if (width_last != this.getWidth() || height_last != this.getHeight()) {
                width_last = this.getWidth();
                height_last = this.getHeight();
                Shader mShader = new LinearGradient(0, 0, width_last, 0, mColors, null, Shader.TileMode.CLAMP);
                this.getPaint().setShader(mShader);
            }
        }

        super.onDraw(canvas);
    }


}
