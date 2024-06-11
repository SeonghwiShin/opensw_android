package com.opensw.sejongfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.opensw.sejongfood.databinding.FragmentWishBinding

class WishFragment : Fragment() {
    private lateinit var binding: FragmentWishBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWishBinding.inflate(
            inflater,
            container,
            false,
        )
        initView()

        return binding.root
    }

    private fun initView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val dummyReviewList = mutableListOf(
            Review(
                    rating = 4.5f,
                    contents = "와플이 정말 맛있어요! 커피도 좋아요.",
                    recommendMenu = "플레인와플, 아메리카노",
                ),
            Review(
                rating = 4.0f,
                contents = "친절한 직원과 깔끔한 매장.",
                recommendMenu = "플레인와플, 아메리카노",
                ),
        )
        val dataList = listOf(
            PlaceData(
                index = 1,
                reviewCount = dummyReviewList.size,
                review = dummyReviewList,
                title = "와플대학 세종대학생회관캠퍼스",
                rating = 4.0f,
                latitude = 37.54972382209993,
                longitude = 127.07525938973366,
            )
        )

        val adapter = WishAdapter(dataList)
        recyclerView.adapter = adapter
    }
}
