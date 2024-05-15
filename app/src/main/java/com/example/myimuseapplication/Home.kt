package com.example.myimuseapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myimuseapplication.MainActivity.Companion.MusicListMA
import com.example.myimuseapplication.databinding.FragmentHomeBinding


class Home : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val binding= FragmentHomeBinding.bind(view)
       





        binding.musicRV.setHasFixedSize(true)
        binding.musicRV.setItemViewCacheSize(15)
        binding.musicRV.layoutManager= LinearLayoutManager(requireContext())
        binding.musicRV.adapter = MusicAdapter(requireContext(), MusicListMA)

        return view



    }


    }
