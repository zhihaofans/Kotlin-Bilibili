package me.zzhhoo.bilibili

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.zhihao.library.android.ZLibrary
import io.zhihao.library.android.util.AppUtil


class App:Application() {
    override fun onCreate() {
        super.onCreate()
        // Logger
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.d("Debug:${AppUtil.isDebug()}")
        // AndroidLibrary
        ZLibrary.init(applicationContext)
        Logger.d("AndroidLibrary:${ZLibrary.getZLibraryVersion()}")
    }
}