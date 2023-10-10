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
import me.zzhhoo.bilibili.services.LoginService
import me.zzhhoo.bilibili.ui.theme.BilibiliTheme
import me.zzhhoo.bilibili.util.ViewUtil

class MainActivity : ComponentActivity() {
    private val viewUtil = ViewUtil(this@MainActivity)
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
                        FloatingActionButton(onClick = {
                            /* TODO:fab按钮 */
                        }) {
                            Icons.Default.Search
                        }
                    }) {

                }
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
        viewUtil.getButton(id = "button_login", title = "登录Bilibili") {
            /*TODO*/
            if (LoginService().isLogin()) {
                showToast("已登录")
            } else {
                val navigate = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(navigate)
            }
        }
        viewUtil.getButton(id = "button_app", title = "应用管理") {}
        viewUtil.getButton(id = "button_dynamic", title = "动态") {}
        viewUtil.getButton(id = "button_fav", title = "收藏") {}
        viewUtil.getButton(id = "button_later2watch", title = "稍后再看") {}
        viewUtil.getButton(id = "button_history", title = "历史") {}
    }

    private fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }
}
