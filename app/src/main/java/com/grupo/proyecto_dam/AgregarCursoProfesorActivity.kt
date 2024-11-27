package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.grupo.proyecto_dam.data.adaptador.CursoAdaptador
import com.grupo.proyecto_dam.data.api.CursoProfesorPut
import com.grupo.proyecto_dam.data.api.CursoSinProfesor
import com.grupo.proyecto_dam.data.model.CursoRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.AgregarcursoprofesoractivityBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarCursoProfesorActivity : AppCompatActivity() {

    private lateinit var binding: AgregarcursoprofesoractivityBinding
    private lateinit var cursoApi: CursoProfesorPut
    private lateinit var cursoGet: CursoSinProfesor
    private lateinit var listaCursos: MutableList<CursoRequest>
    private lateinit var cursoAdapter: CursoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AgregarcursoprofesoractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cursoApi = RetrofitClient.instance.create(CursoProfesorPut::class.java)
        cursoGet = RetrofitClient.instance.create(CursoSinProfesor::class.java)

        listaCursos = mutableListOf()

        // Configurar RecyclerView
        configurarRecyclerView()

        // Cargar cursos desde la API
        cargarCursos()

        // Botón para inscribirse a un curso
        binding.addCursoButton.setOnClickListener {
            val cursoSeleccionado = cursoAdapter.getCursoSeleccionado()
            val profesorId = obtenerIdProfesor()

            if (profesorId == -1L) {
                Toast.makeText(this, "ID del profesor no encontrado. Por favor, inicia sesión nuevamente.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cursoSeleccionado == null) {
                Toast.makeText(this, "Seleccione un curso", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            cursoSeleccionado.id?.let { cursoId ->
                asignarProfesorCurso(cursoId, profesorId)
            } ?: run {
                Toast.makeText(this, "El curso seleccionado no tiene un ID válido.", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para salir
        binding.exitCursoButton.setOnClickListener {
            val intent = Intent(this, CursoProfesorActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Configurar RecyclerView con el adaptador
     */
    private fun configurarRecyclerView() {
        cursoAdapter = CursoAdaptador(listaCursos) { curso ->
            Toast.makeText(this, "Curso seleccionado: ${curso.nombre}", Toast.LENGTH_SHORT).show()
        }
        binding.cursoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cursoRecyclerView.adapter = cursoAdapter
    }

    /**
     * Cargar cursos desde la API y actualizarlos en el RecyclerView
     */
    private fun cargarCursos() {
        cursoGet.obtenercursoSinProfesor().enqueue(object : Callback<List<CursoRequest>> {
            override fun onResponse(
                call: Call<List<CursoRequest>>,
                response: Response<List<CursoRequest>>
            ) {
                if (response.isSuccessful) {
                    listaCursos.clear()
                    listaCursos.addAll(response.body() ?: emptyList())
                    cursoAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@AgregarCursoProfesorActivity, "Error al cargar cursos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CursoRequest>>, t: Throwable) {
                Toast.makeText(this@AgregarCursoProfesorActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Obtener el ID del profesor actualmente autenticado
     */
    private fun obtenerIdProfesor(): Long {
        val sharedPref = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        return sharedPref.getLong("usuario_id", -1L) // Retorna el ID o -1L si no está definido
    }

    /**
     * Asignar un profesor a un curso llamando al endpoint del backend
     */
    private fun asignarProfesorCurso(cursoId: Long, profesorId: Long) {
        lifecycleScope.launch {
            try {
                val response = cursoApi.asignarProfesor(cursoId, profesorId)
                if (response.isSuccessful) {
                    Toast.makeText(this@AgregarCursoProfesorActivity, "Profesor asignado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AgregarCursoProfesorActivity, "Error al asignar profesor", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AgregarCursoProfesorActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
