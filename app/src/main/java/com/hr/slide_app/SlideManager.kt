package com.hr.slide_app

import kotlin.random.Random


class SlideManager {
    private val _slideList = mutableListOf<Slide>()
    private val slideList: List<Slide> = _slideList
    private val _slideMap = mutableMapOf<String, Slide>()
    private val slideMap: Map<String, Slide> = _slideMap


    //생성.
    fun generateSlide(): Slide {
        val slideFactory = SquareSlideFactory()
        val slide = slideFactory.create(200, RGB(255, 0, 0), Alpha.LV_10)
        _slideList.add(slide)
        _slideMap[slide.uniqueID] = slide
        return slide
    }

    fun changeSlideColor(uniqueID: String): Slide? {
        val changeColor = generateRandomColor()
        return slideMap[uniqueID]?.apply {
            rgb = changeColor
        }
    }

    private fun generateRandomColor(): RGB {
        return RGB(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }

    fun changeSlideAlpha(uniqueID: String, type: Int): Slide? {
        slideMap[uniqueID]?.changeAlphaLv(type)
        return slideMap[uniqueID]
    }

    fun changeSlideOrder(from: Int, to: Int): Pair<Int, Int> {
        _slideList.let {
            val slide = slideList[from]
            it.removeAt(from)
            it.add(to, slide)
        }
        return from to to
    }
}