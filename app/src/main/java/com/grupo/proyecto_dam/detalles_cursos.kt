package com.grupo.proyecto_dam

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class detalles_cursos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_cursos)

        // Referencias a los campos de notas
        val notaT1 = findViewById<EditText>(R.id.nota_t1)
        val notaEP = findViewById<EditText>(R.id.nota_ep)
        val notaT2 = findViewById<EditText>(R.id.nota_t2)
        val notaEF = findViewById<EditText>(R.id.nota_ef)

        // Referencias a los botones y al TextView del promedio final
        val btnSimularPromedio = findViewById<Button>(R.id.btn_simular_promedio)
        val btnRegresar = findViewById<Button>(R.id.btn_regresar)
        val promedioFinal = findViewById<TextView>(R.id.txt_promedio_final)

        // Inicializar valores predeterminados para las notas
        val valoresPredeterminados = "00"

        // Función para restaurar las notas a los valores predeterminados
        fun restaurarNotas() {
            notaT1.setText(valoresPredeterminados)
            notaEP.setText(valoresPredeterminados)
            notaT2.setText(valoresPredeterminados)
            notaEF.setText(valoresPredeterminados)
            promedioFinal.text = valoresPredeterminados

            // Deshabilitar campos de notas
            notaT1.isEnabled = false
            notaEP.isEnabled = false
            notaT2.isEnabled = false
            notaEF.isEnabled = false
        }

        // Función para configurar el filtro de entrada en los EditText para limitar valores entre 0 y 20
        fun setNotaFilter(editText: EditText) {
            val filter = InputFilter { source, start, end, dest, dstart, dend ->
                // Eliminar caracteres no numéricos
                val result = source.toString().toIntOrNull()

                // Verificar si el resultado es un número entero dentro del rango permitido (0-20)
                if (result != null) {
                    if (result in 0..20) {
                        null  // Permitir si el número está en el rango de 0 a 20
                    } else {
                        ""  // No permitir números fuera del rango 0-20
                    }
                } else {
                    ""  // Si no es un número entero, no permitirlo
                }
            }

            // Asignar el filtro al EditText
            editText.filters = arrayOf(filter)
        }

        // Aplicar el filtro a los campos de notas
        setNotaFilter(notaT1)
        setNotaFilter(notaEP)
        setNotaFilter(notaT2)
        setNotaFilter(notaEF)

        // Habilitar campos y calcular el promedio al presionar el botón "Simular promedio"
        btnSimularPromedio.setOnClickListener {
            // Habilitar los campos de nota
            notaT1.isEnabled = true
            notaEP.isEnabled = true
            notaT2.isEnabled = true
            notaEF.isEnabled = true

            // Calcular el promedio
            val notas = listOf(
                notaT1.text.toString().toIntOrNull() ?: 0,  // Usamos enteros
                notaEP.text.toString().toIntOrNull() ?: 0,
                notaT2.text.toString().toIntOrNull() ?: 0,
                notaEF.text.toString().toIntOrNull() ?: 0
            )

            val pesos = listOf(10, 20, 30, 40) // Pesos en enteros

            // Calculamos el promedio ponderado
            val promedio = notas.zip(pesos).sumOf { (nota, peso) -> (nota * peso) }
            val sumaPesos = pesos.sum() // Sumar los pesos para la normalización

            // Promedio ponderado, redondeado al entero más cercano
            val promedioFinalCalculado = (promedio.toDouble() / sumaPesos) * 100
            val promedioRedondeado = promedioFinalCalculado.roundToInt() // Redondear al entero más cercano

            // Mostrar el promedio calculado
            promedioFinal.text = promedioRedondeado.toString()
        }

        // Función del botón "Regresar" para navegar a MenuEstudiantesActivity y cargar el fragmento fragment_cursos
        btnRegresar.setOnClickListener {
            restaurarNotas()
            // Navegar a activity_menu_estudiantes y cargar el fragmento fragment_cursos.xml
            val intent = Intent(this, menuEstudiante::class.java)
            startActivity(intent)
        }
    }
}