package com.badlogic.utils

import java.net.HttpURLConnection
import java.net.URL


class KURLConFileDownloader {


    fun getRemoteFileName(urlString: String): String? {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.instanceFollowRedirects = true  // 自动跟随重定向
            connection.requestMethod = "HEAD"          // 仅获取头信息，避免下载大文件
            connection.connect()

            // 获取最终 URL（经过重定向后的）
            val finalUrl = connection.url.toString()

            // 1. 尝试从 Content-Disposition 获取
            val contentDisposition = connection.getHeaderField("Content-Disposition")
            var fileName = parseFileNameFromDisposition(contentDisposition)

            // 2. 若没有，从最终 URL 中提取
            if (fileName == null) {
                fileName = getFileNameFromUrl(finalUrl)
            }

            return fileName
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            connection?.disconnect()
        }
    }

    private fun parseFileNameFromDisposition(disposition: String?): String? {
        if (disposition == null) return null
        // 匹配 filename=... 或 filename*=...
        val regex = Regex("filename[*]?=([^;]+)")
        val match = regex.find(disposition) ?: return null
        var fileName = match.groupValues[1].trim('"')
        // 处理 filename*=UTF-8''... 格式
        if (fileName.startsWith("UTF-8''")) {
            fileName = fileName.removePrefix("UTF-8''")
            fileName = java.net.URLDecoder.decode(fileName, "UTF-8")
        }
        return fileName
    }

    private fun getFileNameFromUrl(urlString: String): String? {
        return try {
            val uri = java.net.URI(urlString)
            val path = uri.path ?: return null
            val fileName = path.substringAfterLast('/')
            fileName.takeIf { it.isNotBlank() }
        } catch (e: Exception) {
            null
        }
    }
}