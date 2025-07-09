package com.example.shopeee.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopeee.databinding.ProductRvItemBinding
import com.example.shopeee.helper.ShimmerImgHelper
import com.example.shopeee.helper.getProductPrice
import com.example.shopeee.repository.Product

class BestProductsAdapter: RecyclerView.Adapter<BestProductsAdapter.BestProductsViewHolder>() {

    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding): RecyclerView.ViewHolder(binding.root) {

        private val shimmerImgHelper: ShimmerImgHelper = ShimmerImgHelper(
            binding.shimmerImgProduct,
            binding.imgProduct)

        fun bind(product: Product) {
            binding.apply {
                shimmerImgHelper.loadImageWithShimmer(product.images.getOrNull(0))
                val priceAfterOffer = product.offerPercentage.getProductPrice(product.price)
                tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                if (product.offerPercentage == null)
                    tvNewPrice.visibility = View.INVISIBLE
                tvPrice.text = "$ ${product.price}"
                tvName.text = product.name
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

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Product) -> Unit)? = null
}
