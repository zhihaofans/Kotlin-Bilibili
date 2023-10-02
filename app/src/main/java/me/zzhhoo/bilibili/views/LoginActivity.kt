package me.zzhhoo.bilibili.views

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
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
import androidx.compose.material3.Button
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
import me.zzhhoo.bilibili.gson.LoginQrcodeResultGson
import me.zzhhoo.bilibili.services.HttpService
import me.zzhhoo.bilibili.services.LoginService
import me.zzhhoo.bilibili.util.QRcodeUtil
import me.zzhhoo.bilibili.views.ui.theme.BilibiliTheme
import okio.use
import java.io.InputStream
import java.net.URL


class LoginActivity : ComponentActivity() {
    private val Login = LoginService()
    private val Http = HttpService()
    private val gson = Gson()
    private val QRcode = QRcodeUtil()
    private var isSuccess = false
    private var loginQrcodeKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            val url = URL("https://http.cat/images/404.jpg")
            val imageData = url.openConnection().getInputStream()
            val image =
                BitmapFactory.decodeStream(imageData).asImageBitmap()
            runOnUiThread {
                setContent {
                    BilibiliTheme {
                        MaterialTheme {
                            initView(image)
                        }
                    }
                }
            }
        }.start()
    }

    private fun init(callback: (success: Boolean, qrcodeKey: String) -> Unit) {
        showToast("正在加载二维码")
        Thread {
            Http.getCallback("https://passport.bilibili.com/x/passport-login/web/qrcode/generate")
                .use { resp ->
                    val httpBody = resp.body
                    val httpString = httpBody?.string()
                    resp.close()
                    runOnUiThread {
                        Logger.d(httpString)
                        if (httpBody == null) {
                            showToast("错误，返回空白信息")
                            callback.invoke(false, "")
                        } else {
                            try {
                                if (httpString.isNotNullAndEmpty()) {
                                    val qrcodeData = gson.fromJson<LoginQrcodeResultGson>(
                                        httpString,
                                        LoginQrcodeResultGson::class.java
                                    )
                                    Logger.d(qrcodeData)
                                    if (qrcodeData !== null && qrcodeData.code == 0 && qrcodeData.data !== null) {
                                        callback.invoke(true, qrcodeData.data?.qrcode_key ?: "")
                                    } else {
                                        callback.invoke(false, "")
                                    }
                                } else {
                                    showToast("加载失败，得到空白结果")
                                    callback.invoke(false, "")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Logger.e(e.message!!)
                                showToast("加载失败，得到错误结果")
                                callback.invoke(false, "")
                            }
                        }
                    }
                }
        }.start()
    }

    @Composable
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    private fun initView(image: ImageBitmap) {
        val imageBitmap: MutableState<ImageBitmap> = remember {
            mutableStateOf(image)
        }
        init() { success: Boolean, qrcodeKey: String ->
            Logger.d("init.finish$success$qrcodeKey")
            Logger.d(success)
            Logger.d(qrcodeKey)
            isSuccess = success
            loginQrcodeKey = qrcodeKey
            if (success) {
                val qrcodeData =
                    QRcode.createQRCode("https://passport.bilibili.com/h5-app/passport/login/scan?navhide=1&qrcode_key=${qrcodeKey}")
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
                Button(modifier = Modifier.layoutId("button_has_scan"),
                    onClick = {
                        /*TODO*/
                        showToast("请等待二维码加载完成")
                        if (isSuccess) {
                            checkQrcodeScan()
                        } else {

                            showToast("请等待二维码加载完成")
                        }
                    }
                ) {
                    Text("已经扫码")
                }

                Button(modifier = Modifier.layoutId("button_gotoapp"),
                    onClick = {
                        /*TODO*/
                        showToast("你跳呀，跳的动你就跳")
                    }
                ) {
                    Text("跳转App登录")
                }

            }
        }

    }

    private fun checkQrcodeScan() {
        if (loginQrcodeKey.isNotNullAndEmpty()) {
            showToast(loginQrcodeKey)
            Login.checkQrcodeStatus(loginQrcodeKey) { result ->
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

                    } else {
                        showToast(data.message )
                    }
                }
            }
        } else {
            showToast("空白qrcodeKey")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this@LoginActivity, text, Toast.LENGTH_SHORT).show()
    }
}
