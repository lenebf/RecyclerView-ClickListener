package com.xiaolf.rv.click

import android.view.View

interface OnItemClickListener {

    fun onItemClick(view: View, id: Int, position: Int)
}