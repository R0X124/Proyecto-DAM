    package com.grupo.proyecto_dam.data.adaptador

    import android.graphics.Color
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.grupo.proyecto_dam.R
    import com.grupo.proyecto_dam.data.model.CursoRequest

    class CursoAdaptador(
        private val cursos: MutableList<CursoRequest>,
        private val onCursoSeleccionado: (CursoRequest) -> Unit
    ) : RecyclerView.Adapter<CursoAdaptador.CursoViewHolder>() {

        private var cursoSeleccionado: CursoRequest? = null

        inner class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imagen: ImageView = itemView.findViewById(R.id.cursoImagen)
            val nombre: TextView = itemView.findViewById(R.id.cursoNombre)
            val profesor: TextView = itemView.findViewById(R.id.cursoProfesorEstado)
            val descripcion: TextView = itemView.findViewById(R.id.cursoDescripcion)
            val dia: TextView = itemView.findViewById(R.id.cursoDia)
            val horario_inicio: TextView = itemView.findViewById(R.id.cursoHorarioInicio)
            val horario_final: TextView = itemView.findViewById(R.id.cursoHorarioFinal)

            init {
                itemView.setOnClickListener {
                    cursoSeleccionado = cursos[adapterPosition]
                    onCursoSeleccionado(cursos[adapterPosition])
                    notifyDataSetChanged() // Actualizar la vista para resaltar la selección
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fila_cursos, parent, false)
            return CursoViewHolder(view)
        }

        override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
            val curso = cursos[position]

            // Mostrar el nombre y la descripción del curso
            holder.nombre.text = curso.nombre
            holder.descripcion.text = curso.descripcion

            // Mostrar la información del profesor
            holder.profesor.text = if (curso.profesor == null) {
                "No asignado"
            } else {
                "${curso.profesor.nombre} ${curso.profesor.apellido}"
            }
            holder.dia.text = if (curso.dia == null){
                "Dia: No asignada"
            } else {
                "Dia: ${curso.dia}"
            }

            holder.horario_inicio.text = if (curso.horaInicio == null){
                "Inicio: No asignado"
            } else {
                "Inicio: ${curso.horaInicio}"
            }

            holder.horario_final.text = if (curso.horaFin == null){
                "Final: No asignado"
            } else {
                "Final: ${curso.horaFin}"
            }

            // Cargar una imagen genérica
            holder.imagen.setImageResource(R.drawable.cursos)

            // Resaltar el curso seleccionado
            if (curso == cursoSeleccionado) {
                holder.itemView.setBackgroundColor(Color.LTGRAY) // Color para curso seleccionado
            } else {
                holder.itemView.setBackgroundColor(Color.WHITE) // Color normal
            }
        }

        override fun getItemCount(): Int = cursos.size

        // Método para obtener el curso seleccionado
        fun getCursoSeleccionado(): CursoRequest? {
            return cursoSeleccionado
        }

        // Método para actualizar la lista de cursos
        fun actualizarCursos(nuevosCursos: List<CursoRequest>) {
            cursos.clear()
            cursos.addAll(nuevosCursos)
            notifyDataSetChanged()
        }
    }
