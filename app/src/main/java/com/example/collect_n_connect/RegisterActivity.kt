package com.example.collect_n_connect

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val backButton = findViewById<Button>(R.id.btnBackToLogin)
        backButton.setOnClickListener {
            finish() // Cierra RegisterActivity y regresa al Login (MainActivity)
        }
    }
}