package com.opensw.sejongfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensw.sejongfood.databinding.ItemHolderBinding

class WishAdapter(
    private val dataList: List<PlaceData>
) : RecyclerView.Adapter<WishAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PlaceData) {
            with(binding) {
                textViewTitle.text = data.title
                textViewBasicInfo.text = data.basicInfo
                textRatring.text = data.rating.toString()
                ratingbar.rating = data.rating
                textReviewCount.text = "(${data.reviewCount})"
                textRecommend.text = data.recommendMenu
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size
}
