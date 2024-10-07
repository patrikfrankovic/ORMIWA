package com.patrik_frankovic.lv6_example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class PersonRecyclerAdapter(private val items: ArrayList<Persons>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PersonViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.person_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PersonViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PersonViewHolder(itemView:View):
        RecyclerView.ViewHolder(itemView){
            private val personImage = itemView.findViewById<ImageView>(R.id.personImage)
            private val personName = itemView.findViewById<TextView>(R.id.personName)
            private val personBirthday = itemView.findViewById<TextView>(R.id.personBirthday)
            private val personAddress = itemView.findViewById<TextView>(R.id.personAddress)
            fun bind(person: Persons){
                Glide.with(itemView.context).load(person.image).into(personImage)
                personName.text = "${person.firstname} ${person.lastname}"
                personBirthday.text = person.birthday
                personAddress.text = person.address.street
            }
        }
}
