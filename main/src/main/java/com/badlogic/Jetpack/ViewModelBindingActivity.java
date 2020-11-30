package com.badlogic.Jetpack;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.badlogic.test.TestModel;
import com.badlogic.ui.R;
import com.badlogic.ui.databinding.ViewModelActivLayoutBinding;
import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;


public class ViewModelBindingActivity extends AppCompatActivity {

    /* todo

    //1
    ActivityMainTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main_test);
    //2
    View inflate = LayoutInflater.from(this).inflate(R.layout.activity_main_test, null);
    ActivityMainTestBinding bind = DataBindingUtil.bind(inflate);
    //3
    ActivityMainTestBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main_test, null, false);


     */

    MyViewModel myViewModel;
    ViewModelActivLayoutBinding mViewModelActivLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mViewModelActivLayoutBinding = DataBindingUtil.setContentView(this,R.layout.view_model_activ_layout);
        TestModel hTestModel = new TestModel();
        hTestModel.setName("TestModel-1");
        mViewModelActivLayoutBinding.setTestA(hTestModel);

        com.badlogic.others.TestModel bbTestModel = new com.badlogic.others.TestModel();
        mViewModelActivLayoutBinding.setTestB(bbTestModel);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        mViewModelActivLayoutBinding.hTextView.setText(myViewModel.number+"");
        mViewModelActivLayoutBinding.addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.number++;
                mViewModelActivLayoutBinding.hTextView.setText(myViewModel.number+"");
                hTestModel.setName("number:"+myViewModel.number);
                bbTestModel.address.set("address:"+myViewModel.number);
            }
        });
        mViewModelActivLayoutBinding.addTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.number += 2;
                mViewModelActivLayoutBinding.hTextView.setText(myViewModel.number+"");
                hTestModel.setName("number:"+myViewModel.number);
                bbTestModel.address.set("address:"+myViewModel.number);
            }
        });


    }


}
