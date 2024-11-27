package com.grupo.proyecto_dam.data.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo.proyecto_dam.R
import com.grupo.proyecto_dam.data.model.UserRequest

class AlumnoAdaptador(
    private val usuarios: List<UserRequest>,
    private val onUsuarioClick: (UserRequest) -> Unit // Callback para manejar clics
) : RecyclerView.Adapter<AlumnoAdaptador.AlumnoViewHolder>() {

    inner class AlumnoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewRol: ImageView = itemView.findViewById(R.id.imageViewRol)
        private val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        private val textViewCorreo: TextView = itemView.findViewById(R.id.textViewCorreo)
        private val textViewRol: TextView = itemView.findViewById(R.id.textViewRol)

        fun bind(usuario: UserRequest) {
            // Asignar datos al layout
            textViewNombre.text = "${usuario.nombre} ${usuario.apellido}"
            textViewCorreo.text = usuario.correo
            textViewRol.text = usuario.rol

            // Cambiar la imagen según el rol
            val rolImage = when (usuario.rol.uppercase()) {
                "ALUMNO" -> R.drawable.usuario
                "PROFESOR" -> R.drawable.usuario
                else -> R.drawable.usuario
            }
            imageViewRol.setImageResource(rolImage)

            // Manejar clics
            itemView.setOnClickListener { onUsuarioClick(usuario) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fila_usuarios, parent, false)
        return AlumnoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        holder.bind(usuarios[position])
    }

    override fun getItemCount(): Int = usuarios.size
}
