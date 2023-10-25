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
import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty
import io.zhihao.library.android.util.AlertUtil
import io.zhihao.library.android.util.AppUtil
import io.zhihao.library.android.util.EncodeUtil
import io.zhihao.library.android.util.IntentUtil
import io.zhihao.library.android.util.ToastUtil
import me.zzhhoo.bilibili.data.LoginData
import me.zzhhoo.bilibili.services.LoginService
import me.zzhhoo.bilibili.util.ViewUtil
import me.zzhhoo.bilibili.util.startActivity
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
                            alertUtil.showInputAlert("Bilibili搜索", "") { text, dialog ->
                                if (text.isNotNullAndEmpty()) {
                                    val searchUrl =
                                        "bilibili://search?keyword=" + EncodeUtil.urlEncode(text)
                                    startActivity(IntentUtil.getOpenWebBrowserIntent(searchUrl))
                                } else {
                                    toastUtil.showShortToast("不能搜索空白")
                                }
                            }
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
        val listItem = listOf(
            "登录Bilibili" + if (isLogin) {
                "(已登录)"
            } else {
                "(未登录)"
            },
            "下载",
            "动态",
            "收藏",
            "稍后再看",
            "历史"
        )
        viewUtil.getListView(listItem) { idx, text ->
            when (idx) {
                0 -> {

//            if (!LoginService().isLogin()) {
//                toastUtil.showShortToast("已登录")
//            } else {
                    startActivity(LoginActivity::class.java)
//            }
                }

                1 -> startActivity(DownloadActivity::class.java)
            }
        }
    }

}
