package com.example.sia.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sia.R
import com.example.sia.utils.ApiService
import com.example.sia.utils.SharedPrefs

class MainActivity2 : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        submitButton = findViewById(R.id.loginButton)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        submitButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            SharedPrefs.setUsername(this, username)
            SharedPrefs.setPassword(this, password)

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show loading
            loadingProgressBar.visibility = View.VISIBLE
            submitButton.isEnabled = false

            ApiService.systemLogin(this) { code, body, token ->
                loadingProgressBar.visibility = View.GONE
                submitButton.isEnabled = true

                if (code == 200 && token != null) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AccessForm::class.java)
                    startActivity(intent)
                } else if (code == -1) {
                    Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Login failed: code $code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
