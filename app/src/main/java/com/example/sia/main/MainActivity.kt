package com.example.sia.main

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.sia.R
import com.example.sia.utils.ApiService

class MainActivity : AppCompatActivity() {
    private lateinit var id: EditText
    private lateinit var firstName: EditText
    private lateinit var firstSurname: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val fragments = listOf(AssetCollector(), AssetActivity(), CameraFragment())

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int) = fragments[position]

        }
    }
}
