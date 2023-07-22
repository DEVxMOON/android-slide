package com.hr.slide_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideViewModel : ViewModel() {

    private val slideManager = SlideManager()

    private val _slides = MutableLiveData<Slide>()
    val slides: LiveData<Slide> = _slides

    private val _selectedSlide = MutableLiveData<Pair<Slide?, Slide?>>()
    val selectedSlide: LiveData<Pair<Slide?, Slide?>> = _selectedSlide

    private val _slideOrderChange = MutableLiveData<Pair<Int, Int>>()
    val slideOrderChange: LiveData<Pair<Int, Int>> = _slideOrderChange

    fun selectedSlide(slide: Slide?) {
        val prev = selectedSlide.value?.second
        _selectedSlide.value = prev to slide
    }

    fun generateSlide() {
        val generatedSlide = slideManager.generateSlide()
        _slides.value = generatedSlide
    }

    fun changeSlideColor() {
        if (selectedSlide.value == null) return

        val changeColor =
            selectedSlide.value!!.second?.let { slideManager.changeSlideColor(it.uniqueID) }
        _selectedSlide.value = _selectedSlide.value?.first to changeColor
    }

    fun changeSlideAlpha(type: Int) {
        if (selectedSlide.value == null) return

        val changeAlpha =
            selectedSlide.value!!.second?.let { slideManager.changeSlideAlpha(it.uniqueID, type) }
        _selectedSlide.value = _selectedSlide.value?.first to changeAlpha
    }

    fun changeSlideOrder(from: Int, to: Int) {
        val orderChange = slideManager.changeSlideOrder(from, to)
        _slideOrderChange.value = orderChange
    }

    fun changeDisplayedSlide(slide: Slide) {
        val prev = selectedSlide.value?.second
        if (prev != slide) {
            _selectedSlide.value = prev to slide

        }
    }

}