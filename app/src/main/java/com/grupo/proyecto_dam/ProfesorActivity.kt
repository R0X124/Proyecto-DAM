package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.grupo.proyecto_dam.databinding.ProfesoractivityBinding
import com.grupo.proyecto_dam.profesor.CursoProfesorActivity
import com.grupo.proyecto_dam.profesor.NotasProfesorActivity

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
        binding.NotasButton.setOnClickListener {
            val intent = Intent(this, NotasProfesorActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}