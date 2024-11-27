package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.grupo.proyecto_dam.data.adaptador.Adaptador
import com.grupo.proyecto_dam.data.adaptador.CursoAdaptador
import com.grupo.proyecto_dam.data.api.CursoService
import com.grupo.proyecto_dam.data.api.CursoServiceSend
import com.grupo.proyecto_dam.data.model.CursoRequest
import com.grupo.proyecto_dam.data.model.UserRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.CursoadminactivityBinding
import com.grupo.proyecto_dam.databinding.UsuarioactivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CursoAdminActivity : AppCompatActivity() {

    private lateinit var binding: CursoadminactivityBinding
    private var cursoService: CursoService = RetrofitClient.instance.create(CursoService::class.java)
    private lateinit var adaptador: CursoAdaptador
    private val listaCursos: MutableList<CursoRequest> = mutableListOf<CursoRequest>()
    private var cursoSeleccionado: CursoRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CursoadminactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exitCursoButton.setOnClickListener {
            val intent = Intent(this,AdminActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.addCursoButton.setOnClickListener {
            val intent = Intent(this,AgregarCursoActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cursoRecyclerView.layoutManager = LinearLayoutManager(this)

        adaptador = CursoAdaptador(listaCursos) { cursos ->
            cursoSeleccionado = cursos
        }
        binding.cursoRecyclerView.adapter = adaptador
        obtenerCurso()
    }

    private fun obtenerCurso(){
        cursoService.obtenerCursos().enqueue(object: Callback<List<CursoRequest>>{
            override fun onResponse(
                call: Call<List<CursoRequest>>,
                response: Response<List<CursoRequest>>
            ) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    listaCursos.clear()
                    listaCursos.addAll(lista)
                    adaptador.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@CursoAdminActivity, "Error al obtener usuarios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<List<CursoRequest>>, t: Throwable) {
                Toast.makeText(this@CursoAdminActivity, "Fallo la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}