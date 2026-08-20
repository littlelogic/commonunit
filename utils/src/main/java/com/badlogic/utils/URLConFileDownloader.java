package com.badlogic.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class URLConFileDownloader {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean isCancelled = false;

    private int mConnectTimeout = 15000;
    private int mReadTimeout = 15000;

    public URLConFileDownloader(){

    }

    public URLConFileDownloader(int mConnectTimeout_,int mReadTimeout_) {
        mConnectTimeout = mConnectTimeout_;
        mReadTimeout = mReadTimeout_;
    }

    /**
     * 开始下载文件
     * @param fileUrl 文件URL
     * @param outputFile 输出文件
     * @param callback 回调接口
     */
    public boolean downloadFile(String fileUrl, File outputFile, URLDownloadCallback callback) {
        return downloadFile(fileUrl,outputFile,false,true,callback);
    }

    public boolean downloadFile(String fileUrl, File outputFile,boolean mainCallMark,boolean syncExecute, URLDownloadCallback callback_out) {
        isCancelled = false;
        Handler handler = null;
        if (mainCallMark) {
            handler = new Handler(Looper.getMainLooper());
        }
        final Handler mainHandler = handler;
        if (callback_out == null) {
            callback_out = new URLDownloadCallback() {
                @Override
                public void onProgress(int progress, long downloaded, long total) {

                }

                @Override
                public void onSuccess(File file) {

                }

                @Override
                public void onFailure(Exception e) {

                }

                @Override
                public void onCancel() {

                }
            };
        }
        final URLDownloadCallback callback = callback_out;

        if (syncExecute) {
            return downloadFileDo(fileUrl,outputFile,mainHandler,callback);
        } else {
            executor.execute(() -> downloadFileDo(fileUrl,outputFile,mainHandler,callback));
            return true;
        }
    }

    public boolean downloadFileDo(String fileUrl, File outputFile, Handler mainHandler, URLDownloadCallback callback) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 告诉服务端我接受的编码方式,避免获取文件大小失败，只是避免
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.setConnectTimeout(mConnectTimeout);
            connection.setReadTimeout(mReadTimeout);
            connection.connect();

            // 检查HTTP响应码
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // 获取文件总大小
            long fileLength = connection.getContentLength();
            if (fileLength <= 0) {
                // 如果无法获取文件大小，使用未知大小模式
                fileLength = -1;
            }

            // 创建输入输出流
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4096];
            long downloaded = 0;
            int bytesRead;

            // 开始读取数据
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (isCancelled) {
                    // 如果取消下载，删除部分下载的文件并回调
                    outputStream.close();
                    if (outputFile.exists()) {
                        outputFile.delete();
                    }
                    notifyCancel(mainHandler,callback);
                    return false;
                }

                // 写入文件
                outputStream.write(buffer, 0, bytesRead);
                downloaded += bytesRead;

                // 计算并通知进度
                if (fileLength > 0) {
                    int progress = (int) (downloaded * 100 / fileLength);
                    notifyProgress(mainHandler,progress, downloaded, fileLength, callback);
                } else {
                    // 未知文件大小时，进度为-1表示不确定
                    notifyProgress(mainHandler,-1, downloaded, -1, callback);
                }
            }

            // 下载完成
            outputStream.flush();
            notifySuccess(mainHandler,outputFile, callback);
            return true;
        } catch (Exception e) {
            // 发生异常，删除可能已部分下载的文件
            if (outputFile.exists()) {
                outputFile.delete();
            }
            notifyFailure(mainHandler,e, callback);
            return false;
        } finally {
            // 关闭所有流和连接
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
                if (connection != null) connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取消下载
     */
    public void cancelDownload() {
        isCancelled = true;
    }

    /**
     * 关闭下载器，释放资源
     */
    public void shutdown() {
        isCancelled = true;
        executor.shutdown();
    }

    // 以下是在主线程通知回调的辅助方法

    private void notifyProgress(Handler mainHandler,int progress, long downloaded, long total, URLDownloadCallback callback) {
        Runnable runnable = () -> {
            if (isCancelled) {
                return;
            }
            callback.onProgress(progress, downloaded, total);
        };
        if (mainHandler == null) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    private void notifySuccess(Handler mainHandler,File file, URLDownloadCallback callback) {
        Runnable runnable = () -> {
            if (isCancelled) {
                return;
            }
            callback.onSuccess(file);
        };
        if (mainHandler == null) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    private void notifyFailure(Handler mainHandler,Exception e, URLDownloadCallback callback) {
        Runnable runnable = () -> {
            if (isCancelled) {
                return;
            }
            callback.onFailure(e);
        };
        if (mainHandler == null) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    private void notifyCancel(Handler mainHandler, URLDownloadCallback callback) {
        Runnable runnable = () -> {
            if (isCancelled) {
                return;
            }
            callback.onCancel();
        };
        if (mainHandler == null) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }
}