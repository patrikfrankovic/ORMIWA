package com.patrik_frankovic.lv7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MakeUpRecyclerAdapter(private val items: ArrayList<ResponseData>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return MakeUpViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.makeup_layout, parent,
                false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int) {
        when(holder) {
            is MakeUpViewHolder -> {
                holder.bind(items[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class MakeUpViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        private val photoImage: ImageView =
            itemView.findViewById(R.id.makeUpPhoto)
        private val makeUpName: TextView =
            itemView.findViewById(R.id.makeUpName)
        private val makeUpPrice: TextView =
            itemView.findViewById(R.id.makeUpPrice)
        private val makeUpRating: TextView =
            itemView.findViewById(R.id.makeUpRating)
        private val makeUpDescription: TextView =
            itemView.findViewById(R.id.makeUpDescription)
        fun bind(makeUps: ResponseData) {
            Glide
                .with(itemView.context)
                .load(makeUps.image_link)
                .into(photoImage)
            makeUpName.text = "${makeUps.name}"
            makeUpPrice.text = "${makeUps.price}${makeUps.currency}"
            makeUpRating.text = makeUps.rating.toString()
            makeUpDescription.text = makeUps.description

        }
    }
}