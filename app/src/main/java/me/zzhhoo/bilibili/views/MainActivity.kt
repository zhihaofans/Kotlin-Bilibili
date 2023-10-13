package me.zzhhoo.bilibili.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orhanobut.logger.Logger
import io.zhihao.library.android.util.AlertUtil
import io.zhihao.library.android.util.ToastUtil
import me.zzhhoo.bilibili.data.LoginData
import me.zzhhoo.bilibili.services.LoginService
import me.zzhhoo.bilibili.util.ViewUtil
import me.zzhhoo.bilibili.views.ui.theme.BilibiliTheme
import java.net.URI

class MainActivity : ComponentActivity() {
    private val alertUtil = AlertUtil(this)
    private val toastUtil = ToastUtil(this)
    private val viewUtil = ViewUtil(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            initView()
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Preview(showBackground = true)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun initView() {
        BilibiliTheme {
            MaterialTheme {
                Scaffold(
                    Modifier.layoutId("appBar"),
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .padding(bottom = 0.dp) // 这里为 TopAppBar 添加上边距
                                .fillMaxWidth(),
                            title = {
                                Text(text = "Bilibili")
                            },
                            actions = {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = null
                                )
                            },
                        )
                    },
                    floatingActionButton = {
                        viewUtil.getFab(Icons.Default.Search, "搜索") {

                        }
                    }) {}
                Spacer(modifier = Modifier.requiredHeight(10.dp))
                Column(
                    modifier = Modifier
                        .padding(top = 56.dp) // 这里为 内容 添加上边距，与TopAppBar隔开
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    initContentView()
                }
            }
        }
    }

    @Composable
    private fun initContentView() {
        val isLogin = LoginService().isLogin()
        viewUtil.getButton(
            id = "button_login", title = "登录Bilibili" + if (isLogin) {
                "(已登录)"
            } else {
                "(未登录)"
            }
        ) {
            /*TODO*/
//            if (!LoginService().isLogin()) {
//                toastUtil.showShortToast("已登录")
//            } else {
            val navigate = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(navigate)
//            }
        }
        viewUtil.getButton(id = "button_download", title = "下载") {
            val dataUrl =
                "https://passport.biligame.com/crossDomain?DedeUserID=***\\u0026DedeUserID__ckMd5=***\\u0026Expires=***\\u0026SESSDATA=***\\u0026bili_jct=***\\u0026gourl=https%3A%2F%2Fpassport.bilibili.com"
            val url = URI(dataUrl)
            Logger.d(url.query)
        }
        viewUtil.getButton(id = "button_dynamic", title = "动态") {}
        viewUtil.getButton(id = "button_fav", title = "收藏") {}
        viewUtil.getButton(id = "button_later2watch", title = "稍后再看") {}
        viewUtil.getButton(id = "button_history", title = "历史") {}
    }

}
