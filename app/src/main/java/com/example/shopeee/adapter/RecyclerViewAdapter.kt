package com.example.shopeee.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopeee.R
import com.example.shopeee.repository.Item
import com.google.android.material.imageview.ShapeableImageView

class RecyclerViewAdapter(val itemList : ArrayList<Item>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
                parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        /*holder.itemImage.setImageResource(currentItem.itemImage)
        holder.itemHeading.text = currentItem.heading*/
        holder.bind(currentItem)
    }

    //inner class - outer.Inner() is possible + no need to create instance
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage : ShapeableImageView = itemView.findViewById(R.id.item_image)
        val itemHeading : TextView = itemView.findViewById(R.id.item_heading)
        val itemQty : TextView = itemView.findViewById(R.id.item_quantity)
        //view binding - remove findViewById

        fun bind(item: Item) {
            itemImage.setImageResource(item.itemImage)
            itemHeading.text = item.heading
            itemQty.text = item.quantity.toString()
        }
    }


}