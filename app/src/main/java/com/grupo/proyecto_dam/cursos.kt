package com.grupo.proyecto_dam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class cursos : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textoPrincipal: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_cursos, container, false)

        // Configurar RecyclerView y el TextView que muestra el mensaje si la lista está vacía
        recyclerView = view.findViewById(R.id.recycler_view_cursos)
        textoPrincipal = view.findViewById(R.id.texto_principal)

        // Datos de ejemplo con la propiedad `nota` como tipo `Int`
        val cursosList = listOf(
            Curso("Matemáticas", "Prof. García", 20),
            Curso("Historia", "Prof. López", 15),
            Curso("Ciencias", "Prof. Fernández", 18)
        )

        // Comprobamos si la lista de cursos está vacía
        if (cursosList.isEmpty()) {
            textoPrincipal.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            textoPrincipal.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            // Configuración del RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            // Pasamos la lista de cursos al adapter
            recyclerView.adapter = CursoAdapter(cursosList, requireContext())
        }

        return view
    }
}
