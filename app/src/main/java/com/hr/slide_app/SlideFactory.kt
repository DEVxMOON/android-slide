package com.hr.slide_app

import kotlin.random.Random

abstract class SlideFactory {
    private val uniqueIdList = mutableSetOf<String>()

    abstract fun create(side: Int, rgb: RGB, alpha: Alpha): Slide

    fun generateRandomId(): String {

        var uniqueId: String
        var isDuplicate: Boolean
        val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
        val random = Random(System.currentTimeMillis())

        do {
            val part1 = (1..3).map { chars[random.nextInt(chars.length)] }.joinToString("")
            val part2 = (1..3).map { chars[random.nextInt(chars.length)] }.joinToString("")
            val part3 = (1..3).map { chars[random.nextInt(chars.length)] }.joinToString("")

            uniqueId = "$part1-$part2-$part3"
            isDuplicate = checkDuplicate(uniqueId)
        } while (isDuplicate)
        return uniqueId
    }

    private fun checkDuplicate(uniqueId: String): Boolean {
        return uniqueIdList.contains(uniqueId).also {
            if (it) {
                uniqueIdList.add(uniqueId)
            }
        }
    }

}