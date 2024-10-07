package com.patrik_frankovic.memories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class ImageRecyclerAdapter(
    private val images: ArrayList<ImageInfo>,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mlistener: onClickListener

    interface onClickListener {
        fun onClickListener(position: Int)
    }

    fun setOnItemClick(listener: onClickListener){
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false),
            mlistener
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> {
                holder.bind(position, images[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(private val view: View, listener: onClickListener): RecyclerView.ViewHolder(view) {
        init{
            itemView.setOnClickListener{
                listener.onClickListener(adapterPosition)
            }
        }
        private val recyclerImage =
            view.findViewById<ImageView>(R.id.recyclerImage)

        fun bind(
            index: Int,
            image: ImageInfo
        ) {
            Glide.with(view.context).load(image.imageUrl).into(recyclerImage)
        }
    }
}

