package com.badlogic.test;
//com.badlogic.test.TestModel

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.badlogic.ui.BR;

public class TestModel extends BaseObservable {

    private String name;

    /*
    因为Observable是个接口，Google为我们提供了一个BaseObservable类，
    我们只要把Model类继承自它就获得了通知UI更新数据的能力了，
    然后再getter方法上添加Bindable注解，在setter方法中使用notifying提醒UI更新数据。
     */
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        //todo
        notifyPropertyChanged(BR.name);
    }
}
