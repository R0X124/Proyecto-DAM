package com.grupo.proyecto_dam.profesor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.grupo.proyecto_dam.R
import com.grupo.proyecto_dam.data.adaptador.AlumnoAdaptador
import com.grupo.proyecto_dam.data.adaptador.CursoAdaptador
import com.grupo.proyecto_dam.data.api.CursoAlumnoGet
import com.grupo.proyecto_dam.data.model.UserRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.ListaalumnosactivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaAlumnosActivity : AppCompatActivity() {

    private lateinit var binding: ListaalumnosactivityBinding
    private lateinit var alumnoAdaptador: AlumnoAdaptador
    private lateinit var alumnoGet: CursoAlumnoGet
    private var alumnototal: UserRequest? = null
    private val listaalumno: MutableList<UserRequest> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar Binding
        binding = ListaalumnosactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alumnoGet = RetrofitClient.instance.create(CursoAlumnoGet::class.java)
        alumnoAdaptador = AlumnoAdaptador(listaalumno){
                alumno -> alumnototal = alumno
            Toast.makeText(this, "Curso Seleccionado ${alumno.nombre}", Toast.LENGTH_SHORT).show()
        }

        binding.listaAlumnosrecycleview.layoutManager = LinearLayoutManager(this)
        binding.listaAlumnosrecycleview.adapter = alumnoAdaptador

        // Recuperar el ID del curso
        val cursoId = intent.getLongExtra("id", -1L)
        if (cursoId != -1L) {
            cargarListaAlumnos(cursoId)
        } else {
            Toast.makeText(this, "No se pudo obtener el curso seleccionado", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.addCursoButton.setOnClickListener{
            agregarNotas(cursoId,alumnototal!!)
        }
    }

    private fun cargarListaAlumnos(cursoId: Long) {
        alumnoGet.alumnosPorCurso(cursoId).enqueue(object : Callback<List<UserRequest>> {
            override fun onResponse(
                call: Call<List<UserRequest>>,
                response: Response<List<UserRequest>>
            ) {
                if (response.isSuccessful) {
                    listaalumno.clear()
                    listaalumno.addAll(response.body() ?: emptyList())
                    alumnoAdaptador.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@ListaAlumnosActivity,
                        "No se encontraron alumnos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("LISTA", "${response}")
            }

            override fun onFailure(call: Call<List<UserRequest>>, t: Throwable) {
                Toast.makeText(
                    this@ListaAlumnosActivity,
                    "Error al cargar alumnos: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun agregarNotas(curso: Long, alumno: UserRequest){
        val intent = Intent(this, AgregarNotasProfesorActivity::class.java)
        intent.putExtra("id", curso)
        intent.putExtra("id", alumno.id)
        startActivity(intent)
    }
}
