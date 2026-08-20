package com.badlogic.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TextFragment extends Fragment {

    String text="";
    int color = 0xffffffff;

    public void setColor(String text_,int color_){
        text = text_;
        color = color_;
    }

    public TextFragment(String text_,int color_){
        this.text = text;
        color = color_;
    }

    public TextFragment(String text){
        this.text = text;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView mTextView = new TextView(container.getContext());
        mTextView.setBackgroundColor(color);
        mTextView.setText(text);
        mTextView.setTextColor(Color.BLACK);
        mTextView.setTextSize(30);
        mTextView.setGravity(Gravity.CENTER);
        return mTextView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
