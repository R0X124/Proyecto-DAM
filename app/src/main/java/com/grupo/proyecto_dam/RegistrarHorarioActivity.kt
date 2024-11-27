package com.grupo.proyecto_dam

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.grupo.proyecto_dam.data.api.CursoService
import com.grupo.proyecto_dam.data.model.CursoRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.ActivityRegistrarHorarioBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarHorarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarHorarioBinding
    private lateinit var cursoService: CursoService
    private val listaCursos = mutableListOf<CursoRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarHorarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cursoService = RetrofitClient.instance.create(CursoService::class.java)

        cargarCursos()

        binding.btnGuardarHorario.setOnClickListener {
            val cursoSeleccionado = listaCursos[binding.spinnerCursos.selectedItemPosition]
            val horario = binding.horarioEditText.text.toString()

            if (horario.isBlank()) {
                Toast.makeText(this, "Por favor ingrese un horario.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            cursoSeleccionado.id?.let { cursoId ->
                //guardarHorario(cursoId, horario)
            } ?: run {
                Toast.makeText(this, "El curso seleccionado no tiene un ID válido.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun cargarCursos() {
        val profesorId = intent.getLongExtra("usuario_id", -1L)
        if (profesorId == -1L) {
            Toast.makeText(this, "ID del profesor no encontrado.", Toast.LENGTH_SHORT).show()
            finish()
        }

        cursoService.obtenerCursos().enqueue(object : Callback<List<CursoRequest>> {
            override fun onResponse(
                call: Call<List<CursoRequest>>,
                response: Response<List<CursoRequest>>
            ) {
                if (response.isSuccessful) {
                    listaCursos.clear()
                    // Filtrar localmente los cursos del profesor
                    listaCursos.addAll(response.body()?.filter { it.profesor?.id == profesorId } ?: emptyList())
                    val nombresCursos = listaCursos.map { it.nombre }
                    binding.spinnerCursos.adapter = ArrayAdapter(
                        this@RegistrarHorarioActivity,
                        android.R.layout.simple_spinner_item,
                        nombresCursos
                    )
                } else {
                    Toast.makeText(this@RegistrarHorarioActivity, "Error al cargar cursos.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CursoRequest>>, t: Throwable) {
                Toast.makeText(this@RegistrarHorarioActivity, "Error de conexión.", Toast.LENGTH_SHORT).show()
            }
        })
    }



    /*private fun guardarHorario(cursoId: Long, horario: String) {
        cursoService.registrarHorario(cursoId, horario).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegistrarHorarioActivity, "Horario registrado correctamente.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegistrarHorarioActivity, "Error al guardar el horario.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@RegistrarHorarioActivity, "Error de conexión.", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
}

