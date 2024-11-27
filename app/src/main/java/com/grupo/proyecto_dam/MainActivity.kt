package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo.proyecto_dam.data.api.ApiService
import com.grupo.proyecto_dam.data.api.ApiServiceId
import com.grupo.proyecto_dam.data.model.UserRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonIniciarSesion.setOnClickListener {

            val correo = findViewById<EditText>(R.id.editTextUsuario).text.toString()
            val contrasena = findViewById<EditText>(R.id.editTextContrasena).text.toString()
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            apiService.login(correo, contrasena).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("LoginResponse", "Response: $response")
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            obtenerUsuarioPorCorreo(correo) // Llama al método para obtener el ID
                            Log.d("LoginResponse", "Response: $loginResponse")
                            // Redirige según el rol obtenido en la respuesta
                            when (loginResponse.toString()) {
                                "adminDashboard" -> goToAdminDashboard()
                                "teacherDashboard" -> goToTeacherDashboard()
                                "studentDashboard" -> goToStudentDashboard()
                                else -> Toast.makeText(this@MainActivity, "Rol no reconocido", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Log.e("LoginResponse", "Response body is null")
                            Toast.makeText(this@MainActivity, "Error: Response body is null", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Log.e("LoginResponse", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("LoginResponse", "Failure: ${t.message}")
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.textViewRecuperar.setOnClickListener {
            val intent = Intent(this, recuperarPassw::class.java)
            startActivity(intent)
        }

    }

    private fun goToAdminDashboard() {
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToTeacherDashboard() {
        val intent = Intent(this, ProfesorActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToStudentDashboard() {
        val intent = Intent(this, menuEstudiante::class.java)
        startActivity(intent)
        finish()
    }


    private fun obtenerUsuarioPorCorreo(correo: String) {
        Log.d("Correo", "Correo enviado: $correo")
        val apiServiceId = RetrofitClient.instance.create(ApiServiceId::class.java)
        apiServiceId.obtenerUsuarioPorCorreo(correo).enqueue(object : Callback<UserRequest> {
            override fun onResponse(call: Call<UserRequest>, response: Response<UserRequest>) {
                Log.d("API_RESPONSE", "Response code: ${response.code()}") // Verifica el código de respuesta
                Log.d("API_RESPONSE", "Response body: ${response.body()}") // Verifica el contenido de la respuesta
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        Log.d("Usuario", "ID: ${usuario.id}, Nombre: ${usuario.nombre}")
                        guardarIdUsuario(usuario.id) // Guarda el ID del usuario en SharedPreferences
                    } else {
                        Log.e("Usuario", "El cuerpo de la respuesta es nulo")
                        Toast.makeText(this@MainActivity, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("API_RESPONSE", "Error al obtener usuario: ${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Error al obtener usuario", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserRequest>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun guardarIdUsuario(id: Long) {
        val sharedPref = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putLong("usuario_id", id)
            apply()
        }
    }

}