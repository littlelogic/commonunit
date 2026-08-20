package com.badlogic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.media.editor.util.Tools;

public class DrawSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private boolean bothMode = false;
    private int dp1;
    private int dp2_5;
    private Rect centreRect = new Rect();
    private RectF perRect = new RectF();
    private Paint centrePaint = new Paint();
    private Paint perPaint = new Paint();
    private Paint backPaint = new Paint();
    private int thumbWidth;
    private int centerX;
    private int centerY;
    private int centerProcess;
    private int halfThumbWidth;
    private int signProcess = -1;
    private Drawable signDrawable;
    private int signWidth;
    private int signheight;
    private RectF rectF = new RectF();
    private RectF backRectF = new RectF();
    private Rect rect_bmp = new Rect();

    public DrawSeekBar(Context context) {
        super(context);
        init();
    }

    public DrawSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Drawable thumbDr;
    Bitmap thumbBmp;

    public void init() {
//        dp1 = Tools.dip2px(this.getContext(), 1f);
//        dp2_5 = Tools.dip2px(this.getContext(), 2.5f);
//        centrePaint.setColor(0xff4D4D4D);
//        signWidth = ScreenUtils.dipConvertPx(getContext(), 9);
//        signheight = ScreenUtils.dipConvertPx(getContext(), 5);
//        signDrawable = getResources().getDrawable(R.drawable.videoedit_common_slider_default);
//        initProcessColorByEnable(isEnabled());
//
//        backPaint.setColor(Color.parseColor("#D8D8D8"));
//        backPaint.setAntiAlias(true);
//
//        this.getThumb().setAlpha(0);///this.setThumb(null);
//        thumbDr = Tools.getDrawableByNew(this.getContext(),R.drawable.seekbar_thumb_selector);
//        thumbBmp = Tools.getBitmapOriginalBgResId_b(this.getContext().getResources(),R.drawable.seekbar_thumb_selector);
//        rect_bmp = new Rect(0,0,thumbBmp.getWidth(),thumbBmp.getHeight());
    }

    /*@Override
    public void setEnabled(boolean enabled) {
        if (isEnabled()) {
            if (!enabled) {
                setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_background_disenable));
            }
        } else {
            if (enabled) {
                setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_background));
            }
        }
        super.setEnabled(enabled);
        initProcessColorByEnable(enabled);
        postInvalidate();
    }*/

    public void initProcessColorByEnable(boolean enable) {
        perPaint.setColor(enable ? 0xff00CAFF : 0x3300CAFF);
        backPaint.setColor(enable ? Color.parseColor("#D8D8D8") : Color.parseColor("#282828"));
    }

    public void removeSignProgress() {
        signProcess = -1;
        postInvalidate();
    }

    public void setSignProgress(int progress) {
        signProcess = progress;
        postInvalidate();
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
    }

    @Override
    public void setProgress(int progress, boolean animate) {
        super.setProgress(progress, animate);
    }

    /**
     * @param mark 在onFinishInflate之调用
     */
    public void setBothMode(boolean mark) {
        bothMode = mark;
        invalidate();
    }

    public boolean isBothMode() {
        return bothMode;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX = getWidth() >> 1;
        centerY = getHeight() >> 1;
        centreRect.left = centerX - dp1;
        centreRect.right = centerX + dp1;
        centreRect.top = centerY - (dp1 << 3);
        centreRect.bottom = centerY + (dp1 << 3);
        thumbWidth = getThumb().getBounds().right - getThumb().getBounds().left;
        halfThumbWidth = thumbWidth >> 1;
        perRect.top = centerY - dp1;
        perRect.bottom = centerY + dp1;
        centerProcess = getMax() >> 1;
        int signX = (int) ((float) signProcess / (float) getMax() * (getWidth() - thumbWidth) + halfThumbWidth);
        int halfSignWidth = signWidth >> 1;
        int signBottom = centerY - 5 * dp1;
        signDrawable.setBounds(signX - halfSignWidth, signBottom - signheight, signX + halfSignWidth, signBottom);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (!bothMode) {
            onDraw_ssl(canvas);
            return;
        }
        setBackground(null);
        backRectF.left = thumbWidth / 2;
        backRectF.right = getWidth() - thumbWidth / 2;
        backRectF.top = centerY - dp2_5 - 1;
        backRectF.bottom = centerY + dp2_5 + 1;
        canvas.drawRoundRect(backRectF , 4.5f * Tools.dip2px(getContext() , 1),4.5f * Tools.dip2px(getContext() , 1), backPaint);
        if (bothMode) {
            canvas.drawRect(centreRect, centrePaint);
        }
        super.onDraw(canvas);
        Rect rect = getThumb().getBounds();
        if (bothMode) {
            int progress = getProgress();
            if (progress <= centerProcess) {
                perRect.left = rect.right;
                perRect.right = centerX;
            } else {
                perRect.left = centerX;
                perRect.right = rect.left;
            }
        } else {
            perRect.left = halfThumbWidth;
            perRect.right = rect.left;
            if (perRect.right <= perRect.left) {
                perRect.right = perRect.left;
            }
        }
        if (!bothMode) {
            if (perRect.left < perRect.right){
                rectF.left = perRect.left;
                rectF.right = perRect.right + 5 * Tools.dip2px(getContext() , 1) ;
                rectF.top = centerY - dp2_5 -1;
                rectF.bottom = centerY + dp2_5 +1;
                canvas.drawRoundRect(rectF , 4.5f * Tools.dip2px(getContext() , 1),4.5f * Tools.dip2px(getContext() , 1), perPaint);
            }
        } else {
            rectF.left = perRect.left;
            rectF.right = perRect.right;
            rectF.top = centerY - dp2_5;
            rectF.bottom = centerY + dp2_5;
            if (rectF.left < rectF.right){
                if (rectF.right > getWidth() / 2) {
                    rectF.right = rectF.right + 5 * Tools.dip2px(getContext() , 1);
                }
                canvas.drawRect(rectF , perPaint);
            }
        }
        if (signProcess >= 0) {
            signDrawable.draw(canvas);
        }
    }

    protected synchronized void onDraw_ssl(Canvas canvas) {
        setBackground(null);
        backRectF.left = thumbWidth / 2;
        backRectF.right = getWidth() - thumbWidth / 2;
        backRectF.top = centerY - dp2_5 - 1;
        backRectF.bottom = centerY + dp2_5 + 1;
        canvas.drawRoundRect(backRectF , 4.5f * Tools.dip2px(getContext() , 1),4.5f * Tools.dip2px(getContext() , 1), backPaint);
        if (bothMode) {
            canvas.drawRect(centreRect, centrePaint);
        }


//        perPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
//        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);


        super.onDraw(canvas);

        Rect rect = getThumb().getBounds();
        if (bothMode) {
            int progress = getProgress();
            if (progress <= centerProcess) {
                perRect.left = rect.right;
                perRect.right = centerX;
            } else {
                perRect.left = centerX;
                perRect.right = rect.left;
            }
        } else {
            perRect.left = halfThumbWidth;
            perRect.right = rect.left;
            if (perRect.right <= perRect.left) {
                perRect.right = perRect.left;
            }
        }
        if (!bothMode) {
            if (perRect.left < perRect.right){
                rectF.left = perRect.left;
                rectF.right = perRect.right + 5 * Tools.dip2px(getContext() , 1)  ;
                rectF.top = centerY - dp2_5 -1;
                rectF.bottom = centerY + dp2_5 +1;
                canvas.drawRoundRect(rectF , 4.5f * Tools.dip2px(getContext() , 1),4.5f * Tools.dip2px(getContext() , 1), perPaint);
            }
        } else {
            rectF.left = perRect.left;
            rectF.right = perRect.right;
            rectF.top = centerY - dp2_5;
            rectF.bottom = centerY + dp2_5;
            if (rectF.left < rectF.right){
                if (rectF.right > getWidth() / 2) {
                    rectF.right = rectF.right + 5 * Tools.dip2px(getContext() , 1);
                }
                canvas.drawRect(rectF , perPaint);
            }
        }


        thumbDr.setBounds(rect);
        thumbDr.draw(canvas);
        canvas.drawBitmap(thumbBmp,rect_bmp,rect,null);

        if (signProcess >= 0) {
            signDrawable.draw(canvas);
        }
    }
}
