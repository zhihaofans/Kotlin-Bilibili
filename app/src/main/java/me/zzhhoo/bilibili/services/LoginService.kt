package me.zzhhoo.bilibili.services

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty
import me.zzhhoo.bilibili.data.LoginData
import me.zzhhoo.bilibili.gson.LoginQrcodeCheckResultGson
import me.zzhhoo.bilibili.gson.LoginQrcodeResultGson
import okio.use

class LoginService {
    val Http = HttpService()
    val gson = Gson()
    fun getQrcode() {
        val httpResult =
            Http.getCallback("https://passport.bilibili.com/x/passport-login/web/qrcode/generate")
                .use { resp ->

                }
    }

    fun checkQrcodeStatus(
        qrcodeKey: String,
        callback: (result: LoginQrcodeCheckResultGson?) -> Unit
    ) {
        Http.getCallback("https://passport.bilibili.com/x/passport-login/web/qrcode/poll")
            .use { resp ->
                val httpBody = resp.body
                val httpString = httpBody?.string()
                if (httpBody == null || httpString.isNotNullAndEmpty()) {
                    callback.invoke(null)
                } else {
                    val qrcodeData = gson.fromJson(
                        httpString,
                        LoginQrcodeCheckResultGson::class.java
                    )
                    callback.invoke(qrcodeData ?: null)
                }
            }
    }

    fun isLogin(): Boolean {
        return LoginData().getCookie().isNotNullAndEmpty()
    }

    fun inputLoginData(cookie: String? = null, accessKey: String? = null): Map<String, Boolean> {
        var cookieSuccess = false
        var accessKeySuccess = false
        if (cookie.isNotNullAndEmpty()) {
            cookieSuccess = LoginData().setCookie(cookie!!)
        }
        if (accessKey.isNotNullAndEmpty()) {
            accessKeySuccess = LoginData().setAccessKey(accessKey!!)
        }
        return mapOf("cookie" to cookieSuccess, "access_key" to accessKeySuccess)
    }
}