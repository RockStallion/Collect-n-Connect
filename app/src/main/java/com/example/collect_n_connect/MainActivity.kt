package com.example.collect_n_connect

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.collect_n_connect.ui.theme.CollectnconnectTheme

class MainActivity : ComponentActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()

            if (isValidLogin(enteredUsername, enteredPassword)) {
                // Navigate to the next activity
                //val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            } else {
                // Show error dialog
                showErrorDialog()
            }
        }
    }

    private fun isValidLogin(username: String, password: String): Boolean {
        // Replace with real validation
        return username == "admin" && password == "1234"
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Invalid login credentials")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id -> }
        builder.create().show()
    }
}