package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.grupo.proyecto_dam.data.api.UserEdit
import com.grupo.proyecto_dam.data.api.UserService
import com.grupo.proyecto_dam.data.model.UserRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.EditarusuarioactivityBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: EditarusuarioactivityBinding
    private val userEdit: UserEdit = RetrofitClient.instance.create(UserEdit::class.java)
    private val TAG = "EditarUsuarioActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditarusuarioactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.roles_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRol.adapter = adapter

        // Obtener los datos del intent
        val usuarioId = intent.getLongExtra("id", -1)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val correo = intent.getStringExtra("correo")
        val contrasena = intent.getStringExtra("contrasena")
        val rol = intent.getStringExtra("rol")

        if (usuarioId != -1L) {
            // Rellenar los campos con los datos obtenidos
            binding.editTextNombre.setText(nombre)
            binding.editTextApellido.setText(apellido)
            binding.editTextCorreo.setText(correo)
            binding.editTextContrasena.setText(contrasena)

            // Seleccionar el rol correcto en el Spinner y deshabilitarlo
            val rolIndex = adapter.getPosition(rol)
            binding.spinnerRol.setSelection(rolIndex)
            binding.spinnerRol.isEnabled = false // No se puede cambiar el rol
        }

        // Botón para guardar cambios
        binding.buttonGuardar.setOnClickListener {
            val nombreEditado = binding.editTextNombre.text.toString()
            val apellidoEditado = binding.editTextApellido.text.toString()
            val correoEditado = binding.editTextCorreo.text.toString()
            val contrasenaEditada = binding.editTextContrasena.text.toString()
            val rolEditada = binding.spinnerRol.selectedItem.toString()


            if (nombreEditado.isNotEmpty() && apellidoEditado.isNotEmpty() && correoEditado.isNotEmpty() && contrasenaEditada.isNotEmpty()) {
                val usuarioEditado = UserRequest(
                    id = usuarioId,
                    nombre = nombreEditado,
                    apellido = apellidoEditado,
                    correo = correoEditado,
                    contrasena = contrasenaEditada,
                    rol = rolEditada
                )
                Log.e("Usuario", "${usuarioEditado}")
                actualizarUsuario(usuarioEditado)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, UsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.buttonSalir.setOnClickListener{
            val intent = Intent(this, UsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun actualizarUsuario(usuario: UserRequest) {
        userEdit.actualizarUsuario(usuario.id!!, usuario).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarUsuarioActivity, "Se cambio correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.e(TAG, "Error al actualizar usuario")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Error de conexión: ${t.message}")
                Toast.makeText(this@EditarUsuarioActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
