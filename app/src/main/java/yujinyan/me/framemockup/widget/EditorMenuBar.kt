package yujinyan.me.framemockup.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import kotlinx.android.synthetic.main.widget_editor_menu_bar.view.*
import yujinyan.me.framemockup.R

class EditorMenuBar(context: Context, attrs: AttributeSet) : HorizontalScrollView(context, attrs) {
    val onClick: ((Int) -> Unit)? = null

    var selectedIndex = 0
        set(value) {
            toggleAnimation(selectedIndex, value)
            field = value
        }

    private var touchingViewIndex = 0
    private var touchingView: View? = null
    private val onClickListener = View.OnClickListener { v ->
        //        val prevView = touchingView
        when {
            v === item0 -> selectedIndex = 0
            v === item1 -> selectedIndex = 1
            v === item2 -> selectedIndex = 2
        }
        Log.d("click listener", "$touchingViewIndex")
//        touchingView = v
        onClick?.invoke(selectedIndex)
    }

    private lateinit var itemsMap: Map<Int, View>

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_editor_menu_bar, this, true)
        itemsMap = mapOf(
                0 to item0,
                1 to item1,
                2 to item2
        )
        itemsMap.values.forEach {
            it.setOnClickListener(onClickListener)
            it.alpha = .3f
        }
        selectedIndex = 0
//        item0.setOnClickListener(onClickListener)
//        item1.setOnClickListener(onClickListener)
//        item2.setOnClickListener(onClickListener)
    }

    private fun toggleAnimation(prevIndex: Int, newIndex: Int) {
        val preView = itemsMap[prevIndex]
        val newView = itemsMap[newIndex]
        preView?.animate()?.alpha(.3f)?.setDuration(300)?.start()
        newView?.animate()?.alpha(1f)?.setDuration(300)?.start()
    }
}