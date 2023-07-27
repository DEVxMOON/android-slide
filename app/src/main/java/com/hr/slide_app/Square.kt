package com.hr.slide_app

class Square(uniqueID: String, private val side: Int, rgb: RGB, alpha: Alpha) :
    Slide(uniqueID, side, side, rgb, alpha,type=0) {
    override fun toString(): String {
        return "('$uniqueID'), Side:$side, $rgb, Alpha: $alpha"
    }
}