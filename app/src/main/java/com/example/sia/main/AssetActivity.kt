package com.example.sia.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.sia.R
import com.example.sia.models.Asset
import com.example.sia.utils.SharedPrefs

class AssetActivity : Fragment() {

    private lateinit var container1: LinearLayout
    private lateinit var buttonSave: Button
    private lateinit var buttonAddAnother: Button

    private lateinit var editTextAssetNumber: EditText
    private lateinit var editTextAssetDescription: EditText
    private lateinit var editTextModel: EditText
    private lateinit var editTextRequisition: EditText

    private var assetList: MutableList<Asset> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.asset_activity, container, false)

        container1 = view.findViewById(R.id.container1)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonAddAnother = view.findViewById(R.id.buttonAddAnother)

        editTextAssetNumber = view.findViewById(R.id.editTextAssetNumber1)
        editTextAssetDescription = view.findViewById(R.id.editTextAssetDescription1)
        editTextModel = view.findViewById(R.id.editTextModel)
        editTextRequisition = view.findViewById(R.id.requisitionEditText)

        SharedPrefs.clearAssetList(requireContext())

        // Load saved assets or empty list
        assetList = SharedPrefs.getAssetList(requireContext()).toMutableList()

        // Show assets already saved
        showAssetList()

        buttonSave.setOnClickListener {
            // Save the current list to SharedPreferences
            SharedPrefs.saveAssetList(requireContext(), assetList)
            Toast.makeText(requireContext(), "Assets saved!", Toast.LENGTH_SHORT).show()
        }

        buttonAddAnother.visibility = View.VISIBLE  // Show this button now

        buttonAddAnother.setOnClickListener {
            addAssetFromInput()
        }

        return view
    }

    private fun addAssetFromInput() {
        val assetNumber = editTextAssetNumber.text.toString().trim()
        val assetDescription = editTextAssetDescription.text.toString().trim()
        val assetModel = editTextModel.text.toString().trim()
        val assetRequisition = editTextRequisition.text.toString().trim()

        if (assetNumber.isEmpty() || assetDescription.isEmpty() || assetModel.isEmpty() || assetRequisition.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val newAsset = Asset(assetNumber, assetDescription, assetRequisition, assetModel)
        assetList.add(newAsset)

        clearInputFields()
        showAssetList()
    }

    private fun clearInputFields() {
        editTextAssetNumber.text.clear()
        editTextAssetDescription.text.clear()
        editTextModel.text.clear()
        editTextRequisition.text.clear()
    }

    private fun showAssetList() {
        container1.removeAllViews()

        // Add the buttons back because container1 includes the buttons in your layout
        container1.addView(buttonSave)
        container1.addView(buttonAddAnother)

        assetList.forEach { asset ->
            val textView = TextView(requireContext())
            textView.text = "Asset: ${asset.assetNumber} - ${asset.assetDescription} - ${asset.assetRequisition} - ${asset.assetModel}"
            textView.textSize = 16f
            textView.setPadding(8, 8, 8, 8)
            container1.addView(textView)
        }
    }
}
