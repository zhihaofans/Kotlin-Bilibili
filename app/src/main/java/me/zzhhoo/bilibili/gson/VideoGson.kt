package me.zzhhoo.bilibili.gson

// 视频
// 视频信息
data class VideoInfoResultGson(
    //code,0：成功 、-400：请求错误 、-403：权限不足 、-404：无视频 、62002：稿件不可见 、62004：稿件审核中
    val code: Int,
    val message: String,
    val data: VideoInfoDataGson
)

data class VideoInfoDataGson(
    // 稿件的唯一标识符
    val bvid: String,

    // 稿件的另一个标识符，数字形式
    val aid: Int,

    // 稿件分P的数量，默认为1
    val videos: Int,

    // 分区的标识符
    val tid: Int,

    // 子分区的名称
    val tname: String,

    // 视频的版权类型，1代表原创，2代表转载
    val copyright: Int,

    // 稿件封面图片的URL
    val pic: String,

    // 稿件的标题
    val title: String,

    // 稿件发布的时间戳，单位为秒
    val pubdate: Int,

    // 用户投稿的时间戳，单位为秒
    val ctime: Int,

    // 视频的简介
    val desc: String?,

    // 新版视频简介，是一个数组格式
//    val descV2: List<Any>, // 注意：具体类型需要替换Any

    // 视频的状态码
    val state: Int?,

    // 稿件属性位配置，已弃用
//    val attribute: Int, // 已经弃用

    // 稿件总时长（包含所有分P），单位为秒
    val duration: Int?,

    // 撞车视频跳转的avid，仅撞车视频存在此字段
//    val forward: Int, // 仅撞车视频存在此字段

    // 稿件参与的活动ID
//    val missionId: Int,

    // 重定向URL，通常用于番剧或影视视频
//    val redirectUrl: String?, // 仅番剧或影视视频存在此字段

    // 视频的属性标志，是一个对象
    //val rights: Rights, // 自定义类Rights

    // 视频UP主的信息，是一个对象
    val owner: VideoInfoOwnerGson, // 自定义类Owner

    // 视频的统计信息，是一个对象
    //val stat: Stat, // 自定义类Stat

    // 视频同步发布的动态的文字内容
    val dynamic: String?,

    // 视频1P的cid
//    val cid: Int,

    // 视频1P的分辨率信息，是一个对象
    //val dimension: Dimension, // 自定义类Dimension

    // 该字段的作用当前不明确，可能与首映相关
//    val premiere: Any?, // 类型需要根据实际使用调整

    // 未描述
//    val teenageMode: Int,

    // 是否是收费的季节性内容
//    val isChargeableSeason: Boolean,

    // 是否是故事性内容
//    val isStory: Boolean,

    // 缓存相关，具体作用不明确
//    val noCache: Boolean,

    // 视频分P列表，是一个数组格式
    //val pages: List<Page>, // 自定义类Page，具体类型需要替换

    // 视频的CC字幕信息，是一个对象
//    val subtitle: Subtitle, // 自定义类Subtitle

    // 合作成员列表，非合作视频无此项
    //val staff: List<StaffMember>, // 自定义类StaffMember，具体类型需要替换

    // 未描述
//    val isSeasonDisplay: Boolean,

    // 用户装扮信息，是一个对象
    //val userGarb: UserGarb, // 自定义类UserGarb

    // 未描述
    //val honorReply: HonorReply, // 自定义类HonorReply

    // 未描述，可能是喜欢按钮的图标
//    val likeIcon: String
)

data class VideoInfoOwnerGson(
    val mid: Int,//	UP主mid
    val name: String,//	UP主昵称
    val face: String//    UP主头像
)