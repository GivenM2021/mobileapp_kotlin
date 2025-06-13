package com.example.sia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sia.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class FirstFragment : Fragment() {

    private lateinit var id: EditText
    private lateinit var firstName: EditText
    private lateinit var firstSurname: EditText
    private lateinit var occupation: EditText
    private lateinit var submit: Button


    val client = getUnsafeOkHttpClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        id = view.findViewById(R.id.editTextID)
        firstName = view.findViewById(R.id.editTextName)
        firstSurname = view.findViewById(R.id.editTextSurname)
        occupation = view.findViewById(R.id.editTextOccupancy)
        submit = view.findViewById(R.id.buttonSubmit)

        submit.setOnClickListener {
            // Your code here
            sendDataToServer()
            Toast.makeText(requireContext(), "Submit clicked!", Toast.LENGTH_SHORT).show()
        }




        return view
    }

    private fun sendDataToServer() {
        // Extract string values
        val idNumber = id.text.toString()
        val fName = firstName.text.toString()
        val surname = firstSurname.text.toString()
        val job = occupation.text.toString()

        val targetUrl = "https://ip_address/db/person"

        val jsonObject = JSONObject().apply {
            put("first_name", fName)
            put("last_name", surname)
            put("occupation", job)
            put("national_id", idNumber)
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(targetUrl)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.newCall(request).execute()
                val body = response.body?.string()
                val code = response.code

                launch(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Success: $body", Toast.LENGTH_LONG).show()
                    } else {
                        if (code == 201) {
                            Toast.makeText(requireContext(), "Record created", Toast.LENGTH_LONG).show()
                        }
                        if (code == 401) {
                            Toast.makeText(requireContext(), "Person exists", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "Error $code: $body", Toast.LENGTH_LONG).show()
                        }
                    }

                    Log.d("sendDataToServer", "Response code: $code")
                    Log.d("sendDataToServer", "Response body: $body")
                }

            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("sendDataToServer", "Exception occurred", e)
                }
            }
        }
    }
    fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true } // disables hostname verification
                .build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
