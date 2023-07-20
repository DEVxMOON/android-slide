package com.hr.slide_app

import kotlin.random.Random


class SlideManager {
    private val viewMap = mutableMapOf<String, Slide>()

    //생성.
    fun generateSlide(): Slide {
        val slideFactory = SquareSlideFactory()
        val slide = slideFactory.create(200, RGB(255, 0, 0), Alpha.LV_10)
        viewMap[slide.uniqueID] = slide
        return slide
    }

    fun changeSlideColor(uniqueID: String): Slide? {
        val changeColor = generateRandomColor()
        viewMap[uniqueID]?.rgb = changeColor
        return viewMap[uniqueID]
    }

    private fun generateRandomColor(): RGB {
        return RGB(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }

    fun changeSlideAlpha(uniqueID: String, type: Int): Slide? {
        viewMap[uniqueID]?.changeAlphaLv(type)
        return viewMap[uniqueID]
    }
}