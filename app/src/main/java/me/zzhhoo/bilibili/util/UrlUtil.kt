package me.zzhhoo.bilibili.util

import android.net.UrlQuerySanitizer

class UrlUtil {
    private val sanitizer = UrlQuerySanitizer()
    private var initFinished = false
    fun init(url: String) {
        sanitizer.allowUnregisteredParamaters = true
        sanitizer.parseUrl(url)
        initFinished = true
    }

    fun getQuery(key: String): String? {
        if (!initFinished) return null
        return sanitizer.getValue(key)
    }
}