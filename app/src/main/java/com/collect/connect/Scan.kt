package com.collect.connect

import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.collect.connect.api.sets.SetsActivity
import com.example.collect_n_connect.R
import com.google.firebase.auth.FirebaseAuth

class Scan : ComponentActivity() {

    private var camera: Camera? = null
    private lateinit var Cam: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan)
        val buscar:LinearLayout = findViewById(R.id.buscar)
        buscar.setOnClickListener {
            val intent = Intent(this, SetsActivity::class.java)
            startActivity(intent)
        }
        // Verificar permisos de cámara en tiempo de ejecución
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1)
        }

        Cam = findViewById(R.id.camara)
        surfaceHolder = Cam.holder

        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                // Intenta abrir la cámara
                try {
                    camera = Camera.open()  // Intenta abrir la cámara
                    val params = camera?.parameters
                    camera?.setDisplayOrientation(90)  // Orienta la cámara si es necesario
                    camera?.setPreviewDisplay(holder)
                    camera?.startPreview()
                } catch (e: Exception) {
                    e.printStackTrace()
                    // En caso de error, mostrar un mensaje
                    showError("Error al abrir la cámara.")
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                // Se puede ajustar la cámara si la superficie cambia
                camera?.stopPreview()
                camera?.setPreviewDisplay(holder)
                camera?.startPreview()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                // Libera la cámara cuando la superficie se destruye
                camera?.stopPreview()
                camera?.release()
            }
        })

        auth = FirebaseAuth.getInstance()
        val NameUser = findViewById<TextView>(R.id.user)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: currentUser.email ?: "collector"
            NameUser.text = "Hello, $name!"
        } else {
            NameUser.text = "Hello, collector!"
        }
        // Configuración de los botones de navegación
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
    }

    // Método para mostrar errores
    private fun showError(message: String) {
        // Implementa un método de error o visualiza un Toast
    }

    override fun onPause() {
        super.onPause()
        // Liberar la cámara cuando la actividad entra en pausa
        camera?.release()
    }

    override fun onResume() {
        super.onResume()
        // Verificar permisos antes de intentar abrir la cámara
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            try {
                camera = Camera.open()  // Vuelve a abrir la cámara si es necesario
            } catch (e: Exception) {
                e.printStackTrace()
                showError("Error al reabrir la cámara.")
            }
        }
    }

    // Manejo de permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                // Permiso denegado, manejar el caso
                showError("El permiso para acceder a la cámara es necesario.")
            }
        }
    }
}
