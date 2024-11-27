package com.grupo.proyecto_dam.profesor

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo.proyecto_dam.R
import com.grupo.proyecto_dam.data.api.CursoNotasPut
import com.grupo.proyecto_dam.data.model.EvaluacionRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.AgregarnotasprofesoractivityBinding
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class AgregarNotasProfesorActivity : AppCompatActivity() {

    private lateinit var binding: AgregarnotasprofesoractivityBinding
    private lateinit var cursoNotasPut: CursoNotasPut
    private var cursoId: Long = -1L
    private var alumnoId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de Binding
        binding = AgregarnotasprofesoractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa Retrofit
        cursoNotasPut = RetrofitClient.instance.create(CursoNotasPut::class.java)

        // Recupera datos del Intent
        cursoId = intent.getLongExtra("CURSO_ID", -1L)
        alumnoId = intent.getLongExtra("ALUMNO_ID", -1L)

        // Validar que los IDs sean válidos
        if (cursoId == -1L || alumnoId == -1L) {
            Toast.makeText(this, "Datos inválidos. Regresa a la lista.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Botón para Guardar Notas
        binding.buttonGuardarNotas.setOnClickListener {
            guardarNotas()
        }

        // Botón para Salir
        binding.buttonSalirNotas.setOnClickListener {
            finish() // Finaliza la actividad
        }
    }

    private fun guardarNotas() {
        // Obtén las notas de los campos
        val notaT1 = binding.editTextNotaT1.text.toString().toDoubleOrNull()
        val examenParcial = binding.editTextExamenParcial.text.toString().toDoubleOrNull()
        val notaT2 = binding.editTextNotaT2.text.toString().toDoubleOrNull()
        val examenFinal = binding.editTextExamenFinal.text.toString().toDoubleOrNull()

        // Validar que las notas no sean nulas
        if (notaT1 == null || examenParcial == null || notaT2 == null || examenFinal == null) {
            Toast.makeText(this, "Todos los campos deben estar llenos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Llamada a Retrofit para guardar las notas
        cursoNotasPut.calificarNotas(cursoId, alumnoId, notaT1, examenParcial, notaT2, examenFinal)
            .enqueue(object : Callback<EvaluacionRequest> {
                override fun onResponse(
                    call: Call<EvaluacionRequest>,
                    response: Response<EvaluacionRequest>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AgregarNotasProfesorActivity,
                            "Notas guardadas correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // Finaliza la actividad después de guardar
                    } else {
                        Toast.makeText(
                            this@AgregarNotasProfesorActivity,
                            "Error al guardar las notas: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<EvaluacionRequest>, t: Throwable) {
                    Toast.makeText(
                        this@AgregarNotasProfesorActivity,
                        "Error de red: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
