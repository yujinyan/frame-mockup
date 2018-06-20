package yujinyan.me.framemockup

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import junit.framework.Assert.assertEquals
import org.junit.Test
import yujinyan.me.framemockup.model.Picture

class JsonUnitTest {
    private val moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(Picture::class.java)
    private val jsonArrayAdapter = moshi
            .adapter<List<Picture>>(Types.newParameterizedType(List::class.java, Picture::class.java))

    @Test
    fun can_serialize_single_model() {
        val picture: Picture? = jsonAdapter.fromJson("""
            {
                "width": 100,
                "height": 200,
                "res_id": 1
            }
        """.trimIndent())
        assertEquals(100, picture!!.width)
        assertEquals(200, picture.height)
        assertEquals(1, picture.resId)
    }

    @Test
    fun can_serialize_as_list() {
        val pictures = jsonArrayAdapter.fromJson("""
            [{
                "width": 100,
                "height": 200,
                "res_id": 1
            }, {
                "width": 100,
                "height": 200,
                "res_id": 1
            }]
        """.trimIndent())
        pictures!!.forEach {
            assertEquals(100, it.width)
        }
    }
}