package me.zzhhoo.bilibili.util

import android.content.Context
import android.content.Intent


fun Context.startActivity(gotoClass: Class<*>) = this.startActivity(Intent(this, gotoClass))