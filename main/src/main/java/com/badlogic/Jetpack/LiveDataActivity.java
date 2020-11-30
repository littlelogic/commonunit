package com.badlogic.Jetpack;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.badlogic.ui.R;
import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;


public class LiveDataActivity extends AppCompatActivity {

    MyViewModelWithLiveData mMyViewModelWithLiveData;
    TextView showTv ;
    TextView addOneTv;
    TextView addTwoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.jetpack_a);
        showTv= this.findViewById(R.id.hTextView);
        addOneTv = this.findViewById(R.id.add_one);
        addTwoTv = this.findViewById(R.id.add_two);

        mMyViewModelWithLiveData = ViewModelProviders.of(this).get(MyViewModelWithLiveData.class);
        mMyViewModelWithLiveData.getLinkNumber().observe(this,new Observer<Integer>(){
            @Override
            public void onChanged(Integer o) {
                showTv.setText( o + "");
            }
        });

        addOneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyViewModelWithLiveData.addLinkNumber(1);
            }
        });
        addTwoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyViewModelWithLiveData.addLinkNumber(2);
            }
        });


    }


}
