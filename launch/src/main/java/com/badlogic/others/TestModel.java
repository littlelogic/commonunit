package com.badlogic.others;
//com.badlogic.others.TestModel

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.badlogic.ui.BR;

public class TestModel {

    /*
    我们刚刚介绍的通知UI更新的方法是用User类继承自BaseObservable，然后在getter上添加注解、
    在setter中添加notify方法，这感觉总是有点麻烦，步骤繁琐，于是，
    Google推出ObservableFields类，使用它我们可以简化我们的Model类，如：
     */
    public final ObservableField<String> address = new ObservableField<>();


    private String Type = "TestModel-Type";

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
