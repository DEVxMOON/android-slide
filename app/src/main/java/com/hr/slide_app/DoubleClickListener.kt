package com.hr.slide_app

import android.view.View

abstract class DoubleClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    private val doubleClickTimeLimit: Long = 500

    override fun onClick(v: View) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < doubleClickTimeLimit) {
            onDoubleClick(v)
        }
        lastClickTime = clickTime
    }

    abstract fun onDoubleClick(v: View)
}