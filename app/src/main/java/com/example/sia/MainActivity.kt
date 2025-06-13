package com.example.sia

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var id: EditText
    private lateinit var firstName: EditText
    private lateinit var firstSurname: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val fragments = listOf(FirstFragment(), SecondFragment(), CameraFragment())

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int) = fragments[position]

//        setContentView(R.layout.custodian_form) // Links this activity to activity_main.xml
//        id = findViewById(R.id.editTextID)
//        firstName = findViewById(R.id.editTextName)
//        firstSurname = findViewById(R.id.editTextSurname)
//
//        Log.d(id.toString(), "onCreate: ID is ")
//        Log.d(firstName.toString() , "onCreate: first name is ")



        }
    }
}
