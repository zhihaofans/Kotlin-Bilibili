package me.zzhhoo.bilibili.data

import io.zhihao.library.android.util.SharedPreferencesUtil


class LoginData {
    private val DATA_KEY = "me.zzhhoo.bilibili.login"
    private val SP = SharedPreferencesUtil(DATA_KEY)
    fun getCookie(): String? {
        return SP.getString("cookie")
    }

    fun setCookie(cookie: String): Boolean {
        return SP.putString("cookie", cookie)
    }

    fun getAccessKey(): String? {
        return SP.getString("access_key")
    }

    fun setAccessKey(accessKey: String): Boolean {
        return SP.putString("access_key", accessKey)
    }
}