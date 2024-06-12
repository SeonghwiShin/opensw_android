package com.opensw.sejongfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.opensw.sejongfood.databinding.FragmentMainBinding

class MainFragment : Fragment(), TouchEventListener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mapView: MapView

    private var oldLabel: Label? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.resume() // MapView 의 resume 호출
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.pause() // MapView 의 pause 호출
    }

    private fun initView() {
        KakaoMapSdk.init(requireActivity(), "a75c80b77b641c9665023314aea7c763")

        binding.mapView.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                }

                override fun onMapError(error: Exception) {
                }
            },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {

                    FirebaseHelper(requireActivity()).getAllPlaceData { placeList ->
                        for (place in placeList) {
                            val options = LabelOptions.from(
                                LatLng.from(place.latitude, place.longitude),
                            ).setStyles(R.drawable.icon_maker1)
                            options.labelId = place.index.toString()

                            val layer = kakaoMap.labelManager?.layer
                            layer?.addLabel(options)
                        }
                    }

                    kakaoMap.setOnLabelClickListener { _, _, label ->
                        if (oldLabel != null) {
                            val oldTmpLatLng = oldLabel?.position
                            val oldTmpId = oldLabel?.labelId
                            oldLabel?.remove()
                            val oldOptions = LabelOptions.from(
                                oldTmpLatLng,
                            ).setStyles(R.drawable.icon_maker1)
                            oldOptions.labelId = oldTmpId

                            val layer = kakaoMap.labelManager?.layer
                            layer?.addLabel(oldOptions)
                        }

                        val tmpLatLng = label.position
                        val tmpId = label.labelId
                        label.remove()

                        val options = LabelOptions.from(
                            tmpLatLng,
                        ).setStyles(R.drawable.icon_maker)
                        options.labelId = tmpId

                        val layer = kakaoMap.labelManager?.layer

                        layer?.addLabel(options)


                        kakaoMap.moveCamera(
                            CameraUpdateFactory.newCenterPosition(label.position),
                            CameraAnimation.from(200),
                        )
                        FirebaseHelper(requireActivity()).getPlaceDataByIndex(label.labelId.toInt()) { placeData ->
                            if (placeData != null) {
                                val detailBottomSheet = DetailBottomSheet(this@MainFragment)
                                detailBottomSheet.setPlaceData(placeData, label.labelId.toInt())
                                detailBottomSheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogBorder20WhiteTheme)
                                detailBottomSheet.show(requireActivity().supportFragmentManager, "DetailBottomSheet")
                            }
                        }
                        oldLabel = label
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
    }

    override fun onTouchEvent(event: MotionEvent) {
        binding.mapView.dispatchTouchEvent(event)
    }
}
