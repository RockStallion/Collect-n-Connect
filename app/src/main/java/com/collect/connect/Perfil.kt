package com.collect.connect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.collect.connect.FireBase.ViewPagerAdapter
import com.example.collect_n_connect.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class Perfil : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil)

        val PagScan: LinearLayout = findViewById(R.id.Scan)
        val PagSets: LinearLayout = findViewById(R.id.Sets)
        val PagPieces: LinearLayout = findViewById(R.id.Pieces)
        val PagYou: LinearLayout = findViewById(R.id.you)
        val PagPerfil: ImageView = findViewById(R.id.perfil)

        auth = FirebaseAuth.getInstance()

        val nameText = findViewById<TextView>(R.id.user)
        val emailText = findViewById<TextView>(R.id.emailText)
        val passwordText = findViewById<TextView>(R.id.passwordText) // si decides mostrarla

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: "collector"
            val email = currentUser.email ?: "unknown"

            nameText.text = "Hello, $name!"
            emailText.text = "Email: $email"

            // Si guardaste la contraseña manualmente en SharedPreferences:
            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val password = sharedPref.getString("password", "********")
            passwordText.text = "Password: $password"
        } else {
            nameText.text = "Hello, collector!"
            emailText.text = "Email: unknown"
            passwordText.text = "Password: ********"
        }
        val btnAgregarSet = findViewById<Button>(R.id.btnAgregarSet)
        btnAgregarSet.setOnClickListener {
            val intent = Intent(this, AgregarSetActivity::class.java)
            startActivity(intent)
        }

        val Coleccion = findViewById<Button>(R.id.Coleccion)
        Coleccion.setOnClickListener {
            val intent = Intent(this, Collections::class.java)
            startActivity(intent)
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
        val imagen1 = findViewById<ImageView>(R.id.img1)
        imagen1.setOnClickListener {
            val url = "https://www.lego.com/es-mx/product/japanese-red-maple-bonsai-tree-10348"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        val imagen2 = findViewById<ImageView>(R.id.img2)
        imagen2.setOnClickListener {
            val url = "https://www.lego.com/es-mx/product/wildflower-bouquet-10313"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
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

    }
}