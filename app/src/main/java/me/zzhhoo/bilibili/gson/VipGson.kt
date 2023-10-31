package me.zzhhoo.bilibili.gson

// 大会员
// 大积分签到
data class VipTaskSignGson(
    //0：成功 、-101：账号未登录 、-401：非法访问 、-403：访问权限不足 、-111：csrf 校验失败 、-400：请求错误 、69800：网络繁忙 请稍后再试 、69801：你已领取过该权益
    val code: Int,
    val message: String,
)