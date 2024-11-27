package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import com.grupo.proyecto_dam.databinding.AdminactivityBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: AdminactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = AdminactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar
        //setSupportActionBar(binding.toolbar)

        /*// Configurar el menú de hamburguesa
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()*/

        // Configuración de clics para botones de la interfaz principal
        binding.usersButton.setOnClickListener {
            val intent = Intent(this, UsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.coursesButton.setOnClickListener {
            val intent = Intent(this, CursoAdminActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.gradesButton.setOnClickListener {
            Toast.makeText(this, "Grados Académicos", Toast.LENGTH_SHORT).show()
        }

        binding.exitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configuración de clics para las opciones del menú lateral
        /*binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_users -> {
                    Toast.makeText(this, "Usuarios", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_courses -> {
                    Toast.makeText(this, "Cursos", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_grades -> {
                    Toast.makeText(this, "Grados Académicos", Toast.LENGTH_SHORT).show()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }*/
    }
}