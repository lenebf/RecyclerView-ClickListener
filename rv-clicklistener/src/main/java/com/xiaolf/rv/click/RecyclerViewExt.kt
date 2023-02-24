package com.xiaolf.rv.click

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/** Setting up multiple click listeners */
fun RecyclerView.setOnItemClickListener(
    itemClickListener: OnItemClickListener?,
    itemLongClickListener: OnItemLongClickListener?,
    itemDoubleClickListener: OnItemDoubleClickListener?,
    clickableChildren: List<Int> = emptyList()
) {
    addOnItemTouchListener(
        ItemClickHandler(
            this,
            itemClickListener,
            itemLongClickListener,
            itemDoubleClickListener,
            clickableChildren
        )
    )
}

/** Setting up a click listener */
fun RecyclerView.setOnItemClickListener(
    itemClickListener: OnItemClickListener,
    clickableChildren: List<Int> = emptyList()
) {
    setOnItemClickListener(itemClickListener, null, null, clickableChildren)
}

/** Setting up a long click listener */
fun RecyclerView.setOnItemLongClickListener(
    itemLongClickListener: OnItemLongClickListener,
    clickableChildren: List<Int> = emptyList()
) {
    setOnItemClickListener(null, itemLongClickListener, null, clickableChildren)
}

/** Setting up a double click listener */
fun RecyclerView.setOnItemDoubleClickListener(
    itemDoubleClickListener: OnItemDoubleClickListener?,
    clickableChildren: List<Int> = emptyList()
) {
    setOnItemClickListener(null, null, itemDoubleClickListener, clickableChildren)
}

internal fun View.isTouchIn(e: MotionEvent): Boolean {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val left = location[0].toFloat()
    val top = location[1].toFloat()
    val right = left + measuredWidth
    val bottom = top + measuredHeight
    return e.rawX in left..right && e.rawY in top..bottom
}