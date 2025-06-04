package com.collect.connect


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.collect.connect.FireBase.ViewPagerAdapter
import com.collect.connect.api.sets.SetsActivity
import com.example.collect_n_connect.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class Collections : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collections)

        auth = FirebaseAuth.getInstance()
        val buscar:LinearLayout = findViewById(R.id.buscar)
        buscar.setOnClickListener {
            val intent = Intent(this, SetsActivity::class.java)
            startActivity(intent)
        }
        val nameUser = findViewById<TextView>(R.id.user)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: currentUser.email ?: "collector"
            nameUser.text = "Hello, $name!"
        } else {
            nameUser.text = "Hello, collector!"
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPagerSets)

        // Ahora "this" es AppCompatActivity (subclase de FragmentActivity)
        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Colección"
                1 -> tab.text = "Mi Perfil"
            }
        }.attach()

        val PagScan: LinearLayout = findViewById(R.id.Scan)
        val PagSets: LinearLayout = findViewById(R.id.Sets)
        val PagPieces: LinearLayout = findViewById(R.id.Pieces)
        val PagYou: LinearLayout = findViewById(R.id.you)
        val PagPerfil: ImageView = findViewById(R.id.perfil)

        PagScan.setOnClickListener {
            startActivity(Intent(this, Scan::class.java))
        }
        PagSets.setOnClickListener {
            startActivity(Intent(this, SetsActivity::class.java))
        }
        PagPieces.setOnClickListener {
            startActivity(Intent(this, Collections::class.java))
        }
        PagYou.setOnClickListener {
            startActivity(Intent(this, Principal::class.java))
        }
        PagPerfil.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }

        val notificacion = findViewById<ImageView>(R.id.imgNotificacion)
        notificacion.setOnClickListener {
            Toast.makeText(this, "No hay notificaciones de momento", Toast.LENGTH_SHORT).show()
        }

        val salir: ImageView = findViewById(R.id.salir)

        salir.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}