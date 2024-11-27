package com.grupo.proyecto_dam.data.adaptador

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo.proyecto_dam.R
import com.grupo.proyecto_dam.data.model.UserRequest

class Adaptador(
    private val usuarios: MutableList<UserRequest>,
    private val onUsuarioSeleccionado: (UserRequest) -> Unit
) : RecyclerView.Adapter<Adaptador.UsuarioViewHolder>() {

    private var usuarioSeleccionado: UserRequest? = null

    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.usuarioImagen)
        val nombreCompleto: TextView = itemView.findViewById(R.id.usuarioNombreCompleto)
        val correo: TextView = itemView.findViewById(R.id.usuarioCorreo)
        val rol: TextView = itemView.findViewById(R.id.usuarioRol)

        init {
            // Configurar el clic para seleccionar un usuario
            itemView.setOnClickListener {
                usuarioSeleccionado = usuarios[adapterPosition]
                onUsuarioSeleccionado(usuarios[adapterPosition])
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fila, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]

        // Concatenar nombre y apellido para mostrar el nombre completo
        holder.nombreCompleto.text = "${usuario.nombre} ${usuario.apellido}"

        // Mostrar el correo
        holder.correo.text = usuario.correo

        // Mostrar el rol
        holder.rol.text = usuario.rol

        // Configurar la imagen según el rol
        when (usuario.rol.uppercase()) {
            "ALUMNO" -> holder.imagen.setImageResource(R.drawable.usuario)
            "PROFESOR" -> holder.imagen.setImageResource(R.drawable.usuario)
            "ADMINISTRADOR" -> holder.imagen.setImageResource(R.drawable.usuario)
            else -> holder.imagen.setImageResource(R.drawable.usuario)
        }

        // Resaltar el usuario seleccionado
        if (usuario == usuarioSeleccionado) {
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = usuarios.size

    // Método para eliminar un usuario de la lista y actualizar el RecyclerView
    fun eliminarUsuarioDeLista(usuario: UserRequest) {
        val index = usuarios.indexOf(usuario)
        if (index != -1) {
            usuarios.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
