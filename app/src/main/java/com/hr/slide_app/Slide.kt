package com.hr.slide_app

open class Slide(
    val uniqueID: String,
    var width: Int,
    var height: Int,
    var rgb: RGB,
    var alpha: Alpha,
    val type : Int
) {
    override fun toString(): String {
        return "('$uniqueID'), Width:$width, Height:$height, $rgb, Alpha: $alpha"
    }

    fun changeAlphaLv(type: Int): Boolean {
        if (type != Alpha.ALPHA_UP && type != Alpha.ALPHA_DOWN) {
            return false
        }
        if (alpha == Alpha.LV_10 && type == Alpha.ALPHA_UP) {
            return false
        }
        if (alpha == Alpha.LV_1 && type == Alpha.ALPHA_DOWN) {
            return false
        }
        alpha = Alpha.values()[alpha.level + type - 1]
        return true
    }
}