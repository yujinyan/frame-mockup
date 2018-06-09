package yujinyan.me.framemockup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import yujinyan.me.framemockup.extension.dpToPx

class MainActivity : AppCompatActivity() {

    companion object {
        private const val FRAME_WIDTH = 320
        private const val FRAME_HEIGHT = 240
    }
    private var scaleFactor = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainImageView = findViewById<ImageView>(R.id.iv_main)
        val bgBitmap = getBitmap(R.drawable.f1, FRAME_WIDTH.dpToPx(), FRAME_HEIGHT.dpToPx())
        scaleFactor = bgBitmap.width / 2488f
        Log.d("bgBitmap", "width: ${bgBitmap.width}, height: ${bgBitmap.height}")
        Log.d("scaleFactor", "$scaleFactor")
        val bgCanvas = Canvas(bgBitmap)
        val rect = Rect(getScaledDimen(408), getScaledDimen(390), getScaledDimen(1864), getScaledDimen(1410))
        val mainBitmap = getBitmap(R.drawable.p1, getScaledDimen(1456), getScaledDimen(1020))
        // val mainBitmap = getBitmap(R.drawable.p2, getScaledDimen(744), getScaledDimen(1392))
        bgCanvas.drawBitmap(mainBitmap, null, rect, null)
        mainImageView.setImageBitmap(bgBitmap)
        mainImageView.scaleType = ImageView.ScaleType.FIT_CENTER
    }

    private fun getScaledDimen(size: Int): Int {
        Log.d("scaleFactor", "using $scaleFactor")
        return (size * scaleFactor).toInt()
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val width = options.outWidth
        val height = options.outHeight
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    private fun getBitmap(resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        options.inMutable = true
        return BitmapFactory.decodeResource(resources, resId, options)
    }
}
