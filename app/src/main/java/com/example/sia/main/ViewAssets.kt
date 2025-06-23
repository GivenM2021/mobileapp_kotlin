package com.example.sia.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sia.R
import com.example.sia.adapters.AssetAdapter
import com.example.sia.models.AssetResponse
import com.example.sia.utils.ApiService

class ViewAssets : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_assets)

        recyclerView = findViewById(R.id.assetRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Use ApiService
        ApiService.viewAssets(this) { assets: List<AssetResponse> ->
            if (assets.isEmpty()) {
                Toast.makeText(this, "No assets found", Toast.LENGTH_SHORT).show()
            } else {
                recyclerView.adapter = AssetAdapter(assets)
            }
        }
    }
}
