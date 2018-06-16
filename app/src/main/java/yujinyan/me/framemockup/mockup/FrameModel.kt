package yujinyan.me.framemockup.mockup

import android.support.annotation.DrawableRes

class FrameModel(@DrawableRes res: Int, width: Int, height: Int) :
        PictureModel(res, width, height) {
}
//class FrameModel(framePicture: PictureModel) {
//    val width: Int = framePicture.width
//    val height: Int = framePicture.height
//
//    constructor(@DrawableRes res: Int, width: Int, height: Int) : this(
//            PictureModel(res, width, height)
//    )
//}

