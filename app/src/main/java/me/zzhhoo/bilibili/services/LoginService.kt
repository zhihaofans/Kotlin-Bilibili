package me.zzhhoo.bilibili.services

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty
import me.zzhhoo.bilibili.data.LoginData
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

    fun isLogin(): Boolean {
        return LoginData().getCookie().isNotNullAndEmpty()
    }
}