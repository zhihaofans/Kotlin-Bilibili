package me.zzhhoo.bilibili.gson

/**
 * @author zhihaofans
 * @date 2023/09/29
 */

// 二维码登录-获取二维码
data class LoginQrcodeResultGson(
    val code: Int,
    val message: String,
    val data: LoginQrcodeDataGson?
)

data class LoginQrcodeDataGson(
    val url: String,
    val qrcode_key: String,
)

// 二维码登录-扫码登录
data class LoginQrcodeCheckResultGson(
    val code: Int,
    val message: String,
    val data: LoginQrcodeCheckDataGson?
)

data class LoginQrcodeCheckDataGson(
    val url: String,
    val refresh_token: String,
    // val timestamp: Int,
    val code: Int,
    val message: String
)
