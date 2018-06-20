package yujinyan.me.framemockup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import kotlinx.android.synthetic.main.activity_picture.*
import yujinyan.me.framemockup.extension.dpToPx
import yujinyan.me.framemockup.mockup.FrameModel
import yujinyan.me.framemockup.model.Picture

class PictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        val mainBitmap = if (intent.data !== null) {
            BitmapFactory.decodeStream(contentResolver.openInputStream(intent.data))
        } else {
            Picture(1456, 1020, R.drawable.p1).getBitmap(resources, 320.dpToPx(), 240.dpToPx())
        }

        mockupView.pictureBitmap = mainBitmap
        setUpBackgroundColor(mainBitmap)
        setUpEditor()

        if (savedInstanceState == null) {
            setUpEditor()
        }
    }

    private fun setUpEditor() {
        val fragment = EditorDetailFragment.newInstance("1", "2")
        fragment.optionClickedHandler = { resId: Int ->
            val frame = FrameModel(resId, width = 1881, height = 1443)
            mockupView.frameBitmap = frame.getBitmapInSize(resources, 1456, 1020)
            mockupView.invalidate()
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.editorContainer, fragment, "detail")
                .commit()
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
}
