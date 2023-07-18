package com.hr.slide_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val slideFactory = SquareSlideFactory()

        val rect1 = slideFactory.create(216,RGB(245,0,245),9)
        val rect2 = slideFactory.create(384,RGB(43,124,95),5)
        val rect3 = slideFactory.create(108,RGB(98,244,15),7)
        val rect4 = slideFactory.create(233,RGB(125,39,99),1)

        Log.d("Square", "Rect1 $rect1")
        Log.d("Square", "Rect2 $rect2")
        Log.d("Square", "Rect3 $rect3")
        Log.d("Square", "Rect4 $rect4")

    }
}