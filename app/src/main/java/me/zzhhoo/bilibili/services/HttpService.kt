package me.zzhhoo.bilibili.services

import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException


class HttpService {
    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaType();

    @Throws(IOException::class)
    fun get(url: String): String? {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response ->
            val body = response.body
            if (body == null) {
                return null
            } else {
                return body.string()
            }
        }
    }

    @Throws(IOException::class)
    fun getCallback(url: String): Response {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        return client.newCall(request).execute()
        /* .use { response ->
             val body = response.body
             if (body==null){
                 return null
             }else{
                 return body.string()
             }
         }*/
    }

    @Throws(IOException::class)
    fun post(url: String, body: String): String? {
        val request = Request.Builder()
            .url(url)
            .post(body.toRequestBody(mediaType))
            .build()
        client.newCall(request).execute().use { response -> return response.body!!.string() }
    }
}