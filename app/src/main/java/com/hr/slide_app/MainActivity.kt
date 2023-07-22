package com.hr.slide_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.hr.slide_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val viewModel: SlideViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMap = mutableMapOf<String, ImageView>()

        val layerViewAdapter = LayerViewAdapter().apply {
            setLayerClickEvent { slide: Slide ->
                viewModel.changeDisplayedSlide(slide)
            }
        }

        val slideItemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(object :
            ItemTouchHelperCallback.ItemTouchHelperListener {
            override fun onItemMove(from: Int, to: Int) {
                viewModel.changeSlideOrder(from, to)
            }
        }))

        /** binding */
        binding.vRecycler?.apply {
            adapter = layerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            slideItemTouchHelper.attachToRecyclerView(this)
        }

        binding.btnLayerPlus?.setOnClickListener { viewModel.generateSlide() }
        binding.vSlideView?.setOnClickListener { viewModel.selectedSlide(null) }
        binding.btnBackground?.setOnClickListener { viewModel.changeSlideColor() }
        binding.btnAlphaMinus?.setOnClickListener { viewModel.changeSlideAlpha(Alpha.ALPHA_DOWN) }
        binding.btnAlphaPlus?.setOnClickListener { viewModel.changeSlideAlpha(Alpha.ALPHA_UP) }

        /** observing */
        viewModel.slides.observe(this)
        {
            layerViewAdapter.addSlide(it)
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

                binding.btnBackground?.setBackgroundColor(Color.parseColor(it.second!!.rgb.toString()))
                binding.btnBackground?.text = it.second!!.rgb.toString()
                binding.vAlpha?.text = it.second!!.alpha.toString()
            }
        }

        viewModel.slideOrderChange.observe(this)
        {
            layerViewAdapter.changeSlideOrder(it.first, it.second)
        }
    }

    private fun createView(slide: Slide): ImageView {
        val iv = ImageView(this)
        iv.setBackgroundColor(Color.RED)
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