package com.collect.connect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.collect_n_connect.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AgregarSetActivity : ComponentActivity() {

    // Instancias de Firestore y Auth
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregareetactivity)

        val nombreSet = findViewById<EditText>(R.id.editNombreSet)
        val numeroPiezas = findViewById<EditText>(R.id.editNumeroPiezas)
        val anio = findViewById<EditText>(R.id.editAnio)
        val tema = findViewById<EditText>(R.id.editTema)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarSet)

        btnGuardar.setOnClickListener {
            val nombre = nombreSet.text.toString().trim()
            val piezas = numeroPiezas.text.toString().trim()
            val año = anio.text.toString().trim()
            val temaTexto = tema.text.toString().trim()

            if (nombre.isEmpty() || piezas.isEmpty() || año.isEmpty() || temaTexto.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                val user = auth.currentUser
                if (user == null) {
                    Toast.makeText(this, "Debes iniciar sesión primero.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Construir un HashMap con los campos del set
                val nuevoSet = hashMapOf(
                    "nombre"    to nombre,
                    "piezas"    to piezas.toInt(),
                    "anio"      to año.toInt(),
                    "tema"      to temaTexto,
                    "timestamp" to System.currentTimeMillis() // para ordenarlos por fecha
                )

                // Cada usuario tendrá su propia subcolección "sets"
                db.collection("users")
                    .document(user.uid)
                    .collection("sets")
                    .add(nuevoSet)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Set agregado correctamente!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}