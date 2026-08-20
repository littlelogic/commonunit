package com.badlogic.utils;

/// com.badlogic.utils.AnyRoundImage
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

public class AnyRoundImage extends androidx.appcompat.widget.AppCompatImageView {
    public AnyRoundImage(Context context) {
        super(context);
    }

    public AnyRoundImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnyRoundImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    int dp_3 = Tools.dip2px(Tools.getApplication(),3);

    private void init(){

    }

    private final Path mPath = new Path();
    float[] radii = {dp_3, dp_3,  dp_3, dp_3,  0f, 0f,  0f, 0f,};

    @Override
    protected void onDraw(Canvas canvas) {




        Rect rectF_view = new Rect(0, 0, this.getWidth(), this.getHeight());
        mPath.addRoundRect(0,0,this.getWidth(), this.getHeight(), radii, Path.Direction.CW);
        BitmapDrawable hBitmapDrawable = (BitmapDrawable)this.getDrawable();
        if (hBitmapDrawable == null) {
            return;
        }
        Bitmap hBitmap = hBitmapDrawable.getBitmap();
        if (hBitmap == null) {
            return;
        }
        RectF rectF_bmp = new RectF(0f, 0f, hBitmap.getWidth(), hBitmap.getHeight());
        canvas.clipPath(mPath, Region.Op.INTERSECT);

//        canvas.drawBitmap(hBitmap,rectF_view,rectF_bmp,null);

        super.onDraw(canvas);

    }






}
