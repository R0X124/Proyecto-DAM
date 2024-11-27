package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo.proyecto_dam.data.api.CursoServiceSend
import com.grupo.proyecto_dam.data.api.UserService
import com.grupo.proyecto_dam.data.model.CursoRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.AgregarcursoactivityBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarCursoActivity : AppCompatActivity() {

    private lateinit var binding: AgregarcursoactivityBinding
    val cursoService: CursoServiceSend = RetrofitClient.instance.create(CursoServiceSend::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AgregarcursoactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón para salir
        binding.buttonSalir.setOnClickListener {
            val intent = Intent(this, CursoAdminActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonGuardar.setOnClickListener{
            val nombreCurso = binding.editTextNombreCurso.text.toString().trim()
            val descripcionCurso = binding.editTextDescripcionCurso.text.toString().trim()

            if(nombreCurso.isNotEmpty() && descripcionCurso.isNotEmpty()){
                val curso = CursoRequest(null,nombreCurso,descripcionCurso, null, null, null, null)
                agregarCurso(curso)
            }
            else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun agregarCurso(curso: CursoRequest) {
        cursoService.agregarCursos(curso).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("AgregarCursoActivity", "Curso agregado exitosamente")
                    Toast.makeText(this@AgregarCursoActivity, "Curso agregado correctamente", Toast.LENGTH_SHORT).show()

                    // Redirigir a la pantalla anterior después de guardar
                    val intent = Intent(this@AgregarCursoActivity, CursoAdminActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("AgregarCursoActivity", "Error al agregar curso: Código HTTP ${response.code()}")
                    Log.e("AgregarCursoActivity", "Mensaje del cuerpo de respuesta: ${response.errorBody()?.string()}")
                    Toast.makeText(this@AgregarCursoActivity, "Error al agregar curso", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("AgregarCursoActivity", "Fallo la conexión: ${t.message}", t)
                Toast.makeText(this@AgregarCursoActivity, "Fallo la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}