package com.opensw.sejongfood

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.opensw.sejongfood.databinding.DialogAddReviewBinding
import com.opensw.sejongfood.databinding.FragmentDetailBottomSheetBinding

class DetailBottomSheet(private var touchEventListener: TouchEventListener?) : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var placeData: PlaceData? = null
    private var index = 0
    private var adapter: ReviewAdapter? = null

    fun setPlaceData(data: PlaceData, i: Int) {
        placeData = data
        index = i
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
    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val touchSideView = dialog!!.window?.decorView?.findViewById<View>(com.google.android.material.R.id.touch_outside)
            touchSideView?.setOnTouchListener { v, event ->
                touchEventListener?.onTouchEvent(event)
                false
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        binding.headerLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val headerHeight = binding.headerLayout.height
            bottomSheetBehavior.peekHeight = headerHeight + dpToPx(requireActivity(), 20.0f).toInt()
        }

        binding.nestedScrollView.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            v.onTouchEvent(event)
            false
        }

        if (placeData?.review?.isNotEmpty() == true ) {
            binding.textReviewCount.text = "(${placeData?.review?.size.toString()})"
            val averageRating = placeData?.review?.map { it.rating }?.average() ?: 0.0
            binding.ratingbar.rating = averageRating.toFloat()
            binding.textRatring.text = String.format("%.1f", averageRating)
        } else {
            binding.textReviewCount.text = "(0)"
            binding.textRatring.text = "0"
        }

        binding.textViewBasicInfo.text = placeData?.address
        binding.textViewTitle.text = placeData?.title

        val recommendMenus = placeData?.review?.map { it.recommendMenu }?.distinct()?.joinToString(", ")
        binding.textRecommend.text = recommendMenus

        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        adapter = ReviewAdapter(placeData!!.review.toMutableList())
        binding.recyclerView.adapter = adapter

        updateReviewVisibility()

        binding.btnAddReview.setOnClickListener {
            showAddReviewDialog()
        }
        binding.btnAddRevieweEmpty.setOnClickListener {
            showAddReviewDialog()
        }
        binding.checkboxWish.isChecked = SharedPreferenceUtil(requireActivity()).isPlaceDataIndexExists(placeData!!.index)
        binding.checkboxWish.setOnClickListener {
            if (binding.checkboxWish.isChecked) {
                SharedPreferenceUtil(requireActivity()).addPlaceData(placeData!!)
            } else {
                SharedPreferenceUtil(requireActivity()).removePlaceData(placeData!!.index)
            }
        }
    }

    private fun updateReviewVisibility() {
        if (placeData?.review.isNullOrEmpty()) {
            binding.recyclerView.isGone = true
            binding.btnAddReview.isGone = true
            binding.btnAddRevieweEmpty.isVisible = true
            binding.textEmpty.isVisible = true
        } else {
            binding.recyclerView.isVisible = true
            binding.btnAddReview.isVisible = true
            binding.btnAddRevieweEmpty.isGone = true
            binding.textEmpty.isGone = true
        }
    }

    private fun showAddReviewDialog() {
        val dialogBinding = DialogAddReviewBinding.inflate(LayoutInflater.from(requireActivity()))
        val dialog = AlertDialog.Builder(requireActivity())
            .setTitle("리뷰 작성하기")
            .setView(dialogBinding.root)
            .create()

        dialogBinding.saveButton.setOnClickListener {
            val rating = dialogBinding.ratingbar.rating
            val contents = dialogBinding.reviewContents.text.toString()
            val recommendMenu = dialogBinding.reviewRecommendMenu.text.toString()

            val review = Review(rating, contents, recommendMenu)

            FirebaseHelper(requireActivity()).addReviewToPlace(index, review) {
                Toast.makeText(requireActivity(), "저장되었습니다", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                placeData?.review?.add(review)
                adapter?.notifyDataSetChanged()
                updateReviewVisibility()
                FirebaseHelper(requireActivity()).getPlaceDataByIndex(index) { placeData ->
                    if (placeData != null) {
                        if (placeData?.review?.isNotEmpty() == true ) {
                            binding.textReviewCount.text = "(${placeData?.review?.size.toString()})"
                            val averageRating = placeData?.review?.map { it.rating }?.average() ?: 0.0
                            binding.ratingbar.rating = averageRating.toFloat()
                            binding.textRatring.text = String.format("%.1f", averageRating)
                        } else {
                            binding.textReviewCount.text = "(0)"
                            binding.textRatring.text = "0"
                        }

                        binding.textViewBasicInfo.text = placeData?.address
                        binding.textViewTitle.text = placeData?.title

                        val recommendMenus = placeData?.review?.map { it.recommendMenu }?.distinct()?.joinToString(", ")
                        binding.textRecommend.text = recommendMenus
                    }
                }
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}

interface TouchEventListener {
    fun onTouchEvent(event: MotionEvent)
}
