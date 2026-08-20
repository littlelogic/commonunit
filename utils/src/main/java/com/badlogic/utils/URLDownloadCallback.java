package com.badlogic.utils;

import java.io.File;

public interface URLDownloadCallback {
    /**
     * 下载进度回调
     * @param progress 当前进度 (0-100)
     * @param downloaded 已下载字节数
     * @param total 总字节数
     */
    void onProgress(int progress, long downloaded, long total);

    /**
     * 下载成功回调
     * @param file 下载完成的文件
     */
    void onSuccess(File file);

    /**
     * 下载失败回调
     * @param e 异常信息
     */
    void onFailure(Exception e);

    /**
     * 下载取消回调
     */
    void onCancel();
}