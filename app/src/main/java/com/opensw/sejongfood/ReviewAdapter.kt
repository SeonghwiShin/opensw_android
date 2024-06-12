package com.opensw.sejongfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensw.sejongfood.databinding.ItemReviewHolderBinding

class ReviewAdapter(
    private val dataList: List<Review>
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemReviewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Review, position: Int) {
            with(binding) {
                ratingbar.rating = data.rating
                textIndex.text = "${position + 1}번 유저"
                textRatring.text = data.rating.toString()
                textContent.text = data.contents
                textRecommend.text = data.recommendMenu
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemReviewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(dataList[position], position)
    }

    override fun getItemCount() = dataList.size
}
