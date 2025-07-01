package com.example.sia.main

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sia.R
import com.example.sia.adapters.AssetAdapter
import com.example.sia.models.AssetResponse
import com.example.sia.utils.ApiService
import com.google.android.material.tabs.TabLayout

class ViewAssets : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_assets)

        tabLayout = findViewById(R.id.topTabs)
        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.assetRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Customize SearchView appearance
        customizeSearchView()

        // Setup tabs
        setupTabs()

        // Load assets
        ApiService.viewAssets(this) { assets: List<AssetResponse> ->
            if (assets.isEmpty()) {
                Toast.makeText(this, "No assets found", Toast.LENGTH_SHORT).show()
            } else {
                recyclerView.adapter = AssetAdapter(assets) { clickedAsset ->
                    Toast.makeText(this, "Clicked asset ID: ${clickedAsset.asset_id}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("All assets"))
        tabLayout.addTab(tabLayout.newTab().setText("Deploy"))
        tabLayout.addTab(tabLayout.newTab().setText("My Assets"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabTitle = tab.text
                // You can filter your assets list here if needed
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun customizeSearchView() {
        val searchEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        // Set text and hint color
        searchEditText.setTextColor(Color.WHITE)
        searchEditText.setHintTextColor(Color.LTGRAY)

        // Change icon colors
        val iconIds = listOf(
            androidx.appcompat.R.id.search_mag_icon,
            androidx.appcompat.R.id.search_close_btn,
            androidx.appcompat.R.id.search_voice_btn
        )
        for (id in iconIds) {
            val icon = searchView.findViewById<ImageView>(id)
            icon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        }
    }
}
