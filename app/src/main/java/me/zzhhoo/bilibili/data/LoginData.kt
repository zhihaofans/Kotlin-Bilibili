package me.zzhhoo.bilibili.data

import com.orhanobut.logger.Logger
import io.zhihao.library.android.kotlinEx.find
import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty
import io.zhihao.library.android.util.SharedPreferencesUtil


class LoginData {
    private val DATA_KEY = "me.zzhhoo.bilibili.login"
    private val SP = SharedPreferencesUtil(DATA_KEY)
    fun getCookie(): String? {
        val cookie = SP.getString("cookie")
        if (cookie.isNotNullAndEmpty() && cookie!!.find(";bili_jct=") >= 0) {
            val biliJctLeft = cookie.find("bili_jct=")
            val biliJct = cookie.substring(biliJctLeft + 9)
            Logger.d(cookie)
            Logger.d(biliJct)
           if (biliJct.isNotNullAndEmpty()) {
               LoginData().setCsrf(biliJct)
           }
        }
        return cookie
    }

    fun setCookie(cookie: String): Boolean {
        return SP.putString("cookie", cookie)
    }

    fun getCsrf(): String? {
        return SP.getString("csrf")
    }

    fun setCsrf(csrf: String): Boolean {
        return SP.putString("csrf", csrf)
    }

    fun getAccessKey(): String? {
        return SP.getString("access_key")
    }

    fun setAccessKey(accessKey: String): Boolean {
        return SP.putString("access_key", accessKey)
    }
}