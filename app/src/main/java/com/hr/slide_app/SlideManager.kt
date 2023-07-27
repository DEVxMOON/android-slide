package com.hr.slide_app

import android.net.Uri
import android.util.Log
import kotlin.random.Random


class SlideManager {
    private val _slideList = mutableListOf<Slide>()
    private val slideList: List<Slide> = _slideList
    private val _slideMap = mutableMapOf<String, Slide>()
    private val slideMap: Map<String, Slide> = _slideMap


    //생성.
    fun generateSquare(): Slide {
        val slideFactory = SquareSlideFactory()
        val slide = slideFactory.create(200,200, RGB(255, 0, 0), Alpha.LV_10)
        _slideList.add(slide)
        _slideMap[slide.uniqueID] = slide
        return slide
    }

    fun generateImage(): Slide{
        val slideFactory = ImageSlideFactory()
        val slide = slideFactory.create(200,200, RGB(175 , 175,175), Alpha.LV_10)
        _slideList.add(slide)
        _slideMap[slide.uniqueID] = slide
        return slide
    }

    fun changeSlideColor(uniqueID: String): Slide? {
        val changeColor = generateRandomColor()
        return if(slideMap[uniqueID]?.type==0) {
            slideMap[uniqueID]?.apply {
                rgb = changeColor
            }
        } else{
            null
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

    fun changeImage(uniqueID: String, imageUri: Uri, width: Int, height: Int): Slide? {
        val slide = _slideMap[uniqueID]
        if (slide is Image) {
            slide.img = imageUri
            slide.width = width
            slide.height = height
            Log.d("SLIDE DATA ", slide.toString())
            return slide
        } else {
            return null
        }
    }
}