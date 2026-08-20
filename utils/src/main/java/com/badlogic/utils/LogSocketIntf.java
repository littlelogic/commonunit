package com.badlogic.utils;

import android.content.Context;

import com.badlogic.socket.LogWebSocketHelper;

public interface LogSocketIntf {

    public void sendLog( String logMessage) ;

    public void sendStructuredLog(String level, String tag, String message);

    public void launch(Context context);

    public void launch(Context context,int port_out);

}
