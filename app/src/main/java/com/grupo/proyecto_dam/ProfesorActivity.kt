package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo.proyecto_dam.databinding.ProfesoractivityBinding

class ProfesorActivity : AppCompatActivity() {
    private lateinit var binding: ProfesoractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ProfesoractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exitButtonProfesor.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cursosButton.setOnClickListener{
            val intent = Intent(this, CursoProfesorActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.horarioButton.setOnClickListener {
            val intent = Intent(this, RegistrarHorarioActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}