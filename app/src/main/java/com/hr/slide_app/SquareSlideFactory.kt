package com.hr.slide_app

class SquareSlideFactory: SlideFactory(){

    override fun create(side:Int, rgb: RGB, alpha:Int): Slide {
        //return Square(generateRandomId(),generateRandomSideLength(), generateRandomColor(), generateRandomAlpha())
        return Square(generateRandomId(),side,rgb,alpha)
    }
}