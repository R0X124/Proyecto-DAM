package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.grupo.proyecto_dam.data.api.UserService
import com.grupo.proyecto_dam.data.model.UserRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.AgregarusuarioactivityBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: AgregarusuarioactivityBinding
    val userService: UserService = RetrofitClient.instance.create(UserService::class.java)
    private val TAG = "AgregarUsuarioActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar View Binding
        binding = AgregarusuarioactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Spinner con el array de roles
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.roles_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRol.adapter = adapter

        // Botón para salir
        binding.buttonSalir.setOnClickListener {
            val intent = Intent(this, UsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón para guardar usuario
        binding.buttonGuardar.setOnClickListener {
            val nombre = binding.editTextNombre.text.toString().trim()
            val contrasena = binding.editTextContrasena.text.toString().trim()
            val apellido = binding.editTextApellido.text.toString().trim()
            val correo = binding.editTextCorreo.text.toString().trim()
            val rol = binding.spinnerRol.selectedItem.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty()) {
                val usuario = UserRequest(0,nombre, apellido, correo, contrasena, rol)
                Log.d(TAG, "Datos del usuario a enviar: $usuario")
                agregarUsuario(usuario)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun agregarUsuario(usuario: UserRequest) {
        userService.agregarUsuario(usuario).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Usuario agregado exitosamente")
                    Toast.makeText(this@AgregarUsuarioActivity, "Usuario agregado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "Error al agregar usuario: Código HTTP ${response.code()}")
                    Log.e(TAG, "Mensaje del cuerpo de respuesta: ${response.errorBody()?.string()}")
                    Toast.makeText(this@AgregarUsuarioActivity, "Error al agregar usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Fallo la conexión: ${t.message}", t)
                Toast.makeText(this@AgregarUsuarioActivity, "Fallo la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
