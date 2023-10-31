package me.zzhhoo.bilibili.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty
import io.zhihao.library.android.util.AlertUtil
import io.zhihao.library.android.util.EncodeUtil
import io.zhihao.library.android.util.IntentUtil
import io.zhihao.library.android.util.ToastUtil
import me.zzhhoo.bilibili.services.LoginService
import me.zzhhoo.bilibili.services.VipService
import me.zzhhoo.bilibili.util.ViewUtil
import me.zzhhoo.bilibili.util.startActivity
import me.zzhhoo.bilibili.views.ui.theme.BilibiliTheme

class MainActivity : ComponentActivity() {
    private val alertUtil = AlertUtil(this)
    private val toastUtil = ToastUtil(this)
    private val viewUtil = ViewUtil(this)
    private val vipService = VipService()
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
        val isLogin = LoginService().isLogin()
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
                                Text(
                                    text = "Bilibili" + if (isLogin) {
                                        "(已登录)"
                                    } else {
                                        "(未登录)"
                                    }
                                )
                            },
                            actions = {
                                IconButton(onClick = {
                                    if (LoginService().isLogin()) {
                                        val builder = AlertDialog.Builder(this@MainActivity)
                                        builder
                                            .setTitle("已登录")
                                            .setMessage("要检测登录状态是否有效吗？")
                                            .setPositiveButton("好的") { dialog, which ->
                                                // TODO:后面直接跳转个人中心
                                                startActivity(LoginActivity::class.java)
                                            }
                                            .setNegativeButton("不了") { _, _ -> }
                                        val dialog: AlertDialog = builder.create()
                                        //    dialog.setCanceledOnTouchOutside(setCanceledOnTouchOutside)
                                        dialog.show()
                                    } else {
                                        startActivity(LoginActivity::class.java)
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.AccountCircle,
                                        contentDescription = "登录"
                                    )
                                }

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
                    }) {/*TODO*/ }
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
        val listItem = listOf(
            "下载(未完成)",
            "大会员大积分签到",
            "大会员卡券兑换"
        )
        viewUtil.getListView(listItem) { idx, text ->
            when (idx) {
                1 -> {
                    if (LoginService().isLogin()) {
                        Thread {
                            vipService.scoreTaskSign { result ->
                                runOnUiThread {
                                    if (result?.code == 0) {
                                        toastUtil.showShortToast("签到成功")
                                    } else {
                                        alertUtil.showInputAlert(
                                            "签到失败",
                                            result?.message ?: "未知错误"
                                        ) { _, _ -> }
                                    }
                                }
                            }
                        }.start()
                    } else {
                        toastUtil.showShortToast("未登录")
                    }
                }

                2 -> {
                    if (LoginService().isLogin()) {
                        val list = arrayOf(
                            "B币券",
                            "会员购优惠券",
                            "漫画福利券",
                            "会员购包邮券",
                            "漫画商城优惠券",
                            "装扮体验卡",
                            "课堂优惠券"
                        )
                        alertUtil.showListAlert(
                            title = "大会员卡券兑换",
                            itemList = list
                        ) { _, idx ->
                            val type = idx + 1
                            val name=list[idx]
                            Thread {
                                vipService.receiveVipPrivilege(type) { result ->
                                    runOnUiThread {
                                        if (result?.code == 0) {
                                            toastUtil.showShortToast("${name}：兑换成功")
                                        } else {
                                            alertUtil.showInputAlert(
                                                "${name}：兑换失败",
                                                result?.message ?: "未知错误"
                                            ) { _, _ -> }
                                        }
                                    }
                                }
                            }.start()
                        }
                    } else {
                        toastUtil.showShortToast("未登录")
                    }
                }

                else -> toastUtil.showShortToast("未完成")
            }
        }
    }

}
