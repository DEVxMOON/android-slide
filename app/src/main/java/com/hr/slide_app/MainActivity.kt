package com.hr.slide_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hr.slide_app.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private val viewModel: SlideViewModel by viewModels()

    val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val imageData = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                val width = imageData.width
                val height = imageData.height
                Log.d("SIZE", width.toString() + height.toString())
                viewModel.changeImage(uri, width, height)
            }
        }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMap = mutableMapOf<String, ImageView>()

        val layerViewAdapter = LayerViewAdapter().apply {
            setLayerClickEvent { slide: Slide ->
                binding.root.removeView(viewMap[slide.uniqueID])
                binding.root.addView(viewMap[slide.uniqueID])

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

        binding.btnLayerPlus?.setOnClickListener {
            val random = Random()
            val type = random.nextInt(2)

            if (type == 0) viewModel.generateSquare()
            else viewModel.generateImage()

        }
        binding.vSlideView?.setOnClickListener { viewModel.selectedSlide(null) }
        binding.btnBackground?.setOnClickListener { viewModel.changeSlideColor() }
        binding.btnAlphaMinus?.setOnClickListener { viewModel.changeSlideAlpha(Alpha.ALPHA_DOWN) }
        binding.btnAlphaPlus?.setOnClickListener { viewModel.changeSlideAlpha(Alpha.ALPHA_UP) }

        /** observing */
        viewModel.slides.observe(this)
        {
            layerViewAdapter.addSlide(it)
            if (it.type == 0) {
                viewMap[it.uniqueID] = createSquare(it)
            } else if (it.type == 1) {
                viewMap[it.uniqueID] = createImage(it, 200, 200)
            }
        }

        viewModel.selectedSlide.observe(this)
        {
            if (it.second == null) {
                viewMap[it.first?.uniqueID]?.setBackgroundResource(0)

            } else {
                if (it.second!!.type == 0) {
                    viewMap[it.second!!.uniqueID]?.setBackgroundResource(R.drawable.border)
                    viewMap[it.second!!.uniqueID]?.alpha = it.second!!.alpha.value
                    viewMap[it.second!!.uniqueID]?.setColorFilter(Color.parseColor(it.second!!.rgb.toString()))
                    binding.btnBackground?.text = it.second!!.rgb.toString()
                } else if (it.second!!.type == 1) {
                    binding.root.removeView(viewMap[it.second!!.uniqueID])
                    viewMap[it.second!!.uniqueID] =
                        createImage(it.second!!, it.second!!.width, it.second!!.height)
                    viewMap[it.second!!.uniqueID]?.let { it1 ->
                        Glide.with(this).load((it.second as Image).img).into(it1)
                    }
                    viewMap[it.second!!.uniqueID]?.setBackgroundResource(R.drawable.border)
                    viewMap[it.second!!.uniqueID]?.alpha = it.second!!.alpha.value
                    binding.btnBackground?.text = " "
                }

                binding.btnBackground?.setBackgroundColor(Color.parseColor(it.second!!.rgb.toString()))
                binding.vAlpha?.text = it.second!!.alpha.toString()
            }
        }

        viewModel.slideOrderChange.observe(this)
        {
            layerViewAdapter.changeSlideOrder(it.first, it.second)
        }
    }

    private fun createSquare(slide: Slide): ImageView {
        val sq = ImageView(this)
        sq.setBackgroundColor(Color.RED)
        val lp = ConstraintLayout.LayoutParams(slide.width, slide.height).apply {
            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        }
        sq.layoutParams = lp
        sq.setPadding(3, 3, 3, 3)
        sq.setImageResource(R.drawable.square)
        sq.setOnClickListener {
            viewModel.selectedSlide(slide)
        }
        binding.root.addView(sq)
        return sq
    }

    private fun createImage(slide: Slide, width: Int, height: Int): ImageView {

        val iv = ImageView(this)
        val lp = ConstraintLayout.LayoutParams(width, height).apply {
            startToStart = binding.vSlideView!!.id
            endToEnd = binding.vSlideView!!.id
            topToTop = binding.vSlideView!!.id
        }
        iv.layoutParams = lp
        iv.setPadding(10, 10, 10, 10)
        iv.setImageResource(R.drawable.baseline_image_search_24)
        iv.setOnClickListener {
            viewModel.selectedSlide(slide)
        }

        iv.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View) {
                pickMedia.launch(PickVisualMediaRequest((ActivityResultContracts.PickVisualMedia.ImageOnly)))
            }
        })

        binding.root.addView(iv)
        return iv
    }
}