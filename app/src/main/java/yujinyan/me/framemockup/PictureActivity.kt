package yujinyan.me.framemockup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.util.Log
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : AppCompatActivity() {
    companion object {
        private const val FRAME_WIDTH = 320
        private const val FRAME_HEIGHT = 240
    }

    private var scaleFactor = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)
//        val mainImageView = findViewById<ImageView>(R.id.iv_main)
//        val bgBitmap = getBitmap(R.drawable.f3, FRAME_WIDTH.dpToPx(), FRAME_HEIGHT.dpToPx())
//        scaleFactor = bgBitmap.width / 2488f
//        Log.d("bgBitmap", "width: ${bgBitmap.width}, height: ${bgBitmap.height}")
//        Log.d("scaleFactor", "$scaleFactor")
//        val bgCanvas = Canvas(bgBitmap)
//        val rect = Rect(getScaledDimen(408), getScaledDimen(390), getScaledDimen(1864), getScaledDimen(1410))

//        val intent = this.intent
        val mainBitmap = if (intent.data !== null) {
            BitmapFactory.decodeStream(contentResolver.openInputStream(intent.data))
        } else {
            getBitmap(R.drawable.p1, getScaledDimen(1456), getScaledDimen(1020))
        }

        mockupView.pictureBitmap = mainBitmap
        setUpBackgroundColor(mainBitmap)
        // val mainBitmap = getBitmap(R.drawable.p2, getScaledDimen(744), getScaledDimen(1392))
//        Log.d("mainBitmap", "width: ${mainBitmap.width}, height: ${mainBitmap.height}")
//        val paint = Paint()
//        paint.setShadowLayer(20f, 5f, 5f, Color.argb(30, 0,0,0))
//        val shadow = bgBitmap.extractAlpha()
//        val shadowPaint = Paint()
//        shadowPaint.setShadowLayer(20f, 5f, 5f, Color.argb(100, 0,0,0))
//        val shadowRect = Rect(0, 0, bgBitmap.width, bgBitmap.height)
//        shadowRect.offset(getScaledDimen(200), getScaledDimen(200))
//        bgCanvas.drawBitmap(shadow, null, shadowRect, shadowPaint)

//        bgCanvas.drawBitmap(mainBitmap, getSourceRect(mainBitmap, rect), rect, null)

//        shadowPaint.color = Color.argb(0.4f, 0f, 0f, 0f)
//        bgCanvas.drawBitmap(mainBitmap, null, rect, null)
//        mainImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//        mainImageView.setImageBitmap(bgBitmap)
//        mainImageView.scaleType = ImageView.ScaleType.FIT_CENTER

        setUpEditor()
    }

    private fun getSourceRect(bitmap: Bitmap, dstRect: Rect): Rect {
        val result = Rect()
        val rBitmap: Float = bitmap.width / bitmap.height.toFloat()
        val rDst: Float = dstRect.width() / dstRect.height().toFloat()

        fun calculateOffsetForCenter(a: Int, b: Int): Int = (a - b) / 2

        val height: Float
        val width: Float
        if (rBitmap > rDst) {
            height = bitmap.height.toFloat()
            width = height * rDst
        } else {
            width = bitmap.width.toFloat()
            height = width / rDst
        }
        Log.d("source rect", "width: $width, height: $height")
        result.right = width.toInt()
        result.bottom = height.toInt()
        if (rBitmap > rDst) {
            result.offset(calculateOffsetForCenter(bitmap.width, width.toInt()), 0)
        } else {
            result.offset(0, calculateOffsetForCenter(bitmap.height, height.toInt()))
        }
        Log.d("source rect", result.flattenToString())
        return result
    }

    private fun setUpEditor() {
//        with(findViewById())
    }

    private fun setUpBackgroundColor(bitmap: Bitmap) {
        val palette = Palette.from(bitmap).generate()
        val swatch = palette.darkVibrantSwatch
        with(findViewById<ConstraintLayout>(R.id.root)) {
            if (swatch !== null) {
                setBackgroundColor(swatch.rgb)
            }
        }
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
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeResource(resources, resId, options)
    }
}
