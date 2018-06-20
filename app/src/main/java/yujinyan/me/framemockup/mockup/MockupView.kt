package yujinyan.me.framemockup.mockup

import android.content.Context
import android.graphics.*
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.Log
import android.view.View
import yujinyan.me.framemockup.R
import yujinyan.me.framemockup.extension.dpToPx

class MockupView(context: Context,
                 attributeSet: AttributeSet?) : View(context, attributeSet) {
    companion object {
        private const val FRAME_WIDTH = 320
        private const val FRAME_HEIGHT = 240
        private const val SHADOW_BLUR_RADIUS = 80
    }

    public var frame = FrameModel(R.drawable.f3, width = 1881, height = 1443)
        set(value) {
            field = value
            frameBitmap = value.getBitmapInSize(resources, FRAME_WIDTH.dpToPx(), FRAME_HEIGHT.dpToPx())
            invalidate()
        }

    public var picture = PictureModel(R.drawable.p1, width = 1456, height = 1020)
        set(value) {
            field = value
            pictureBitmap = picture.getBitmapInSize(resources, 1456, 1020)
        }


    public var pictureBitmap: Bitmap? = null
        set(value) {
            field = value
            invalidate()
        }

    public var frameBitmap: Bitmap
    private var paint: Paint
    private var frameLeft: Int = 0
    private var frameTop: Int = 0
    private var frameRect = Rect()
    private var pictureRect = Rect()
    private var shadowDrawable: ShapeDrawable
    private var layerDrawable: LayerDrawable
    private var scaleFactor = 1f

    constructor(context: Context, frame: FrameModel, picture: PictureModel) :
            this(context, null) {
        this.frame = frame
        this.picture = picture
    }

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        frameBitmap = frame.getBitmapInSize(resources, FRAME_WIDTH.dpToPx(), FRAME_HEIGHT.dpToPx())
        pictureBitmap = picture.getBitmapInSize(resources, 1456, 1020)
//        pictureBitmap = getBitmap(R.drawable.p2, 1456, 1020)
        setScaleFactor(frameBitmap.width)

        Log.d("MockupView", "picture btm w ${pictureBitmap?.width}, h ${pictureBitmap?.height}")
        Log.d("MockupView", "frame btm w ${frameBitmap.width}, h ${frameBitmap.height}")
        Log.d("MockupView", "scaleFactor $scaleFactor")
        shadowDrawable = ShapeDrawable()

        val outerRadiusValue = 80f
        val outerRadius = FloatArray(120) { _ -> outerRadiusValue }
        shadowDrawable.shape = RoundRectShape(outerRadius, null, null)
        shadowDrawable.paint.color = Color.argb(120, 0, 0, 0)
        shadowDrawable.paint.setShadowLayer(80f, 10f, 20f, Color.argb(100, 0, 0, 0))

        layerDrawable = LayerDrawable(arrayOf(shadowDrawable))
    }

    override fun onDraw(canvas: Canvas?) {
        frameLeft = (width - FRAME_WIDTH.dpToPx()) / 2
        frameTop = (height - FRAME_HEIGHT.dpToPx()) / 2
        frameRect.set(frameLeft, frameTop,
                frameLeft + FRAME_WIDTH.dpToPx(),
                frameTop + FRAME_HEIGHT.dpToPx())
        setScaleFactor(frameRect.width())

        layerDrawable.setLayerInset(0, frameLeft + 40, frameTop + 80, frameLeft + 40, frameTop + 20)

        background = layerDrawable
        pictureRect.set(frameRect)
        pictureRect.inset(272.scaled(), 256.scaled())

        canvas!!.drawBitmap(frameBitmap, null, frameRect, paint)
        if (pictureBitmap != null) {
            canvas.drawBitmap(pictureBitmap, getSourceRect(pictureBitmap!!, frameRect), pictureRect, null)
        }
    }

    private fun setScaleFactor(frameWidth: Int) {
        scaleFactor = frameWidth / 1881f
    }

    private fun Int.scaled(): Int {
        Log.d("scaleFactor", "using $scaleFactor")
        return (this * scaleFactor).toInt()
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
}