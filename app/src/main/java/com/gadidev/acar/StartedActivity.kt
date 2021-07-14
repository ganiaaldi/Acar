package com.gadidev.acar

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gadidev.acar.adapter.GifAdapter
import com.gadidev.acar.databinding.ActivityMainBinding
import com.gadidev.acar.databinding.ActivityStartedBinding
import com.gadidev.acar.model.Gif
import com.opensooq.pluto.PlutoView

class StartedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStartedBinding

    private fun getGifs(): MutableList<Gif> {
        val items = mutableListOf<Gif>()
        items.add(Gif(R.drawable.satu))
        items.add(Gif(R.drawable.dua))
        items.add(Gif(R.drawable.tiga))
        return items
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        val adapter = GifAdapter(getGifs())
        binding.sliderView.create(adapter, 4000, lifecycle)
        binding.sliderView.setCustomIndicator(findViewById(R.id.state_indicator))
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}