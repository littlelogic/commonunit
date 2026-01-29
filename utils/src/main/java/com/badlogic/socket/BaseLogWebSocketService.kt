package com.badlogic.socket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

abstract class BaseLogWebSocketService : Service() {

    private val NOTIFICATION_ID = 1 // 通知ID，必须唯一且不为0
    private val CHANNEL_ID = "log_websocket_channel" // 通知渠道ID

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "日志同步服务"
            val importance = NotificationManager.IMPORTANCE_LOW // 或 DEFAULT，根据需求
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                description = "用于保持WebSocket连接同步日志"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun startForegroundServiceWithNotification() {
        // 构建一个符合前台服务要求的通知
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("日志同步服务运行中")
            .setContentText("正在与电脑同步日志...")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // 必须设置一个有效的小图标
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        // 关键：启动前台服务，并绑定通知
        startForeground(NOTIFICATION_ID, notification)
    }
}