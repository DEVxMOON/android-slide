package com.hr.slide_app

class SquareSlideFactory: SlideFactory(){


    override fun create(side:Int, rgb: RGB, alpha:Alpha): Slide {
        return Square(generateRandomId(),side,rgb,alpha)
    }
}