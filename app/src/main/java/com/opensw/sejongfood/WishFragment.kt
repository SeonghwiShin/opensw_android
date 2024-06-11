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
    private var adapter: WishAdapter? = null
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

    override fun onResume() {
        super.onResume()
        adapter?.refreshData(SharedPreferenceUtil(requireActivity()).getPlaceDataList().toMutableList())
    }
    private fun initView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        adapter = WishAdapter(SharedPreferenceUtil(requireActivity()).getPlaceDataList().toMutableList())
        recyclerView.adapter = adapter
    }
}
