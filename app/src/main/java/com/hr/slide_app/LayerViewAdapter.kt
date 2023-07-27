package com.hr.slide_app

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
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

        init {
            binding.layerItem.setOnLongClickListener {
                val pop = PopupMenu(binding.root.context, it)
                pop.inflate(R.menu.slide_menu)

                pop.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.bring_to_front -> {
                            changeSlideOrder(layoutPosition, 0)
                            return@setOnMenuItemClickListener true
                        }

                        R.id.bring_forward -> {
                            changeSlideOrder(layoutPosition, layoutPosition - 1)
                            return@setOnMenuItemClickListener true
                        }

                        R.id.send_backward -> {
                            changeSlideOrder(layoutPosition, layoutPosition + 1)
                            return@setOnMenuItemClickListener true
                        }

                        R.id.send_to_back -> {
                            changeSlideOrder(layoutPosition, layerList.lastIndex)
                            return@setOnMenuItemClickListener true
                        }

                        else -> return@setOnMenuItemClickListener false
                    }
                }
                pop.show()
                true
            }
        }

        fun bind(pos: Int, slide: Slide) {
            val idx = pos + 1
            binding.txtNumbering.text = idx.toString()
            if(slide.type==0)
            {
                binding.type.setImageResource(R.drawable.square_png)
            }
            else if(slide.type==1)
            {
                binding.type.setImageResource(R.drawable.image_png)
            }

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