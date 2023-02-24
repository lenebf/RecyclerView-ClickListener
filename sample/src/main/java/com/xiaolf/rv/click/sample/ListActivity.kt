package com.xiaolf.rv.click.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaolf.rv.click.OnItemClickListener
import com.xiaolf.rv.click.OnItemDoubleClickListener
import com.xiaolf.rv.click.OnItemLongClickListener
import com.xiaolf.rv.click.setOnItemClickListener

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ListAdapter(this)
        recyclerView.setOnItemClickListener(
            object : OnItemClickListener {
                override fun onItemClick(view: View, id: Int, position: Int) {
                    Log.d("RVU", "onItemClick : ${id.viewName()}")
                }
            },
            object : OnItemLongClickListener {
                override fun onItemLongClick(view: View, id: Int, position: Int) {
                    Log.d("RVU", "onItemLongClick : ${id.viewName()}")
                }
            },
            object : OnItemDoubleClickListener {
                override fun onItemDoubleClick(view: View, id: Int, position: Int) {
                    Log.d("RVU", "onItemDoubleClick : ${id.viewName()}")
                }
            },
            arrayListOf(R.id.iv_header, R.id.tv_title, R.id.tv_msg)
        )
    }

    private fun Int.viewName(): String {
        return when (this) {
            R.id.iv_header -> "Header"
            R.id.tv_title -> "Title"
            R.id.tv_msg -> "Message"
            else -> "None"
        }
    }
}