package com.hr.slide_app

class RGB(var r: Int, var g: Int, var b: Int) {
    override fun toString(): String {
        val hexR = r.toString(16).padStart(2, '0')
        val hexG = g.toString(16).padStart(2, '0')
        val hexB = b.toString(16).padStart(2, '0')
        return "#$hexR$hexG$hexB"
    }
}