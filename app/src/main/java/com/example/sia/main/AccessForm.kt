package com.example.sia.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sia.R

class AccessForm : AppCompatActivity() {
    private lateinit var issueButton: Button
    private lateinit var monitorButton: Button
    private lateinit var returnButton: Button
    private lateinit var myassetsButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.access_form)

        issueButton = findViewById(R.id.issueAsset)
        monitorButton = findViewById(R.id.monitorAsset)
        myassetsButton = findViewById(R.id.myAsset)
        returnButton = findViewById(R.id.returnAsset)

        issueButton.setOnClickListener {
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

        myassetsButton.setOnClickListener {
            val intent = Intent(this, ViewAssets::class.java)
            startActivity(intent)
        }

        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // TODO: Add your logic
        }
    }
}