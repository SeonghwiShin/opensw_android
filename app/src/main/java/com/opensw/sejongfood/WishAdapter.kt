package com.opensw.sejongfood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensw.sejongfood.databinding.ItemHolderBinding

class WishAdapter(
    private val dataList: MutableList<PlaceData>
) : RecyclerView.Adapter<WishAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PlaceData) {
            with(binding) {
                textViewTitle.text = data.title
                textViewBasicInfo.text = "fadafd"
                if (data.review.isEmpty()) {
                    binding.ratingbar.rating = 0f
                    binding.textReviewCount.text = "0"
                } else {
                    val averageRating = data.review.map { it.rating }?.average() ?: 0.0
                    binding.ratingbar.rating = averageRating.toFloat()
                    binding.textRatring.text = String.format("%.1f", averageRating)
                    binding.textReviewCount.text = "(${data.review?.size.toString()})"
                }
                val recommendMenus = data.review.map { it.recommendMenu }.distinct().joinToString(", ")
                binding.textRecommend.text = recommendMenus
                binding.textViewBasicInfo.text = data.address
                binding.textDelete.setOnClickListener {
                    SharedPreferenceUtil(it.context).removePlaceData(data.index)
                    dataList.remove(data)
                    notifyDataSetChanged()
                }
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

    fun refreshData(data: MutableList<PlaceData>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size
}
