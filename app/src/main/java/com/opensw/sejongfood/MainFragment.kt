package com.opensw.sejongfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.opensw.sejongfood.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false,
        )

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
}
