package com.grupo.proyecto_dam

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo.proyecto_dam.databinding.EditarcursoactivityBinding

class EditarCursoActivity : AppCompatActivity() {

    private lateinit var binding: EditarcursoactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = EditarcursoactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}