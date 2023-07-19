package com.hr.slide_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.hr.slide_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SlideViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.generateSlide()

        val viewMap = mutableMapOf<String, ImageView>()

        viewModel.slides.observe(this) {
            viewMap[it.uniqueID] = createView(it)
        }

        viewModel.selectedSlide.observe(this)
        {
            if (it.second == null) {
                viewMap[it.first?.uniqueID]?.setImageResource(0)

            } else {
                viewMap[it.second!!.uniqueID]?.setImageResource(R.drawable.border)
                viewMap[it.second!!.uniqueID]?.setBackgroundColor(Color.parseColor(it.second!!.rgb.toString()))
                viewMap[it.second!!.uniqueID]?.alpha = it.second!!.alpha.value
                //색상 변동 setBackground
                //알파 변동 alpha

                binding.btnBackground?.setBackgroundColor(Color.parseColor(it.second!!.rgb.toString()))
                binding.btnBackground?.text = it.second!!.rgb.toString()
                binding.vAlpha?.text = it.second!!.alpha.toString()
            }
        }

        binding.vSlideView?.setOnClickListener {
            viewModel.selectedSlide(null)
        }


        binding.btnBackground?.setOnClickListener {
            viewModel.changeSlideColor()
        }

        binding.btnAlphaMinus?.setOnClickListener {
            viewModel.changeSlideAlpha(Alpha.ALPHA_DOWN)
        }

        binding.btnAlphaPlus?.setOnClickListener {
            viewModel.changeSlideAlpha(Alpha.ALPHA_UP)
        }
    }

    private fun createView(slide: Slide): ImageView {
        Log.d("height", slide.toString())
        val iv = ImageView(this)
        iv.setBackgroundColor(R.drawable.button)
        val lp = ConstraintLayout.LayoutParams(slide.width, slide.height).apply {
            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        }
        iv.layoutParams = lp

        iv.setOnClickListener {
            viewModel.selectedSlide(slide)
        }
        binding.root.addView(iv)
        return iv
    }
}