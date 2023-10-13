package me.zzhhoo.bilibili.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import io.zhihao.library.android.util.EncodeUtil


class AppService(context: Context) {
    private val _context = context
    fun openWebBrowser(url: String) {
        val browserIntent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "bilibili://browser/?url=${
                    EncodeUtil.urlEncode(url)
                }"
            )
        )
        _context.startActivity(browserIntent)
    }
}