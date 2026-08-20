package com.badlogic.utils;

/// com.badlogic.utils.MyImageView

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyImageView extends androidx.appcompat.widget.AppCompatImageView {

    public MyImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public MyImageView(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected void init(Context context) {
        this.setScaleType(ScaleType.FIT_XY);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        ALog.i("------");
        if (false) {
            super.onDraw(canvas);
        }

        if (this.getDrawable() == null) {
            return; // couldn't resolve the URI
        }

        if (this.getDrawable().getIntrinsicWidth() == 0 || this.getDrawable().getIntrinsicHeight() == 0) {
            return;     // nothing to draw (empty bounds)
        }

        if (this.getImageMatrix() == null && this.getPaddingTop() == 0 && this.getPaddingLeft() == 0) {
            this.getDrawable().draw(canvas);
        } else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            if (getCropToPadding()) {
                final int scrollX = this.getScrollX();
                final int scrollY = this.getScrollY();
                canvas.clipRect(scrollX + this.getPaddingLeft(), scrollY + this.getPaddingTop(),
                        scrollX + this.getRight() - this.getLeft() - this.getPaddingRight(),
                        scrollY + this.getBottom() - this.getTop() - this.getPaddingBottom());

            }

            canvas.translate(this.getPaddingLeft(), this.getPaddingTop());

            if (this.getImageMatrix() != null) {
                canvas.concat(this.getImageMatrix());
            }
            this.getDrawable().draw(canvas);
            canvas.restoreToCount(saveCount);
        }


    }

}
