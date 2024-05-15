package com.example.myimuseapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myimuseapplication.databinding.ActivityPlayerBinding


class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_IMUSEApplication)
        val binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}




