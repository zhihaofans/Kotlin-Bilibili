package me.zzhhoo.bilibili.gson

/**
 * @author zhihaofans
 * @date 2023/09/29
 */
//下面的是用户信息
data class LoginQrcodeResultGson(
    val code: Int,
    val message: String,
    val data: LoginQrcodeDataGson?
)

data class LoginQrcodeDataGson(
    val url: String,
    val qrcode_key: String,
)
