package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.grupo.proyecto_dam.data.adaptador.CursoAdaptador
import com.grupo.proyecto_dam.data.api.CursoAlumnoPut
import com.grupo.proyecto_dam.data.api.CursoService
import com.grupo.proyecto_dam.data.model.CursoRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.FragmentInscripcionBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class inscripcion : Fragment() {

    private lateinit var binding: FragmentInscripcionBinding
    private lateinit var cursoService: CursoService
    private lateinit var alumnoPut: CursoAlumnoPut
    private lateinit var listaCursos: MutableList<CursoRequest>
    private lateinit var cursoAdapter: CursoAdaptador


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInscripcionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cursoService = RetrofitClient.instance.create(CursoService::class.java)
        alumnoPut = RetrofitClient.instance.create(CursoAlumnoPut::class.java)

        listaCursos = mutableListOf()

        // Configurar RecyclerView
        configurarRecyclerView()
        cargarCursos()

        binding.salirbtn.setOnClickListener {
            val intent = Intent(requireContext(), menuEstudiante::class.java)
            startActivity(intent)
        }

        binding.btnalumnoinscripcion.setOnClickListener {
            val cursoId = cursoAdapter.getCursoSeleccionado()?.id
            val alumnoId = obtenerIdAlumno()

            if (cursoId != null && alumnoId != null) {
                inscribirAlumnoEnCurso(cursoId, alumnoId)
            } else {
                Toast.makeText(context, "Datos incompletos. Seleccione un curso.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * Configurar RecyclerView con el adaptador
     */
    private fun configurarRecyclerView() {
        cursoAdapter = CursoAdaptador(listaCursos) { curso ->
            Toast.makeText(requireContext(), "Curso seleccionado: ${curso.nombre}", Toast.LENGTH_SHORT).show()
        }
        binding.cursoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cursoRecyclerView.adapter = cursoAdapter
    }

    /**
     * Cargar cursos desde la API y actualizarlos en el RecyclerView
     */
    private fun cargarCursos() {
        cursoService.obtenerCursos().enqueue(object : Callback<List<CursoRequest>> {
            override fun onResponse(
                call: Call<List<CursoRequest>>,
                response: Response<List<CursoRequest>>
            ) {
                if (response.isSuccessful) {
                    listaCursos.clear()
                    listaCursos.addAll(response.body() ?: emptyList())
                    cursoAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Error al cargar cursos", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<CursoRequest>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Obtener el ID del profesor actualmente autenticado
     */
    private fun obtenerIdAlumno(): Long {
        val sharedPref = requireContext().getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        return sharedPref.getLong("usuario_id", -1L) // Retorna el ID o -1L si no está definido
    }

    /**
     * Asignar un alumno a un curso llamando al endpoint del backend
     */
    private fun inscribirAlumnoEnCurso(cursoId: Long, alumnoId: Long) {
        val alumnosIds = listOf(alumnoId) // Crear una lista con el ID del alumno
        val call = alumnoPut.inscribirAlumnosEnCurso(cursoId, alumnosIds)

        call.enqueue(object : Callback<CursoRequest> {
            override fun onResponse(call: Call<CursoRequest>, response: Response<CursoRequest>) {
                if (response.isSuccessful) {
                    // Respuesta exitosa
                    val curso = response.body()
                    Toast.makeText(
                        requireContext(),
                        "Profesor asignado correctamente: ${curso?.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Error del servidor
                    Toast.makeText(
                        requireContext(),
                        "Error al asignar profesor: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<CursoRequest>, t: Throwable) {
                // Error de conexión o problema con la llamada
                Toast.makeText(
                    requireContext(),
                    "Error de conexión: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}