package com.badlogic.socket;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.badlogic.socket.NetworkUtils;
import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * WebSocket实时日志传输服务
 */
public class LogWebSocketHelper extends HandlerThread{


    private static final String TAG = "LogWebSocketService";
    public static final int PORT_DEAFULT = 8127;
    public static volatile int PORT = PORT_DEAFULT;

    private MyWebSocketServer webSocketServer;
    private ScheduledExecutorService heartbeatExecutor;
    private String localIpAddress = "";

    private volatile Handler handler = null;
    private volatile Context appContext;

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        handler = new Handler(getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dealMessage(msg);
            }
        };

        {
            Message message = Message.obtain();
            message.what = StartWebSocketServer;
            message.obj = PORT;
            handler.sendMessage(message);
            String ip = NetworkUtils.getLocalIpAddress(appContext);
            String text = "服务器运行中\nIP: " + ip + ":"+ LogWebSocketHelper.PORT+"\n" +
                    "在电脑浏览器访问: http://" + ip + ":"+ LogWebSocketHelper.PORT;
            Log.i(TAG,text);
        }

    }


    //==========================================

    private static class SingletonHolder{
        private static LogWebSocketHelper instance = new LogWebSocketHelper();
    }

    private LogWebSocketHelper(){
        super("LogWebSocketHelper");
    }
    public static LogWebSocketHelper getInstance(){
        return SingletonHolder.instance;
    }

    //===========================================================

    public void launch(Context context){
        launch(context,PORT_DEAFULT);
    }

    public void launch(Context context,int port_out){
        appContext = context.getApplicationContext();
        // 获取本机IP地址
        PORT = port_out;
        start();
    }

    //===========================================================

    private void dealMessage(Message msg) {
        if (msg.what == StartWebSocketServer) {

            int port_out = (int)msg.obj;
            // 获取本机IP地址
            localIpAddress = NetworkUtils.getLocalIpAddress(appContext);
            PORT = port_out;
            // 启动WebSocket服务器
            startWebSocketServer();
            // 启动心跳检测
            startHeartbeat();
            Log.i(TAG, "WebSocket服务已启动，IP: " + localIpAddress + ":" + PORT);
        } else if (msg.what == SEND_LOG) {
            String log = (String)msg.obj;
            if (webSocketServer != null && log != null) {
                webSocketServer.broadcastLog(log);
            }
        } else if (msg.what == SEND_STRUCTURED_LOG) {
            String logJson = (String)msg.obj;
            if (webSocketServer != null && logJson != null) {
                webSocketServer.broadcastJson(logJson);
            }
        }
    }


    private void startWebSocketServer() {
        try {
            webSocketServer = new MyWebSocketServer(new InetSocketAddress(PORT));
            webSocketServer.setConnectionLostTimeout(10);
            webSocketServer.start();
        } catch (Exception e) {
            Log.e(TAG, "启动WebSocket服务器失败", e);
        }
    }

    private void startHeartbeat() {
        heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
        heartbeatExecutor.scheduleAtFixedRate(() -> {
            if (webSocketServer != null) {
                webSocketServer.broadcastPing();
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public static final int StartWebSocketServer = 1;
    public static final String Key_Port = "Key_Port";
    public static final int SEND_LOG = 2;
    public static final String SEND_LOG_MSG = "log";
    public static final int SEND_STRUCTURED_LOG = 3;
    public static final String SEND_STRUCTURED_LOG_MSG = "log_entry";

    /**
     * 发送日志到所有连接的客户端
     */
    public void sendLog( String logMessage) {
        Message message = Message.obtain();
        message.what = SEND_LOG;
        message.obj = logMessage;
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    /**
     * 发送结构化日志
     */

//    /**
//     *  ['INFO', 'WARN', 'ERROR', 'DEBUG']
//
//     */
    public void sendStructuredLog(String level, String tag, String message) {
        sendStructuredLog(new LogEntry(level,tag,message));
    }

    public void sendInfoLog(String tag, String message) {
        sendStructuredLog(new LogEntry("INFO",tag,message));
    }

    public void sendDebugLog(String tag, String message) {
        sendStructuredLog(new LogEntry("DEBUG",tag,message));
    }

    public void sendWarnLog(String tag, String message) {
        sendStructuredLog(new LogEntry("WARN",tag,message));
    }

    public void sendErrorLog(String tag, String message) {
        sendStructuredLog(new LogEntry("ERROR",tag,message));
    }


    public void sendStructuredLog(LogEntry logEntry) {
        Message message = Message.obtain();
        message.what = SEND_LOG;
        message.obj = logEntry;
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    public void onDestroy() {
        Log.i(TAG, "WebSocket服务停止");

        try {
            if (webSocketServer != null) {
                try {
                    webSocketServer.stop(1000);
                } catch (Exception e) {
                    Log.e(TAG, "停止WebSocket服务器失败", e);
                }
            }

            if (heartbeatExecutor != null) {
                heartbeatExecutor.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * WebSocket服务器实现
     */
    private class MyWebSocketServer extends WebSocketServer {
        private final Map<WebSocket, ClientInfo> clients = new ConcurrentHashMap<>();
        private final Gson gson = new Gson();

        public MyWebSocketServer(InetSocketAddress address) {
            super(address);
            setReuseAddr(true);
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            ClientInfo clientInfo = new ClientInfo(
                    handshake.getFieldValue("User-Agent"),
                    conn.getRemoteSocketAddress().toString(),
                    System.currentTimeMillis()
            );
            clients.put(conn, clientInfo);

            Log.i(TAG, "新客户端连接: " + clientInfo);

            // 发送欢迎消息和连接信息
            Map<String, Object> welcomeMsg = new HashMap<>();
            welcomeMsg.put("type", "welcome");
            welcomeMsg.put("message", "连接成功，开始接收日志");
            welcomeMsg.put("server_time", System.currentTimeMillis());
            welcomeMsg.put("client_count", clients.size());

            conn.send(gson.toJson(welcomeMsg));

            // 通知所有客户端连接数变化
            broadcastClientCount();
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            ClientInfo removed = clients.remove(conn);
            if (removed != null) {
                Log.i(TAG, "客户端断开: " + removed + ", 原因: " + reason);
                broadcastClientCount();
            }
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            Log.d(TAG, "收到客户端消息: " + message);

            try {
                Map<String, Object> msgMap = gson.fromJson(message, Map.class);
                String type = (String) msgMap.get("type");

                switch (type) {
                    case "ping":
                        // 响应心跳
                        Map<String, Object> pong = new HashMap<>();
                        pong.put("type", "pong");
                        pong.put("server_time", System.currentTimeMillis());
                        conn.send(gson.toJson(pong));
                        break;

                    case "command":
                        // 处理客户端命令
                        handleCommand(conn, msgMap);
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, "解析客户端消息失败", e);
            }
        }

        @Override
        public void onMessage(WebSocket conn, ByteBuffer message) {
            Log.d(TAG, "收到二进制消息，长度: " + message.remaining());
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            Log.e(TAG, "WebSocket错误", ex);
            if (conn != null) {
                clients.remove(conn);
            }
        }

        @Override
        public void onStart() {
            Log.i(TAG, "WebSocket服务器启动成功，端口: " + PORT);
        }

        /**
         * 广播日志到所有客户端
         */
        public void broadcastLog(String log) {
            Map<String, Object> logMsg = new HashMap<>();
            logMsg.put("type", "log");
            logMsg.put("timestamp", System.currentTimeMillis());
            logMsg.put("content", log);
            logMsg.put("level", "INFO");

            String json = gson.toJson(logMsg);
            broadcast(json);
        }

        /**
         * 广播JSON消息
         */
        public void broadcastJson(String json) {
            broadcast(json);
        }

        /**
         * 广播客户端数量变化
         */
        private void broadcastClientCount() {
            Map<String, Object> countMsg = new HashMap<>();
            countMsg.put("type", "client_count");
            countMsg.put("count", clients.size());
            countMsg.put("timestamp", System.currentTimeMillis());

            broadcast(gson.toJson(countMsg));
        }

        /**
         * 发送心跳ping
         */
        public void broadcastPing() {
            Map<String, Object> pingMsg = new HashMap<>();
            pingMsg.put("type", "ping");
            pingMsg.put("server_time", System.currentTimeMillis());

            broadcast(gson.toJson(pingMsg));
        }

        /**
         * 处理客户端命令
         */
        private void handleCommand(WebSocket conn, Map<String, Object> command) {
            String cmd = (String) command.get("command");

            Map<String, Object> response = new HashMap<>();
            response.put("type", "command_response");
            response.put("command", cmd);
            response.put("timestamp", System.currentTimeMillis());

            switch (cmd) {
                case "get_clients":
                    response.put("data", getClientList());
                    break;
                case "clear_logs":
                    // 这里可以触发清理日志文件
                    response.put("data", "日志清理命令已接收");
                    break;
                case "get_server_info":
                    response.put("data", getServerInfo());
                    break;
                default:
                    response.put("error", "未知命令: " + cmd);
            }

            conn.send(gson.toJson(response));
        }

        private List<Map<String, Object>> getClientList() {
            List<Map<String, Object>> list = new ArrayList<>();
            for (Map.Entry<WebSocket, ClientInfo> entry : clients.entrySet()) {
                Map<String, Object> clientMap = new HashMap<>();
                clientMap.put("address", entry.getValue().address);
                clientMap.put("user_agent", entry.getValue().userAgent);
                clientMap.put("connect_time", entry.getValue().connectTime);
                list.add(clientMap);
            }
            return list;
        }

        private Map<String, Object> getServerInfo() {
            Map<String, Object> info = new HashMap<>();
            info.put("ip", localIpAddress);
            info.put("port", PORT);
            info.put("start_time", System.currentTimeMillis());
            info.put("client_count", clients.size());
            info.put("version", "1.0.0");
            return info;
        }
    }

    /**
     * 客户端信息类
     */
    private static class ClientInfo {
        String userAgent;
        String address;
        long connectTime;

        ClientInfo(String userAgent, String address, long connectTime) {
            this.userAgent = userAgent;
            this.address = address;
            this.connectTime = connectTime;
        }

        @Override
        public String toString() {
            return address + " (" + userAgent + ")";
        }
    }

    /**
     * 日志条目类
     */
    public static class LogEntry {
        private String level;
        private String tag;
        private String message;
        private long timestamp;
        private String thread;

        /*
        ['INFO', 'WARN', 'ERROR', 'DEBUG']
         */
        public LogEntry(String level, String tag, String message) {
            this.level = level;
            this.tag = tag;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
            this.thread = Thread.currentThread().getName();
        }

        public String toJson() {
            Map<String, Object> map = new HashMap<>();
            map.put("level", level);
            map.put("tag", tag);
            map.put("message", message);
            map.put("timestamp", timestamp);
            map.put("thread", thread);
            return new Gson().toJson(map);
        }
    }

}