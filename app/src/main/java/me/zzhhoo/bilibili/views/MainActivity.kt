package me.zzhhoo.bilibili.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import me.zzhhoo.bilibili.gson.LoginQrcodeResultGson
import me.zzhhoo.bilibili.services.LoginService
import me.zzhhoo.bilibili.ui.theme.BilibiliTheme
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class MainActivity : ComponentActivity() {
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

        Button(modifier = Modifier.layoutId("button_login"),
            onClick = {
                /*TODO*/
                val navigate = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(navigate)
            }
        ) {
            Text("登录Bilibili")
        }

        Button(modifier = Modifier.layoutId("button_app"),
            onClick = { /*TODO*/ }
        ) {
            Text("应用管理")
        }

        Button(modifier = Modifier.layoutId("button_dynamic"),
            onClick = { /*TODO*/ }
        ) {
            Text("动态")
        }

        Button(modifier = Modifier.layoutId("button_fav"),
            onClick = { /*TODO*/ }
        ) {
            Text("收藏")
        }
        Button(modifier = Modifier.layoutId("button_later2watch"),
            onClick = { /*TODO*/ }
        ) {
            Text("稍后再看")
        }
        Button(modifier = Modifier.layoutId("button_history"),
            onClick = { /*TODO*/ }
        ) {
            Text("历史")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BilibiliTheme {
        Greeting("Android")
    }
}