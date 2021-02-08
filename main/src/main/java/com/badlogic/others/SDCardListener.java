package com.badlogic.others;

import android.os.FileObserver;
import android.util.Log;

public class SDCardListener extends FileObserver {

    /*
todo
    全局变量的储存实例引用的方式：需要在长生命周期，引用,被系统回收就接受不到事件了

    CCESS，即文件被访问
    MODIFY，文件被 修改
    ATTRIB，文件属性被修改，如 chmod、chown、touch 等
    CLOSE_WRITE，可写文件被 close
    CLOSE_NOWRITE，不可写文件被 close
    OPEN，文件被 open
    MOVED_FROM，文件被移走,如 mv
    MOVED_TO，文件被移来，如 mv、cp
    CREATE，创建新文件
    DELETE，文件被删除，如 rm
    DELETE_SELF，自删除，即一个可执行文件在执行时删除自己
    MOVE_SELF，自移动，即一个可执行文件在执行时移动自己
    CLOSE，文件被关闭，等同于(IN_CLOSE_WRITE | IN_CLOSE_NOWRITE)
    ALL_EVENTS，包括上面的所有事件
     */
    public SDCardListener(String path) {
        /*
         * 这种构造方法是默认监听所有事件的,如果使用super(String,int)这种构造方法，
         * 则int参数是要监听的事件类型.
         */
        super(path);
    }

    @Override
    public void onEvent(int event, String path) {
        switch(event) {
            case FileObserver.ALL_EVENTS:
                Log.d("all", "path:"+ path);
                break;
            case FileObserver.CREATE:
                Log.d("Create", "path:"+ path);
                break;
        }
    }
}