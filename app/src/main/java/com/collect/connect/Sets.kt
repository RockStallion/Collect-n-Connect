package com.collect.connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collect.connect.Api.RetrofitClient
import com.collect.connect.Api.SetsAdapter
import com.collect.connect.Api.SetsResponse
import com.example.collect_n_connect.R
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Sets : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val apiKey = "key 99b136a60113e968eab67b0b2a18cc57"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SetsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sets)
        recyclerView = findViewById(R.id.recyclerViewSets)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SetsAdapter(emptyList())
        recyclerView.adapter = adapter
        val PagScan: LinearLayout = findViewById(R.id.Scan)
        val PagSets: LinearLayout = findViewById(R.id.Sets)
        val PagPieces: LinearLayout = findViewById(R.id.Pieces)
        val PagYou: LinearLayout = findViewById(R.id.you)
        val PagPerfil: ImageView = findViewById(R.id.perfil)

        auth = FirebaseAuth.getInstance()
        val NameUser = findViewById<TextView>(R.id.user)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: currentUser.email ?: "collector"
            NameUser.text = "Hello, $name!"
        } else {
            NameUser.text = "Hello, collector!"
        }


        PagScan.setOnClickListener {
            val intent = Intent(this, Scan::class.java)
            startActivity(intent)
        }

        PagSets.setOnClickListener {
            val intent = Intent(this, Sets::class.java)
            startActivity(intent)
        }

        PagPieces.setOnClickListener {
            val intent = Intent(this, Pieces::class.java)
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

        fetchLegoSets()
    }


    private fun fetchLegoSets() {

        RetrofitClient.instance.getSets().enqueue(object : Callback<SetsResponse> {
            override fun onResponse(call: Call<SetsResponse>, response: Response<SetsResponse>) {
                if (response.isSuccessful) {
                    val sets = response.body()?.results ?: emptyList()
                    adapter.updateData(sets)
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SetsResponse>, t: Throwable) {
                Log.e("API_FAILURE", "Fallo: ${t.message}")
            }
        })
    }
}