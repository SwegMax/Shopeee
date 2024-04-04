package com.example.shopeee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopeee.data.Items
import com.google.android.material.imageview.ShapeableImageView

class RecyclerViewAdapter(val itemList : ArrayList<Items>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
                parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemImage.setImageResource(currentItem.itemImage)
        holder.itemHeading.text = currentItem.heading
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage : ShapeableImageView = itemView.findViewById(R.id.item_image)
        val itemHeading : TextView = itemView.findViewById(R.id.item_heading)
        //view binding - remove findViewById

    }


}