package com.collect.connect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.collect_n_connect.R
import com.google.firebase.auth.FirebaseAuth
import androidx.viewpager2.widget.ViewPager2
import com.collect.connect.FireBase.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.appcompat.app.AppCompatActivity
import com.collect.connect.api.sets.SetsActivity
import com.collect.connect.api.pieces.PiecesActivity

class Principal : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)
        val buscar:LinearLayout = findViewById(R.id.buscar)
        buscar.setOnClickListener {
            val intent = Intent(this, SetsActivity::class.java)
            startActivity(intent)
        }
        auth = FirebaseAuth.getInstance()

        val NameUser = findViewById<TextView>(R.id.user)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: currentUser.email ?: "collector"
            NameUser.text = "Hello, $name!"
        } else {
            NameUser.text = "Hello, collector!"
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

        val imagen1 = findViewById<ImageView>(R.id.img1)
        imagen1.setOnClickListener {
            val url = "https://www.lego.com/es-mx/product/miles-morales-mask-76329"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        val imagen2 = findViewById<ImageView>(R.id.img2)
        imagen1.setOnClickListener {
            val url = "https://www.lego.com/es-mx/product/iron-spider-man-bust-76326"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        val imagen3 = findViewById<ImageView>(R.id.miImagen)
        imagen1.setOnClickListener {
            val url = "https://www.lego.com/es-mx/product/x-men-the-x-mansion-76294"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
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