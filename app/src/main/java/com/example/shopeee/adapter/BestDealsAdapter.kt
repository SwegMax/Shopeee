package com.example.shopeee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopeee.R
import com.example.shopeee.databinding.BestDealsRvItemBinding
import com.example.shopeee.repository.Product

class BestDealsAdapter: RecyclerView.Adapter<BestDealsAdapter.BestDealsViewHolder>() {
    inner class BestDealsViewHolder(private val binding: BestDealsRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) { //i think product was initialised in the previous
            binding.apply {
                if (product.images.isNotEmpty()) {
                    Glide.with(itemView).load(product.images[0]).into(imgBestDeal)
                    product.offerPercentage?.let{
                        val remainingPricePercentage = 1f - it
                        val priceAfterOffer = remainingPricePercentage * product.price
                        tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                    }
                    tvOldPrice.text = "$ ${product.price}"
                    tvDealProductName.text = product.name
                } else {
                    imgBestDeal.setImageResource(R.drawable.blue_background)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewHolder {
        return BestDealsViewHolder(
            BestDealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestDealsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
