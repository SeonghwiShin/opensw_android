package com.opensw.sejongfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.opensw.sejongfood.databinding.ActivityMainBinding
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}