package yujinyan.me.framemockup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import yujinyan.me.framemockup.mockup.FrameModel

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val frame = FrameModel(R.drawable.f3, width = 1881, height = 1443)

//        val mockupView = MockupView(this, null)
//        root.addView(mockupView)
    }
}
