package com.hr.slide_app

open class Slide(var uniqueID: String, var width: Int,var height:Int, var rgb: RGB, var alpha: Int) {
    override fun toString(): String {
        return "('$uniqueID'), Width:$width, Height:$height, $rgb, Alpha: $alpha"
    }
}