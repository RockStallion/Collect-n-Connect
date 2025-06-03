package com.collect.connect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.collect_n_connect.R
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.collect.connect.Api.SetAdapter
import com.collect.connect.Api.SetsResponse


class Sets : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sets)
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


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerSets)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitClient.api.getLegoSets().enqueue(object : Callback<SetsResponse> {
            override fun onResponse(call: Call<SetsResponse>, response: Response<SetsResponse>) {
                if (response.isSuccessful) {
                    val sets = response.body()?.results ?: emptyList()
                    recyclerView.adapter = SetAdapter(sets)
                } else {
                    Toast.makeText(this@Sets, "Error al cargar sets", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SetsResponse>, t: Throwable) {
                Toast.makeText(this@Sets, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


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


        val imagen1 = findViewById<ImageView>(R.id.img2)
        imagen1.setOnClickListener {
            val url = "https://www.lego.com/es-mx/product/iron-man-mk4-bust-76327"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        val notificacion = findViewById<ImageView>(R.id.imgNotificacion)
        notificacion.setOnClickListener {
            Toast.makeText(this, "No hay notificaciones de momento", Toast.LENGTH_SHORT).show()
        }


        }
    }



