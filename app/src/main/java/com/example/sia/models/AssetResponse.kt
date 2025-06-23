package com.example.sia.models


data class AssetResponse(
    val asset_id: Int,
    val serial_no: String,
    val description: String,
    val access_control_id: Int
)