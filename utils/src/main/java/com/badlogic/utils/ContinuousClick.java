package com.badlogic.utils;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class ContinuousClick implements View.OnClickListener {

    private List<Long> hitList = new ArrayList();
    protected int num_target = 7;
    protected long during_target = 2600;

    public ContinuousClick(){

    }

    public ContinuousClick(int num_targe_,long during_target_){
        num_target = num_targe_;
        during_target = during_target_;
    }

    @Override
    public void onClick(View v) {
        if (num_target < 2) {
            return;
        }
        hitList.add(System.currentTimeMillis());
        if (hitList.size() > num_target) {
            hitList.remove(0);
        }
        if (hitList.size() == num_target) {
            long differ = hitList.get(num_target - 1) - hitList.get(0);
            if (differ < during_target) {
                hitList.clear();
                onContinuousClick(v);
            }
        }
    }

    public abstract void onContinuousClick(View v);
}