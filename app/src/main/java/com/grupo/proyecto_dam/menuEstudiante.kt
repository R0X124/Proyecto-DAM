package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment

class menuEstudiante : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_estudiante)

        // Inicializar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Configuración de la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el icono de menú a la derecha
        val menuIconRight: View = findViewById(R.id.menu_icon_right)
        menuIconRight.setOnClickListener {
            // Abrir o cerrar el menú lateral al hacer clic en el ícono
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        if (savedInstanceState == null) {
            replaceFragment(principalEstu())
        }

        // Configurar el NavigationView
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_inicio -> replaceFragment(principalEstu())
                R.id.nav_cursos -> replaceFragment(cursos())
                R.id.nav_notas -> replaceFragment(notas())
                R.id.nav_horarios -> replaceFragment(horarios())
                R.id.nav_anuncios -> replaceFragment(anuncios())
                R.id.nav_eventos -> replaceFragment(eventos())
                R.id.nav_redes_sociales -> replaceFragment(redes())
                R.id.nav_pagos -> replaceFragment(pagos())
                R.id.nav_inscripcion -> replaceFragment(inscripcion())
                R.id.nav_salida -> showLogoutDialog()
            }
            drawerLayout.closeDrawer(GravityCompat.END) // Cierra el menú después de la selección
            true
        }
    }

    // Función para reemplazar fragmentos en el contenedor `content_frame`
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Métodos para abrir los diferentes fragmentos
    fun abrirFragmentCursos(view: View) {
        replaceFragment(cursos())
    }

    fun abrirFragmentAnuncios(view: View) {
        replaceFragment(anuncios())
    }

    fun abrirFragmentEventos(view: View) {
        replaceFragment(eventos())
    }

    fun abrirFragmentPagos(view: View) {
        replaceFragment(pagos())
    }

    fun abrirFragmentInscripciones(view: View) {
        replaceFragment(inscripcion())
    }

    // Mostrar diálogo de cierre de sesión
    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Cerrar sesión") { dialog, _ ->
                dialog.dismiss() // Cierra el diálogo
                logout() // Llama a la función de cierre de sesión
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss() // Cierra el diálogo sin hacer nada
            }
            .create()
            .show()
    }

    // Función de cierre de sesión
    private fun logout() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Cierra la actividad actual
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
