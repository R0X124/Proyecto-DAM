package com.grupo.proyecto_dam

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CursoAdapter(
    private val cursosList: List<Curso>,
    private val context: Context
) : RecyclerView.Adapter<CursoAdapter.CursoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_curso, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursosList[position]
        holder.bind(curso)
    }

    override fun getItemCount(): Int = cursosList.size

    inner class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreCursoTextView: TextView = itemView.findViewById(R.id.txt_nombre_curso)
        private val nombreDocenteTextView: TextView = itemView.findViewById(R.id.txt_nombre_docente)
        private val notaTotalTextView: TextView = itemView.findViewById(R.id.txt_nota_total)
        private val verDetallesTextView: TextView = itemView.findViewById(R.id.txt_ver_detalles)
        private val verParticipantesTextView: TextView = itemView.findViewById(R.id.txt_ver_participantes)

        fun bind(curso: Curso) {
            nombreCursoTextView.text = curso.nombre
            nombreDocenteTextView.text = "Docente: ${curso.docente}"
            // Ahora mostramos la nota como un entero
            notaTotalTextView.text = "Nota Total: ${curso.nota}"

            // Listener para "Ver detalles del curso"
            verDetallesTextView.setOnClickListener {
                val intent = Intent(context, detalles_cursos::class.java)
                intent.putExtra("cursoNombre", curso.nombre)
                context.startActivity(intent)
            }

            // Listener para "Ver participantes del curso"
            verParticipantesTextView.setOnClickListener {
                val intent = Intent(context, detalle_participantes::class.java)
                intent.putExtra("cursoNombre", curso.nombre)
                context.startActivity(intent)
            }
        }
    }
}
