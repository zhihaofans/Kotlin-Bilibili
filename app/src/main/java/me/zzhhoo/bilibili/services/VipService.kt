package me.zzhhoo.bilibili.services

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import me.zzhhoo.bilibili.data.LoginData
import me.zzhhoo.bilibili.gson.VipTaskSignGson
import okio.use

class VipService {
    private val http = HttpService()
    private val gson = Gson()
    fun scoreTaskSign(
        callback: (result: VipTaskSignGson?) -> Unit
    ) {
        val csrf = LoginData().getCsrf() ?: ""
        val cookie = LoginData().getCookie() ?: ""
        val url = "https://api.bilibili.com/pgc/activity/score/task/sign"
        val body = mapOf("csrf" to csrf)
        val header = mapOf("Cookie" to cookie,"Referer" to "https://www.bilibili.com")
        Logger.d(body)
        Logger.d(header)
        http.postCallback(url, http.getPostBodyFromJsonMap(body), header)
            .use { resp ->
                val httpBody = resp.body
                val httpString = httpBody?.string()
                if (httpBody == null || httpString.isNullOrEmpty()) {
                    Logger.e("scoreTaskSign.result==null")
                    callback.invoke(null)
                } else {
                    Logger.w(httpString)
                    val signResult = gson.fromJson(
                        httpString,
                        VipTaskSignGson::class.java
                    )
                    callback.invoke(signResult ?: null)
                }
            }
    }
}