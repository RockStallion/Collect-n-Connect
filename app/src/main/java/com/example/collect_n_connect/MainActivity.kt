package com.example.collect_n_connect

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var signupText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        signupText = findViewById(R.id.signupText)

        loginButton.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()

            if (isValidLogin(enteredUsername, enteredPassword)) {
                startActivity(Intent(this, Principal::class.java))
            } else {
                showErrorDialog()
            }
        }

        signupText.setOnClickListener {
            // Ir a RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidLogin(username: String, password: String): Boolean {
        return username == "admin" && password == "1234"
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Credenciales incorrectas")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}