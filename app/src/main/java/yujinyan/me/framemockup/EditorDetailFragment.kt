package yujinyan.me.framemockup

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import yujinyan.me.framemockup.extension.decodeResourceInSize
import yujinyan.me.framemockup.extension.dpToPx


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditorDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditorDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var optionClickedHandler: ((resId: Int) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_editor_detail, container, false)
        val root = view.findViewById<ViewGroup>(R.id.root)
        arrayOf(R.drawable.f1, R.drawable.f2, R.drawable.f3).forEach {
            val imageView = ImageView(activity)
            val bitmap = activity!!.decodeResourceInSize(it, 120.dpToPx(), 120.dpToPx())
            val resId = it
            imageView.layoutParams = LinearLayout.LayoutParams(
                    120.dpToPx(), 80.dpToPx())
            imageView.setImageBitmap(bitmap)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setOnClickListener {
                optionClickedHandler?.invoke(resId)
            }
            root.addView(imageView)
        }
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditorDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EditorDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
