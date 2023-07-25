package com.hr.slide_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hr.slide_app.databinding.LayerLayoutBinding

class LayerViewAdapter : RecyclerView.Adapter<LayerViewAdapter.LayerViewHolder>() {

    private var selectedIndex: Int? = null
    private var listener: LayerClickListener? = null
    private val layerList = mutableListOf<Slide>()

    private interface LayerClickListener {
        fun onLayerClicked(slide: Slide)
    }

    inner class LayerViewHolder(private val binding: LayerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, slide: Slide) {
            val idx = pos + 1
            binding.txtNumbering.text = idx.toString()
            binding.type.setImageResource(R.drawable.checkbox_indeterminate_filled_icon)

            binding.root.setOnClickListener { listener?.onLayerClicked(slide) }
            if (pos == selectedIndex) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    )
                )
            } else {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayerViewHolder {
        return LayerViewHolder(
            LayerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = layerList.size

    override fun onBindViewHolder(holder: LayerViewHolder, pos: Int) {

        holder.bind(pos, layerList[pos])
    }

    fun addSlide(slide: Slide) {
        layerList.add(slide)
        notifyItemInserted(layerList.lastIndex)
    }

    fun setLayerClickEvent(onClick: (Slide) -> Unit) {
        listener = object : LayerClickListener {
            override fun onLayerClicked(slide: Slide) {
                onClick.invoke(slide)
            }
        }
    }

    fun changeSlideOrder(from: Int, to: Int) {
        val slide = layerList[from]
        layerList.removeAt(from)
        layerList.add(to, slide)
        if (from == selectedIndex) selectedIndex = to

        notifyItemMoved(from, to)
        notifyItemChanged(from)
        notifyItemChanged(to)
    }
}