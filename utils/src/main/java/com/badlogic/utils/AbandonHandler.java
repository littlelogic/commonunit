package com.badlogic.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class AbandonHandler extends Handler {

    private volatile boolean tolerant = false;
    private volatile boolean abandon = false;

    public boolean isAbandon() {
        return abandon;
    }

    public void setAbandon(boolean abandon) {
        this.abandon = abandon;
    }

    public AbandonHandler() {
        super();
    }

    public AbandonHandler(Looper looper) {
        super(looper);
    }

    public AbandonHandler(boolean tolerant) {
        super();
        this.tolerant = tolerant;
    }

    public AbandonHandler(Looper looper, boolean tolerant) {
        super(looper);
        this.tolerant = tolerant;
    }

    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void test() {
        this.sendEmptyMessage(1);
        this.sendMessageAtTime(null,12);
        this.post(null);
        this.postDelayed(null,23);
        this.postDelayed(null,"token",23);
        this.postAtTime(null,23);
    }


    /**
     * postDelayed,post,都会最终调到此处
     * @param msg
     * @param uptimeMillis
     * @return
     */
    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        if (abandon) {
            return false;
        }
        return super.sendMessageAtTime(msg,uptimeMillis);
    }

    @Override
    public void dispatchMessage(@NonNull Message msg) {
        if (abandon) {
            return;
        }
        if (tolerant) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            super.dispatchMessage(msg);
        }
    }


};