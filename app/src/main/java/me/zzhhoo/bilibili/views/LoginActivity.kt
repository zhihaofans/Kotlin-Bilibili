package me.zzhhoo.bilibili.views

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.UrlQuerySanitizer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty
import io.zhihao.library.android.util.AlertUtil
import io.zhihao.library.android.util.ToastUtil
import me.zzhhoo.bilibili.data.LoginData
import me.zzhhoo.bilibili.gson.LoginQrcodeResultGson
import me.zzhhoo.bilibili.services.AppService
import me.zzhhoo.bilibili.services.HttpService
import me.zzhhoo.bilibili.services.LoginService
import me.zzhhoo.bilibili.util.QRcodeUtil
import me.zzhhoo.bilibili.util.UrlUtil
import me.zzhhoo.bilibili.util.ViewUtil
import me.zzhhoo.bilibili.views.ui.theme.BilibiliTheme
import okio.use
import java.net.URI
import java.net.URL


class LoginActivity : ComponentActivity() {
    private val appService = AppService(this)
    private val loginService = LoginService()
    private val http = HttpService()
    private val gson = Gson()
    private val qrcodeUtil = QRcodeUtil()
    private val alertUtil = AlertUtil(this)
    private val toastUtil = ToastUtil(this)
    private val viewUtil = ViewUtil(this)
    private var loadQrcodeFinish = false
    private var loginQrcodeKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun inputLoginData() {
        alertUtil.showInputAlert("输入Cookie", LoginData().getCookie() ?: "") { inputCookie, _ ->
            if (inputCookie.isNotNullAndEmpty()) {
                val success: Boolean =
                    loginService.inputLoginData(cookie = inputCookie)["cookie"] ?: false
                if (success) {
                    toastUtil.showShortToast("输入Cookie:成功")
                } else {

                    toastUtil.showShortToast("输入Cookie:失败")
                }
            }
        }
    }

    private fun loginByQRcode() {
        Thread {
            val url = URL("https://http.cat/images/404.jpg")
            val imageData = url.openConnection().getInputStream()
            val image =
                BitmapFactory.decodeStream(imageData).asImageBitmap()
            runOnUiThread {
                setContent {
                    BilibiliTheme {
                        MaterialTheme {
                            initQrcodeView(image)
                        }
                    }
                }
            }
        }.start()
    }

    private fun init() {
        setContent {
            Text(text = "加载中")
        }
//        if (!loginService.isLogin()) {
//            toastUtil.showLongToast("已登录")
//        } else {

        alertUtil.showListAlert(
            title = "登录方式",
            itemList = arrayOf("输入Cookie与Access Key", "扫码登录")
        ) { _, idx ->
            when (idx) {
                0 -> inputLoginData()
                1 -> loginByQRcode()
                else -> toastUtil.showShortToast("不支持")
            }
        }
//        }
    }

    private fun initQrcodeData(callback: (success: Boolean, qrcodeKey: String, loginUrl: String) -> Unit) {
        Thread {
            http.getCallback("https://passport.bilibili.com/x/passport-login/web/qrcode/generate")
                .use { resp ->
                    val httpBody = resp.body
                    val httpString = httpBody?.string()
                    resp.close()
                    runOnUiThread {
                        Logger.d(httpString)
                        if (httpBody == null) {
                            toastUtil.showShortToast("错误，返回空白信息")
                            callback.invoke(false, "", "")
                        } else {
                            try {
                                if (httpString.isNotNullAndEmpty()) {
                                    val qrcodeData = gson.fromJson<LoginQrcodeResultGson>(
                                        httpString,
                                        LoginQrcodeResultGson::class.java
                                    )
                                    Logger.d(qrcodeData)
                                    if (qrcodeData !== null && qrcodeData.code == 0 && qrcodeData.data !== null) {
                                        callback.invoke(
                                            true,
                                            qrcodeData.data?.qrcode_key ?: "",
                                            qrcodeData.data?.url ?: ""
                                        )
                                    } else {
                                        callback.invoke(false, "", "")
                                    }
                                } else {
                                    toastUtil.showShortToast("加载失败，得到空白结果")
                                    callback.invoke(false, "", "")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Logger.e(e.message!!)
                                toastUtil.showShortToast("加载失败，得到错误结果")
                                callback.invoke(false, "", "")
                            }
                        }
                    }
                }
        }.start()
    }

    @Composable
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    private fun initQrcodeView(image: ImageBitmap) {
        val imageBitmap: MutableState<ImageBitmap> = remember {
            mutableStateOf(image)
        }
        val appLoginUrl = remember {
            mutableStateOf("")
        }
        initQrcodeData() { success: Boolean, qrcodeKey: String, loginUrl: String ->
            Logger.d("init.finish$success$qrcodeKey")
            Logger.d(success)
            Logger.d(qrcodeKey)
            loadQrcodeFinish = success
            loginQrcodeKey = qrcodeKey
            appLoginUrl.value = loginUrl
            Logger.d(loginUrl)
            if (success) {
                val qrcodeData =
                    qrcodeUtil.createQRCode("https://passport.bilibili.com/h5-app/passport/login/scan?navhide=1&qrcode_key=${qrcodeKey}")
                if (qrcodeData != null) {
                    imageBitmap.value = qrcodeData.asImageBitmap()
                } else {
                    showToast("生成二维码失败：空白")
                }
            }
            Logger.d("init.finish.finish")
        }
        Scaffold(
            Modifier.layoutId("appBar"),
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .padding(bottom = 0.dp) // 这里为 TopAppBar 添加上边距
                        .fillMaxWidth(),
                    title = {
                        Text(text = "二维码登录")
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    },
                )
            }/*,
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                *//* TODO:fab按钮 *//*
                            }) {
                                Icons.Default.Search
                            }
                        }*/
        ) {
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            Column(
                modifier = Modifier
                    .padding(top = 56.dp) // 这里为 内容 添加上边距，与TopAppBar隔开
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    bitmap = imageBitmap.value,
                    contentDescription = "这里应该有个二维码",
                )

                viewUtil.getButton(id = "button_has_scan", title = "已经扫码") {
                    if (loadQrcodeFinish) {
                        checkQrcodeScan()
                    } else {
                        toastUtil.showShortToast("请等待二维码加载完成")
                    }
                }
                viewUtil.getButton(id = "button_gotoapp", title = "跳转App登录") {
                    Logger.d(appLoginUrl.value)
                    if (appLoginUrl.value.isNotEmpty()) {
                        appService.openWebBrowser(appLoginUrl.value)
                    } else {
                        toastUtil.showShortToast("你跳呀，跳的动你就跳")
                    }
                }
            }
        }

    }

    private fun checkQrcodeScan() {
        if (loginQrcodeKey.isNotNullAndEmpty()) {
            showToast(loginQrcodeKey)
            Thread {
                loginService.checkQrcodeStatus(loginQrcodeKey) { result ->
                    Logger.d(result)
                    val codeStr = mapOf<Int, String>(
                        0 to "扫码登录成功",
                        86038 to "二维码已失效",
                        86090 to "二维码已扫码未确认",
                        86101 to "未扫码"
                    )
                    if (result?.data == null) {
                        showToast("获取扫码结果失败")
                    } else {
                        val data = result.data
                        if (data.code == 0) {
                            val urlUtil = UrlUtil()
                            urlUtil.init(data.url)
                            val uid = urlUtil.getQuery("DedeUserID")
                            val uidMd5 = urlUtil.getQuery("DedeUserID__ckMd5")
                            val sessdata = urlUtil.getQuery("SESSDATA")
                            val expiresTime = urlUtil.getQuery("Expires")
                            val biliJct = urlUtil.getQuery("bili_jct")
                            val goUrl = urlUtil.getQuery("gourl")
                            val setSuccess =
                                LoginData().setCookie("DedeUserID=${uid};DedeUserID__ckMd5=${uidMd5};SESSDATA=${sessdata};bili_jct=${biliJct}")

                            runOnUiThread {
                                toastUtil.showShortToast(
                                    "更新登录数据" + if (setSuccess) {
                                        "成功"
                                    } else {
                                        "失败"
                                    }
                                )
                            }
                        } else {
                            showToast(data.message)
                        }
                    }
                }
            }.start()
        } else {
            showToast("空白qrcodeKey")
        }
    }

    private fun showToast(text: String) {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, text, Toast.LENGTH_SHORT).show()
        }
    }
}
