package com.gadidev.acar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gadidev.acar.databinding.ActivityMainBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseCrashlytics.getInstance()
    }
}