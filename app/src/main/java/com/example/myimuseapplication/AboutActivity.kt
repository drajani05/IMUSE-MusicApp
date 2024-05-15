package com.example.myimuseapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myimuseapplication.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    val binding = ActivityAboutBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.title = "About"
    binding.aboutText.text = "Developed By: \n \n Rajani Dhulipala , Raj Jha and\n Yash Chavan\n\n Imuse Music app is an online Music player application made for project basis.\n\n None of the songs that are been used in the project are owned by the developers. It is simply used as an example point of view.\n\nIf you want to provide feedback, Kindly contact us , We would love to hear your suggestions and feedback."

}
}



