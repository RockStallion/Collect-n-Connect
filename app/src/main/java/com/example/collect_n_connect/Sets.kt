package com.example.collect_n_connect

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity

class Sets : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sets)

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


    }
}