package com.example.sia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sia.R
import com.example.sia.models.AssetResponse

class AssetAdapter(
    private val assetList: List<AssetResponse>,
    private val onItemClick: (AssetResponse) -> Unit
) : RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {

    private val selectedPositions = mutableSetOf<Int>()

    inner class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val assetNumber: TextView = itemView.findViewById(R.id.assetNumber)
        private val serialNo: TextView = itemView.findViewById(R.id.serialNo)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val accessControlId: TextView = itemView.findViewById(R.id.accessControlId)
        private val tickIcon: ImageView = itemView.findViewById(R.id.tickIcon)

        fun bind(asset: AssetResponse, isSelected: Boolean) {
            assetNumber.text = "Asset #${asset.asset_id}"
            serialNo.text = "Serial: ${asset.serial_no}"
            description.text = "Description: ${asset.description}"
            accessControlId.text = "Access ID: ${asset.access_control_id}"
            tickIcon.visibility = if (isSelected) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                val pos = adapterPosition
                if (selectedPositions.contains(pos)) {
                    selectedPositions.remove(pos)
                } else {
                    selectedPositions.add(pos)
                }
                notifyItemChanged(pos)
                onItemClick(asset)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.asset_item, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val isSelected = selectedPositions.contains(position)
        holder.bind(assetList[position], isSelected)
    }

    override fun getItemCount(): Int = assetList.size
}
