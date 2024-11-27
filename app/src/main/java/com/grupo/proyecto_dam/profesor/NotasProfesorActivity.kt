package com.grupo.proyecto_dam.profesor

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.grupo.proyecto_dam.AgregarCursoProfesorActivity
import com.grupo.proyecto_dam.ProfesorActivity
import com.grupo.proyecto_dam.data.adaptador.CursoAdaptador
import com.grupo.proyecto_dam.data.api.CursoProfesorGet
import com.grupo.proyecto_dam.data.model.CursoRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.NotasprofesoractivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotasProfesorActivity : AppCompatActivity() {

    private lateinit var binding: NotasprofesoractivityBinding
    private lateinit var cursoAdaptador: CursoAdaptador
    private lateinit var cursoProfesorGet: CursoProfesorGet
    private var cursototal: CursoRequest? = null
    private val listaCursos = mutableListOf<CursoRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = NotasprofesoractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cursoProfesorGet = RetrofitClient.instance.create(CursoProfesorGet::class.java)
        cursoAdaptador = CursoAdaptador(listaCursos){
                curso -> cursototal = curso
            Toast.makeText(this, "Curso Seleccionado ${curso.nombre}", Toast.LENGTH_SHORT).show()
        }

        binding.cursoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cursoRecyclerView.adapter = cursoAdaptador

        // Obtener el ID del profesor y cargar los cursos
        val profesorId = obtenerIdUsuario()
        if (profesorId != -1L) {
            cargarCursosProfesor(profesorId)
        } else {
            Toast.makeText(this, "ID del profesor no encontrado.", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.buttonSalirprofesor.setOnClickListener {
            val intent = Intent(this, ProfesorActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.listaAlumnos.setOnClickListener {
            lista(cursototal!!)
        }
    }
    private fun cargarCursosProfesor(profesorId: Long) {
        cursoProfesorGet.obtenerCursoGet(profesorId).enqueue(object : Callback<List<CursoRequest>> {
            override fun onResponse(
                call: Call<List<CursoRequest>>,
                response: Response<List<CursoRequest>>
            ) {
                if (response.isSuccessful) {
                    listaCursos.clear()
                    listaCursos.addAll(response.body() ?: emptyList())
                    cursoAdaptador.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@NotasProfesorActivity, "Error al cargar cursos.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CursoRequest>>, t: Throwable) {
                Toast.makeText(this@NotasProfesorActivity, "Error de conexión.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerIdUsuario(): Long {
        val sharedPref = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        return sharedPref.getLong("usuario_id", -1L) // Devuelve -1L si no está definido
    }

    private fun lista(curso: CursoRequest){
        val intent = Intent(this, ListaAlumnosActivity::class.java)
        intent.putExtra("id", curso.id)
        /*intent.putExtra("nombre", curso.nombre)
        intent.putExtra("descripcion",curso.descripcion)
        intent.putExtra("profesor", curso.profesor)*/
        startActivity(intent)
    }
}