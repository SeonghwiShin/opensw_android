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

        val dataList = listOf(
            PlaceData(
                "와플대학 세종대학생회관캠퍼스",
                "서울 광진구 능동로 209 지하1층 (군자동)",
                4.0f,
                5,
                "플레인와플, 아메리카노"
            ),
        )

        val adapter = WishAdapter(dataList)
        recyclerView.adapter = adapter
    }
}
