package com.grupo.proyecto_dam.profesor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo.proyecto_dam.CursoProfesorActivity
import com.grupo.proyecto_dam.ProfesorActivity
import com.grupo.proyecto_dam.R
import com.grupo.proyecto_dam.data.api.CursoHorarioPut
import com.grupo.proyecto_dam.data.model.CursoRequest
import com.grupo.proyecto_dam.data.model.UserRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.PruebaactivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class pruebaactivity : AppCompatActivity() {

    private lateinit var binding: PruebaactivityBinding
    private lateinit var cursohorarioput: CursoHorarioPut

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = PruebaactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cursohorarioput = RetrofitClient.instance.create(CursoHorarioPut::class.java)

        val adapter = ArrayAdapter.createFromResource(this, R.array.dia_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerDia.adapter = adapter

        val cursoid = intent.getLongExtra("id", -1)
        /*val cursonombre = intent.getStringExtra("nombre")
        val cursodescripcion = intent.getStringExtra("descripcion")
        val cursoprofesor = intent.getSerializableExtra("profesor") as? UserRequest*/

        binding.buttonSalir.setOnClickListener{
            val intent = Intent(this, CursoProfesorActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegistrar.setOnClickListener{
            val hora_inicio = binding.editTextHoraInicio.text.toString()
            val hora_final = binding.editTextHoraFin.text.toString()
            val spinnerDia = binding.spinnerDia.selectedItem.toString()

            if(hora_final.isNotEmpty() && hora_inicio.isNotEmpty() && spinnerDia.isNotEmpty()){
                val curso = CursoRequest(cursoid, null, null, null, spinnerDia, hora_inicio, hora_final)
                agregarparteCurso(curso)
            } else {
                Toast.makeText(this, "Los valores no tienen que ser nulos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun agregarparteCurso(curso: CursoRequest) {
        // Realiza la llamada al endpoint utilizando Retrofit
        cursohorarioput.agregarHorario(curso.id,curso.dia,curso.horaInicio,curso.horaFin).enqueue(object : Callback<CursoRequest> {
            override fun onResponse(call: Call<CursoRequest>, response: Response<CursoRequest>) {
                if (response.isSuccessful && response.body() != null) {
                    // Si la respuesta es exitosa y contiene datos
                    val curso = response.body()
                    Log.d("horario", "${response.body()}")
                    Toast.makeText(this@pruebaactivity, "Horario agregado exitosamente: ${curso?.dia}", Toast.LENGTH_SHORT).show()
                } else {
                    // Si la respuesta no es exitosa (error en el servidor)
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (errorBody.isNullOrEmpty()) {
                        "Error desconocido al agregar horario"
                    } else {
                        "Error: $errorBody"
                    }
                    Log.e("prueba","${response}")
                    Toast.makeText(this@pruebaactivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CursoRequest>, t: Throwable) {
                // Si ocurre un error de red o en la llamada HTTP
                Toast.makeText(this@pruebaactivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }
}