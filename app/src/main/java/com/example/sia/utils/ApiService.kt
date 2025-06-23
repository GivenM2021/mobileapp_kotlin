package com.example.sia.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sia.models.AssetResponse
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

class ApiService {
    companion object {

        val client = getUnsafeOkHttpClient()

        fun systemLogin(context: Context) {
            val targetUrl = "https://${SharedPrefs.ACCESS_CONTROL_IP_ADDRESS}/user_management/system_login"
            println("IP address $targetUrl")

            val jsonObject = JSONObject().apply {
                put("username_arg", "a")
                put("infihlo_arg", "a")
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
                        if (response.isSuccessful && body != null) {
                            try {
                                val json = JSONObject(body)
                                val token = json.getString("token")

                                SharedPrefs.setToken(context, token)  // ✅ Proper usage
                                val tokenValue = SharedPrefs.getToken(context)
                                Log.d("Token", "Token value: ${tokenValue}")

                                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Token parsing error", Toast.LENGTH_LONG).show()
                                Log.e("Token", "Error parsing token from response", e)
                            }

                        } else {
                            when (code) {
                                201 -> Toast.makeText(context, "Record created", Toast.LENGTH_LONG).show()
                                409 -> Toast.makeText(context, "Person exists", Toast.LENGTH_LONG).show()
                                else -> Toast.makeText(context, "Error $code: $body", Toast.LENGTH_LONG).show()
                            }
                        }

                        Log.d("sendDataToServer", "Response code: $code")
                        Log.d("sendDataToServer", "Response body: $body")
                    }

                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.e("sendDataToServer", "Exception occurred", e)
                    }
                }
            }
        }


        fun submitAssetCollectorData(context: Context) {

        val id = SharedPrefs.getNationalId(context)
        val name = SharedPrefs.getFirstName(context)
        val surname = SharedPrefs.getSurname(context)
        val job = SharedPrefs.getOccupation(context)


        val targetUrl = "https://"+SharedPrefs.ACCESS_CONTROL_IP_ADDRESS+"/db/person"
        println("IP address "+ targetUrl)

        val jsonObject = JSONObject().apply {
            put("first_name", name)
            put("last_name", surname)
            put("occupation", job)
            put("national_id", id)
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(targetUrl)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + SharedPrefs.getToken(context))
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.newCall(request).execute()
                val body = response.body?.string()
                val code = response.code

                launch(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Success: $body", Toast.LENGTH_LONG).show()
                    } else {
                        if (code == 201) {
                            Toast.makeText(context, "Record created", Toast.LENGTH_LONG).show()
                        }
                        if (code == 409) {
                            Toast.makeText(context, "Person exists", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Error $code: $body", Toast.LENGTH_LONG).show()
                        }
                    }

                    Log.d("sendDataToServer", "Response code: $code")
                    Log.d("sendDataToServer", "Response body: $body")
                }

            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("sendDataToServer", "Exception occurred", e)
                }
            }
        }
    }

        fun viewAssets(context: Context, callback: (List<AssetResponse>) -> Unit) {

            val targetUrl = "https://"+SharedPrefs.ACCESS_CONTROL_IP_ADDRESS+"/db/view_assets"
            val request = Request.Builder()
                .url(targetUrl)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + SharedPrefs.getToken(context))
                .build()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = client.newCall(request).execute()
                    val body = response.body?.string()

                    if (response.isSuccessful && body != null) {
                        val type = object : TypeToken<List<AssetResponse>>() {}.type
                        val assetList: List<AssetResponse> = Gson().fromJson(body, type)

                        withContext(Dispatchers.Main) {
                            callback(assetList)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error: ${response.code}", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
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
}}