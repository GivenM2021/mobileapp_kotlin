package com.example.sia.main

import ViewAsset
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.sia.R

class AccessForm : AppCompatActivity() {
    private lateinit var issueButton: Button
    private lateinit var monitorButton: Button
    private lateinit var returnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.access_form)

        issueButton = findViewById(R.id.issueAsset)
        monitorButton = findViewById(R.id.monitorAsset)
        returnButton = findViewById(R.id.returnAsset)

        issueButton.setOnClickListener {
            Toast.makeText(this, "Issue Asset Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // TODO: Add your logic
        }

        monitorButton.setOnClickListener {
//            Toast.makeText(this, "Monitor Asset Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ViewAssets::class.java)
            startActivity(intent)


            // TODO: Add your logic
        }

        returnButton.setOnClickListener {
            Toast.makeText(this, "Return Asset Clicked", Toast.LENGTH_SHORT).show()
            // TODO: Add your logic
        }
    }
}