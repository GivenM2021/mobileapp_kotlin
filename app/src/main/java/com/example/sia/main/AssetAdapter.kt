package com.example.sia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sia.R
import com.example.sia.models.AssetResponse

class AssetAdapter(private val assets: List<AssetResponse>) :
    RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {

    class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView = itemView.findViewById(R.id.assetNumber)
        val serialNo: TextView = itemView.findViewById(R.id.serialNo)
        val description: TextView = itemView.findViewById(R.id.description)
        val accessId: TextView = itemView.findViewById(R.id.accessControlId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.asset_item, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val asset = assets[position]
        holder.number.text = "Asset #${position + 1}"
        holder.serialNo.text = "Serial: ${asset.serial_no}"
        holder.description.text = "Description: ${asset.description}"
        holder.accessId.text = "Access ID: ${asset.access_control_id}"
    }

    override fun getItemCount(): Int = assets.size
}
