package me.zzhhoo.bilibili.util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Hashtable

class QRcodeUtil {
    fun createQRCode(
        content: CharSequence?,
        QR_WIDTH: Int = 800,
        QR_HEIGHT: Int = 800,
        border: Int = 1,
        backgroundColor: Int = -0x1,
        codeColor: Int = -0x1000000
    ): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            // 判断URL合法性
            if (content == null || "" == content || content.isEmpty()) {
                return null
            }
            val hints = Hashtable<EncodeHintType, String?>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            hints[EncodeHintType.MARGIN] = border.toString() + ""
            // 图像数据转换，使用了矩阵转换
            val bitMatrix = QRCodeWriter().encode(
                content.toString() + "",
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT,
                hints
            )
            val pixels = IntArray(QR_WIDTH * QR_HEIGHT)
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (y in 0 until QR_HEIGHT) {
                for (x in 0 until QR_WIDTH) {
                    if (bitMatrix[x, y]) {
                        pixels[y * QR_WIDTH + x] = codeColor
                    } else {
                        pixels[y * QR_WIDTH + x] = backgroundColor
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }
}