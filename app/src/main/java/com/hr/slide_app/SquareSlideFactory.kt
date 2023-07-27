package com.hr.slide_app

class SquareSlideFactory : SlideFactory() {
    override fun create(width: Int, height: Int, rgb: RGB, alpha: Alpha): Slide {
        return Square(generateRandomId(), width, rgb, alpha)
    }
}