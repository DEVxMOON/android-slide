package com.hr.slide_app

class ImageSlideFactory : SlideFactory() {

    override fun create(width: Int, height: Int, rgb: RGB, alpha: Alpha): Slide {
        return Image(generateRandomId(), width, height, rgb, alpha)
    }
}