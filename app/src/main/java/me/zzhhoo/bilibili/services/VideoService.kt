package me.zzhhoo.bilibili.services

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import me.zzhhoo.bilibili.data.LoginData
import me.zzhhoo.bilibili.gson.VideoInfoResultGson
import okio.use

class VideoService {
    private val http = HttpService()
    private val gson = Gson()
    fun getVideoInfo(bvid: String, callback: (videoResult: VideoInfoResultGson?) -> Unit) {
        val url = "https://api.bilibili.com/x/web-interface/view?bvid=${bvid}"
        val csrf = LoginData().getCsrf() ?: ""
        val cookie = LoginData().getCookie() ?: ""
        val header = mapOf("Cookie" to cookie, "Referer" to "https://www.bilibili.com")
        Logger.d(header)
        http.getCallback(url)
            .use { resp ->
                val httpBody = resp.body
                val httpString = httpBody?.string()
                if (httpBody == null || httpString.isNullOrEmpty()) {
                    Logger.e("checkQrcodeStatus.result==null")
                    callback.invoke(null)
                } else {
                    Logger.w(httpString)
                    val httpResult = gson.fromJson(
                        httpString,
                        VideoInfoResultGson::class.java
                    )
                    callback.invoke(httpResult)
                }
            }
    }
}