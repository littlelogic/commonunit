package com.badlogic.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;


/**
 *
 */
public class PassThroughDialog extends Dialog {

    private final View contentView;
    private Activity mActivity;

    public PassThroughDialog(@NonNull Activity context, View contentView) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mActivity = context;
        this.contentView = contentView;
        setContentView(contentView);

        // 不允许返回键或外部点击关闭
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        // 背景透明
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 大小 = 内容 View 自适应
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    boolean selfTouchEventMark = false;

    Handler showDialogHandler = new Handler(Looper.getMainLooper());

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        float rawX = ev.getRawX();
        float rawY = ev.getRawY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 内容区域坐标
            int[] location = new int[2];
            contentView.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + contentView.getWidth();
            int bottom = top + contentView.getHeight();
            if (rawX >= left && rawX <= right && rawY >= top && rawY <= bottom) {
                // 点击在内容区域 → 自己处理
                selfTouchEventMark = true;
            } else {
                selfTouchEventMark = false;
            }
        }

        if (selfTouchEventMark) {
            return super.dispatchTouchEvent(ev);
        } else {
            // 点击在内容区域外 → 透传给底层 Activity
            Activity owner = getOwnerActivity();
            if (owner == null) {
                owner = mActivity;
            }
            if (owner != null) {

                showDialogHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 点击在内容区域外 → 透传给底层 Activity
                        Activity owner = getOwnerActivity();
                        if (owner == null) {
                            owner = mActivity;
                        }
                        if (owner != null) {
                            MotionEvent newEvent = MotionEvent.obtain(
                                    ev.getDownTime(),
                                    ev.getEventTime(),
                                    ev.getAction(),
                                    rawX,
                                    rawY,
                                    ev.getMetaState()
                            );
                            boolean handled = owner.getWindow().superDispatchTouchEvent(newEvent);
                            newEvent.recycle();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    public boolean onKeyDown11(int keyCode, @NonNull KeyEvent event) {
        Activity owner = getOwnerActivity();
        if (owner == null) {
            owner = mActivity;
        }
        if (owner != null) {
            Activity finalOwner = owner;
            showDialogHandler.post(new Runnable() {
                @Override
                public void run() {
                    finalOwner.onKeyDown(keyCode,event);

                    finalOwner.getWindow().superDispatchKeyEvent(event);
                    finalOwner.getWindow().superDispatchKeyShortcutEvent(event);
                }
            });
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public boolean onKeyUp11(int keyCode, @NonNull KeyEvent event) {
        Activity owner = getOwnerActivity();
        if (owner == null) {
            owner = mActivity;
        }
        if (owner != null) {
            Activity finalOwner = owner;
            showDialogHandler.post(new Runnable() {
                @Override
                public void run() {
                    finalOwner.onKeyUp(keyCode,event);
                }
            });
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }


    public boolean onKeyDown22(int keyCode, @NonNull KeyEvent event) {
        return false;
    }

    public boolean onKeyUp22(int keyCode, @NonNull KeyEvent event) {
        return false;
    }

    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        Activity owner = getOwnerActivity();
        if (owner == null) {
            owner = mActivity;
        }
        if (owner != null) {
            Activity finalOwner = owner;
            showDialogHandler.post(new Runnable() {
                @Override
                public void run() {
                    finalOwner.dispatchKeyEvent(event);
                    ////finalOwner.getWindow().superDispatchKeyEvent(event);
                }
            });
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onBackPressed() {
        Activity owner = getOwnerActivity();
        if (owner == null) {
            owner = mActivity;
        }
        if (owner != null) {
            Activity finalOwner = owner;
            showDialogHandler.post(new Runnable() {
                @Override
                public void run() {
                    finalOwner.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 工具方法：直接通过布局 id 创建并显示
     */
    public static PassThroughDialog show(@NonNull Activity activity, int layoutResId) {
        View view = activity.getLayoutInflater().inflate(layoutResId, null);
        PassThroughDialog dialog = new PassThroughDialog(activity, view);
        dialog.show();
        return dialog;
    }

    /**
     * 工具方法：直接通过 View 创建并显示
     */
    public static PassThroughDialog show(@NonNull Activity activity, View view) {
        PassThroughDialog dialog = new PassThroughDialog(activity, view);
        dialog.show();
        return dialog;
    }
}
