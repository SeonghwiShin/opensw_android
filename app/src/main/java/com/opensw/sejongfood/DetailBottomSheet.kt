package com.opensw.sejongfood

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.opensw.sejongfood.databinding.FragmentDetailBottomSheetBinding

class DetailBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var placeData: PlaceData? = null

    fun setPlaceData(data: PlaceData) {
        placeData = data
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        val peekHeightDp = 200
        val peekHeightPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            peekHeightDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        bottomSheetBehavior.peekHeight = peekHeightPx // 초기 높이 설정

        binding.nestedScrollView.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            v.onTouchEvent(event)
            true
        }

        binding.textViewTitle.text = placeData?.title
        binding.textReviewCount.text = placeData?.reviewCount.toString()
        binding.ratingbar.rating = placeData?.rating!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
