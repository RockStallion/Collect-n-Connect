package com.collect.connect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.collect.connect.api.sets.SetsActivity
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

        val NameUser = findViewById<TextView>(R.id.user)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: currentUser.email ?: "collector"
            NameUser.text = "Hello, $name!"
        } else {
            NameUser.text = "Hello, collector!"
        }

        val nombreSet = findViewById<EditText>(R.id.editNombreSet)
        val numeroPiezas = findViewById<EditText>(R.id.editNumeroPiezas)
        val anio = findViewById<EditText>(R.id.editAnio)
        val tema = findViewById<EditText>(R.id.editTema)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarSet)

        val PagScan: LinearLayout = findViewById(R.id.Scan)
        val PagSets: LinearLayout = findViewById(R.id.Sets)
        val PagPieces: LinearLayout = findViewById(R.id.Pieces)
        val PagYou: LinearLayout = findViewById(R.id.you)
        val PagPerfil: ImageView = findViewById(R.id.perfil)

        PagScan.setOnClickListener {
            val intent = Intent(this, Scan::class.java)
            startActivity(intent)
        }

        PagSets.setOnClickListener {
            val intent = Intent(this, SetsActivity::class.java)
            startActivity(intent)
        }

        PagPieces.setOnClickListener {
            val intent = Intent(this, Collections::class.java)
            startActivity(intent)
        }

        PagYou.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }

        PagPerfil.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }
        val salir: ImageView = findViewById(R.id.salir)

        salir.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val notificacion = findViewById<ImageView>(R.id.imgNotificacion)
        notificacion.setOnClickListener {
            Toast.makeText(this, "No hay notificaciones de momento", Toast.LENGTH_SHORT).show()
        }
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