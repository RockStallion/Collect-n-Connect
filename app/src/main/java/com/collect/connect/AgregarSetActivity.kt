package com.collect.connect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.collect_n_connect.R

class AgregarSetActivity : ComponentActivity() {
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
                // Aquí podrías guardar el set en Firebase, en una base de datos local o en un arreglo
                Toast.makeText(this, "Set agregado correctamente!", Toast.LENGTH_SHORT).show()
                finish() // Cierra la actividad
            }
        }
    }
}