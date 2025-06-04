package com.collect.connect.api.sets


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collect.connect.api.SetItem
import com.collect.connect.api.repository.LegoRepository
import com.example.collect_n_connect.R
import kotlinx.coroutines.launch
import android.net.Uri
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.collect.connect.Collections
import com.collect.connect.Perfil
import com.collect.connect.Pieces
import com.collect.connect.Principal
import com.collect.connect.Scan
import com.collect.connect.Sets
import com.google.firebase.auth.FirebaseAuth

class SetsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: SetsAdapter
    private val repository = LegoRepository()

    private lateinit var recyclerViewSets: RecyclerView
    private lateinit var buttonFetchSets: Button
    private lateinit var editTextYear: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Asegúrate de usar el nombre de tu layout correcto:
        setContentView(R.layout.activity_set)
        val PagScan: LinearLayout = findViewById(R.id.Scan)
        val PagSets: LinearLayout = findViewById(R.id.Sets)
        val PagPieces: LinearLayout = findViewById(R.id.Pieces)
        val PagYou: LinearLayout = findViewById(R.id.you)
        val PagPerfil: ImageView = findViewById(R.id.perfil)
        recyclerViewSets = findViewById(R.id.recyclerViewSets)
        buttonFetchSets = findViewById(R.id.buttonFetchSets)
        editTextYear = findViewById(R.id.editTextYear)


        auth = FirebaseAuth.getInstance()
        val NameUser = findViewById<TextView>(R.id.user)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: currentUser.email ?: "collector"
            NameUser.text = "Hello, $name!"
        } else {
            NameUser.text = "Hello, collector!"
        }


        // Aquí cambiamos la lambda para que abra la web del set en lugar de PiecesActivity:
        adapter = SetsAdapter(listOf()) { selectedSet ->
            // Construimos la URL de Brickset para ese set:
            val url = "https://brickset.com/sets/${selectedSet.number}"
            // Creamos un Intent para abrir el navegador:
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        recyclerViewSets.layoutManager = LinearLayoutManager(this)
        recyclerViewSets.adapter = adapter

        buttonFetchSets.setOnClickListener {
            val yearText = editTextYear.text.toString().trim()
            if (yearText.isEmpty()) {
                Toast.makeText(this, "Ingresa un año válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val year = yearText.toIntOrNull()
            if (year == null) {
                Toast.makeText(this, "El año debe ser numérico", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            fetchSets(year)
        }


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
    }

    private fun fetchSets(year: Int) {
        lifecycleScope.launch {
            try {
                val response = repository.fetchSetsByYear(year)
                if (response.isSuccessful) {
                    val setResponse = response.body()
                    if (setResponse?.status == "success") {
                        val setsList: List<SetItem> = setResponse.sets
                        adapter.updateData(setsList)
                    } else {
                        Toast.makeText(
                            this@SetsActivity,
                            "Error: ${setResponse?.message ?: "Desconocido"}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SetsActivity,
                        "Error en la llamada: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@SetsActivity,
                    "Excepción: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}