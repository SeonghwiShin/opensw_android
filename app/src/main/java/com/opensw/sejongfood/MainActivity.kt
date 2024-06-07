package com.opensw.sejongfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.opensw.sejongfood.databinding.ActivityMainBinding
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MainAdapter(this)
        binding.viewpager.adapter = adapter

        // 탭레이아웃과 뷰페이저 연결
        val tabTitles = arrayOf("Main", "Wish", "Other")
        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}