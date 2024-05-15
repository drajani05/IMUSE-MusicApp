package com.example.myimuseapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myimuseapplication.databinding.FragmentNowPlayingBinding

class NowPlaying :Fragment(){
        companion object{
            @SuppressLint("StaticFieldLeak")
            lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding=FragmentNowPlayingBinding.bind(view)
        binding.root.visibility=View.INVISIBLE

        return view
    }

   // override fun onResume() {
     //   super.onResume()
       // if (PlayerActivity.musicService !== null) {
         //   binding.root.visibility=View.VISIBLE
        //}
        
        
    }


