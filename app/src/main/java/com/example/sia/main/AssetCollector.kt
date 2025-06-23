package com.example.sia.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.sia.R
import com.example.sia.utils.ApiService
import com.example.sia.utils.SharedPrefs

class AssetCollector : Fragment() {

    private lateinit var id: EditText
    private lateinit var firstName: EditText
    private lateinit var Surname: EditText
    private lateinit var occupation: EditText
    private lateinit var submit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.collector_activity, container, false)

        ApiService.systemLogin(requireContext())

        id = view.findViewById(R.id.editTextID)
        firstName = view.findViewById(R.id.editTextName)
        Surname = view.findViewById(R.id.editTextSurname)
        occupation = view.findViewById(R.id.editTextOccupancy)
        submit = view.findViewById(R.id.buttonSubmit)

        submit.setOnClickListener {
            // Your code here
            val nationalID = id.text.toString()
            val name = firstName.text.toString()
            val lastName = occupation.text.toString()
            val job = occupation.text.toString()

            SharedPrefs.setNationalId(requireContext(), nationalID)
            SharedPrefs.setFirstName(requireContext(), name)
            SharedPrefs.setSurname(requireContext(), lastName)
            SharedPrefs.setOccupation(requireContext(), job)
            ApiService.submitAssetCollectorData(requireContext())
//            Toast.makeText(requireContext(), "Submit clicked!", Toast.LENGTH_SHORT).show()
        }
        return view
    }

}
