package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.grupo.proyecto_dam.data.adaptador.Adaptador
import com.grupo.proyecto_dam.data.api.UserDelete
import com.grupo.proyecto_dam.data.api.UserGet
import com.grupo.proyecto_dam.data.api.UserService
import com.grupo.proyecto_dam.data.model.UserRequest
import com.grupo.proyecto_dam.data.network.RetrofitClient
import com.grupo.proyecto_dam.databinding.UsuarioactivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioActivity : AppCompatActivity() {

    private lateinit var binding: UsuarioactivityBinding
    private lateinit var userService: UserGet
    private lateinit var userDelete: UserDelete
    private lateinit var adaptador: Adaptador
    private val listaUsuarios = mutableListOf<UserRequest>()
    private var usuarioSeleccionado: UserRequest? = null
    private val TAG = "UsuarioActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UsuarioactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el servicio Retrofit
        userService = RetrofitClient.instance.create(UserGet::class.java)
        userDelete = RetrofitClient.instance.create(UserDelete::class.java)

        // Configurar el RecyclerView
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar el adaptador
        adaptador = Adaptador(listaUsuarios) { usuario ->
            usuarioSeleccionado = usuario
        }
        binding.usersRecyclerView.adapter = adaptador

        // Cargar los usuarios desde el API
        obtenerUsuarios()

        // Botón para salir
        binding.exitUserButton.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.editUserButton.setOnClickListener{
            if (usuarioSeleccionado != null) {
                editarUsuario(usuarioSeleccionado!!)
            } else {
                Toast.makeText(this, "Seleccione un usuario para editar", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para agregar un nuevo usuario
        binding.addUserButton.setOnClickListener {
            val intent = Intent(this, AgregarUsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón para eliminar el usuario seleccionado
        binding.deleteUserButton.setOnClickListener {
            if (usuarioSeleccionado != null) {
                eliminarUsuario(usuarioSeleccionado!!)
            } else {
                Toast.makeText(this, "Seleccione un usuario para eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerUsuarios() {
        Log.d(TAG, "Iniciando solicitud para obtener usuarios")
        userService.obtenerUsuarios().enqueue(object : Callback<List<UserRequest>> {
            override fun onResponse(
                call: Call<List<UserRequest>>,
                response: Response<List<UserRequest>>
            ) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    listaUsuarios.clear()
                    listaUsuarios.addAll(lista)
                    adaptador.notifyDataSetChanged()
                    Log.d(TAG, "Usuarios obtenidos: ${listaUsuarios.size}")
                } else {
                    Log.e(TAG, "Error al obtener usuarios: Código HTTP ${response.code()}")
                    Toast.makeText(this@UsuarioActivity, "Error al obtener usuarios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserRequest>>, t: Throwable) {
                Log.e(TAG, "Fallo la conexión: ${t.message}", t)
                Toast.makeText(this@UsuarioActivity, "Fallo la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarUsuario(usuario: UserRequest) {
        if (usuario.id != null) {
            userDelete.eliminarUsuario(usuario.id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        listaUsuarios.remove(usuario)
                        adaptador.notifyDataSetChanged()
                        Toast.makeText(this@UsuarioActivity, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "Error al eliminar usuario")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, "Fallo al eliminar usuario: ${t.message}")
                }
            })
        } else {
            Toast.makeText(this, "ID de usuario no válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editarUsuario(usuario: UserRequest){
        val intent = Intent(this, EditarUsuarioActivity::class.java)
        intent.putExtra("id",usuario.id)
        intent.putExtra("nombre", usuario.nombre)
        intent.putExtra("apellido", usuario.apellido)
        intent.putExtra("correo", usuario.correo)
        intent.putExtra("contrasena", usuario.contrasena)
        intent.putExtra("rol", usuario.rol)
        startActivity(intent)
    }
}
