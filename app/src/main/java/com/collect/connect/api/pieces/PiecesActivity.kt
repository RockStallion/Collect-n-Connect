package com.collect.connect.api.pieces



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collect.connect.api.PartItem
import com.collect.connect.api.repository.LegoRepository
import com.example.collect_n_connect.R
import kotlinx.coroutines.launch

class PiecesActivity : AppCompatActivity() {

    private lateinit var adapter: PiecesAdapter
    private val repository = LegoRepository()

    // Declara vistas:
    private lateinit var recyclerViewPieces: RecyclerView
    private lateinit var editTextSetNumber: EditText
    private lateinit var buttonFetchParts: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pieces)

        // Asigna vistas con findViewById:
        recyclerViewPieces = findViewById(R.id.recyclerViewPieces)
        editTextSetNumber = findViewById(R.id.editTextSetNumber)
        buttonFetchParts = findViewById(R.id.buttonFetchParts)

        adapter = PiecesAdapter(listOf())
        recyclerViewPieces.layoutManager = LinearLayoutManager(this)
        recyclerViewPieces.adapter = adapter

        val initialSetNumber = intent.getStringExtra("EXTRA_SET_NUMBER")
        initialSetNumber?.let {
            editTextSetNumber.setText(it)
            // fetchParts(it)
        }

        buttonFetchParts.setOnClickListener {
            val setNumber = editTextSetNumber.text.toString().trim()
            if (setNumber.isEmpty()) {
                Toast.makeText(this, "Ingresa número de set válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            fetchParts(setNumber)
        }
    }

    private fun fetchParts(setNumber: String) {
        lifecycleScope.launch {
            try {
                val response = repository.fetchPartsForSet(setNumber)
                if (response.isSuccessful) {
                    val partResponse = response.body()
                    if (partResponse?.status == "success") {
                        val partsList: List<PartItem> = partResponse.parts
                        adapter.updateData(partsList)
                    } else {
                        Toast.makeText(
                            this@PiecesActivity,
                            "Error: ${partResponse?.message ?: "Desconocido"}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@PiecesActivity,
                        "Error en la llamada: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@PiecesActivity,
                    "Excepción: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}