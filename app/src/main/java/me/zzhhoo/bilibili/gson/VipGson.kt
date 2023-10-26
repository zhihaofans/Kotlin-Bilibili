package me.zzhhoo.bilibili.gson

// 大会员
// 大积分签到
data class VipTaskSignGson(
    //0：成功 、-101：账号未登录 、-401：非法访问 、-403：访问权限不足
    val code: Int,
    val message: String,
)