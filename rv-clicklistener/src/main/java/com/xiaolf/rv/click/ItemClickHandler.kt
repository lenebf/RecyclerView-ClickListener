package com.xiaolf.rv.click

import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

class ItemClickHandler(
    private val recyclerView: RecyclerView,
    private val itemClickListener: OnItemClickListener?,
    private val itemLongClickListener: OnItemLongClickListener?,
    private val itemDoubleClickListener: OnItemDoubleClickListener?,
    private val clickableChildren: List<Int>
) : OnItemTouchListener {

    private var disallowIntercept = false
    private val gestureDetector = GestureDetector(recyclerView.context,
        object : SimpleOnGestureListener() {

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if (itemClickListener == null) {
                    return false
                }
                if (itemDoubleClickListener != null) {
                    // When the double click listener is set, handle the click event in the onSingleTapConfirmed function
                    return false
                }
                return performClick(e)
            }

            override fun onLongPress(e: MotionEvent) {
                if (itemLongClickListener == null) {
                    return
                }
                val childView = recyclerView.findChildViewUnder(e.x, e.y) ?: return
                val position = recyclerView.getChildLayoutPosition(childView)
                if (position == NO_POSITION) {
                    return
                }
                performLongClick(e, childView, position)
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (itemClickListener == null || itemDoubleClickListener == null) {
                    return false
                }
                return performClick(e)
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                if (itemDoubleClickListener == null) {
                    return false
                }
                return performDoubleClick(e)
            }
        })

    private fun performClick(e: MotionEvent): Boolean {
        val itemView = recyclerView.findChildViewUnder(e.x, e.y) ?: return false
        val itemPosition = recyclerView.getChildLayoutPosition(itemView)
        if (itemPosition == NO_POSITION) {
            return false
        }
        if (clickableChildren.isNotEmpty() && itemView is ViewGroup) {
            val targetView = findTargetView(e, itemView)
            if (targetView != null) {
                itemClickListener?.onItemClick(targetView, targetView.id, itemPosition)
                    ?: return false
                return true
            }
        }
        val itemViewId = itemView.id
        itemClickListener?.onItemClick(itemView, itemViewId, itemPosition) ?: return false
        return true
    }

    private fun performLongClick(e: MotionEvent, itemView: View, itemPosition: Int) {
        if (clickableChildren.isNotEmpty() && itemView is ViewGroup) {
            val targetView = findTargetView(e, itemView)
            if (targetView != null) {
                itemLongClickListener?.onItemLongClick(targetView, targetView.id, itemPosition)
                return
            }
        }
        val itemViewId = itemView.id
        itemLongClickListener?.onItemLongClick(itemView, itemViewId, itemPosition)
    }

    private fun performDoubleClick(e: MotionEvent): Boolean {
        val itemView = recyclerView.findChildViewUnder(e.x, e.y) ?: return false
        val itemPosition = recyclerView.getChildLayoutPosition(itemView)
        if (itemPosition == NO_POSITION) {
            return false
        }
        if (clickableChildren.isNotEmpty() && itemView is ViewGroup) {
            val targetView = findTargetView(e, itemView)
            if (targetView != null) {
                itemDoubleClickListener?.onItemDoubleClick(targetView, targetView.id, itemPosition)
                    ?: return false
                return true
            }
        }
        val itemViewId = itemView.id
        itemDoubleClickListener?.onItemDoubleClick(itemView, itemViewId, itemPosition)
            ?: return false
        return true
    }

    /** Recursively find the topmost child View */
    private fun findTargetView(e: MotionEvent, group: ViewGroup): View? {
        group.children.forEach { childView ->
            if (!childView.isTouchIn(e)) {
                return@forEach
            }
            if (childView is ViewGroup) {
                val targetView = findTargetView(e, childView)
                if (targetView == null) {
                    return@forEach
                } else {
                    return targetView
                }
            } else if (clickableChildren.contains(childView.id)) {
                return childView
            }
        }
        return if (clickableChildren.contains(group.id)) {
            group
        } else {
            null
        }
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (disallowIntercept) {
            return false
        }
        if (itemClickListener == null && itemLongClickListener == null
            && itemDoubleClickListener == null
        ) {
            return false
        }
        val childView = rv.findChildViewUnder(e.x, e.y) ?: return false
        if (rv.getChildLayoutPosition(childView) == NO_POSITION) {
            return false
        }
        // Any events sent to the RecyclerView will be sent here first,
        // so we don't care the return result of gesture detector。If we consume events,
        // RecyclerView will not be able to scroll.
        gestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        this.disallowIntercept = disallowIntercept
    }
}