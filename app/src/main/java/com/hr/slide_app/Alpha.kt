package com.hr.slide_app

enum class Alpha(val level: Int, val value: Float) {
    LV_1(1, 0.1f),
    LV_2(2, 0.2f),
    LV_3(3, 0.3f),
    LV_4(4, 0.4f),
    LV_5(5, 0.5f),
    LV_6(6, 0.6f),
    LV_7(7, 0.7f),
    LV_8(8, 0.8f),
    LV_9(9, 0.9f),
    LV_10(10, 1f);

    override fun toString(): String {
        return level.toString()
    }

    companion object
    {
        const val ALPHA_UP=1
        const val ALPHA_DOWN=-1
    }
}