package me.zzhhoo.bilibili.services

import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.net.URL


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
    fun getCallback(url: String, headers: Map<String, String>? = null): Response {
        val requestBuilder = Request.Builder()
            .url(url)
        headers?.map {
            requestBuilder.addHeader(it.key, it.value)
        }
        val request = requestBuilder.build()
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

    @Throws(IOException::class)
    fun postCallback(
        url: String,
        body: RequestBody? = null,
        headers: Map<String, String>? = null
    ): Response {
        val requestBuilder = Request.Builder()
            .url(url)
        if (body !== null) {
            requestBuilder.post(body)
        }
        headers?.map {
            requestBuilder.addHeader(it.key, it.value)
        }
        val request = requestBuilder.build()
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

    fun createCookies(
        url: String,
        cookiesName: String,
        cookiesValue: String,
        httpOnly: Boolean = true,
        secure: Boolean = true
    ): Cookie {
        val mUrl = URL(url)
        return createCookies(mUrl.host, mUrl.path, cookiesName, cookiesValue, httpOnly, secure)
    }

    fun createCookies(
        domain: String,
        path: String,
        cookiesName: String,
        cookiesValue: String,
        httpOnly: Boolean = true,
        secure: Boolean = true
    ): Cookie {
        return Cookie.Builder()
            .domain(domain)
            .path(path)
            .name(cookiesName)
            .value(cookiesValue)
            .apply {
                if (httpOnly) httpOnly()
                if (secure) secure()
            }
            .build()
    }

    fun getCookie() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                private val cookieStore = HashMap<HttpUrl, List<Cookie>>()
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieStore[url] = cookies
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookies = cookieStore[url]
                    return cookies ?: ArrayList()
                }
            })
            .build()
    }

    fun getPostBodyFromJsonString(jsonString: String): RequestBody {
        return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun getPostBodyFromJsonMap(jsonMap: Map<String, String>): RequestBody {
        val formBody = FormBody.Builder()
        jsonMap.map {
            if (it.key.isNotNullAndEmpty() && it.value.isNotNullAndEmpty()) {
                formBody.add(it.key, it.value)
            }
        }
        return formBody.build()
    }
}