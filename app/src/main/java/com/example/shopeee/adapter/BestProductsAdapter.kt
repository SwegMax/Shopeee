package com.example.shopeee.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopeee.R
import com.example.shopeee.databinding.ProductRvItemBinding
import com.example.shopeee.repository.Product

class BestProductsAdapter: RecyclerView.Adapter<BestProductsAdapter.BestProductsViewHolder>() {

    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) { //i think product was initialised in the previous
            binding.apply {
                if (product.images.isNotEmpty()) {
                    Glide.with(itemView).load(product.images[0]).into(imgProduct)
                    product.offerPercentage?.let{
                        val remainingPricePercentage = 1f - it
                        val priceAfterOffer = remainingPricePercentage * product.price
                        tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                        tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    if (product.offerPercentage == null)
                        tvNewPrice.visibility = View.INVISIBLE
                    tvPrice.text = "$ ${product.price}"
                    tvName.text = product.name
                } else {
                    imgProduct.setImageResource(R.drawable.alpha_s_box)
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsViewHolder {
        return BestProductsViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
