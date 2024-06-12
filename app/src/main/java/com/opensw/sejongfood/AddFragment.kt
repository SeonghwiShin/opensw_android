package com.opensw.sejongfood

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.opensw.sejongfood.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddBinding.inflate(
            inflater,
            container,
            false,
        )
        initView()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        KakaoMapSdk.init(
            requireActivity(),
            "a75c80b77b641c9665023314aea7c763",
        )

        binding.mapView.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                }

                override fun onMapError(error: Exception) {
                }
            },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    kakaoMap.cameraMinLevel = 17
                    kakaoMap.cameraMaxLevel = 17

                    kakaoMap.setOnMapClickListener { _, latLng, _, _ ->
                        kakaoMap.labelManager?.layer?.removeAll()
                        val options = LabelOptions.from(
                            LatLng.from(
                                latLng.latitude,
                                latLng.longitude,
                            )
                        ).setStyles(R.drawable.icon_maker)
                        mLatitude = latLng.latitude
                        mLongitude = latLng.longitude

                        val layer = kakaoMap.labelManager?.layer
                        layer?.addLabel(options)

                        kakaoMap.moveCamera(
                            CameraUpdateFactory.newCenterPosition(latLng),
                            CameraAnimation.from(500),
                        )
                    }
                }

                override fun getPosition(): LatLng {
                    // 지도 시작 시 위치 좌표를 설정
                    return LatLng.from(37.55101, 127.07431)
                }

                override fun getZoomLevel(): Int {
                    // 지도 시작 시 확대/축소 줌 레벨 설정
                    return 17
                }
            },
        )

        // MapView를 터치하는 동안 ScrollView의 스크롤을 비활성화
        binding.viewMapTransparent.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(false)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> true
            }
        }
        FirebaseApp.initializeApp(requireActivity())

        binding.btnSave.setOnClickListener {
            if (mLatitude != 0.0 || mLongitude != 0.0 || binding.editTitle.text!!.isNotEmpty() || binding.editTitle.text!!.isNotEmpty()) {
                val firebaseDatabaseHelper = FirebaseHelper(requireActivity())
                var newIndex = 0
                firebaseDatabaseHelper.getPlaceIndex {
                    newIndex = it + 1
                }
                val placeData = PlaceData(
                    index = newIndex,
                    review = mutableListOf<Review>(),
                    reviewCount = 0,
                    rating = 0f,
                    latitude = mLatitude,
                    longitude = mLongitude,
                    title = binding.editTitle.text.toString(),
                    address = binding.editAddress.text.toString()

                )
                firebaseDatabaseHelper.addPlaceData(placeData)
                Toast.makeText(requireActivity(), "저장되었습니다.", Toast.LENGTH_SHORT).show()
                binding.editTitle.setText("")
                binding.editAddress.setText("")
            } else {
                Toast.makeText(requireActivity(), "입력하지 않은 내용이 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
