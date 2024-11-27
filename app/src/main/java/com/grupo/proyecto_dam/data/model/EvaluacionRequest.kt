package com.grupo.proyecto_dam.data.model

data class EvaluacionRequest(
    val id: Long?,
    val curso: CursoRequest,
    val alumno: UserRequest,
    val notaT1:Double?,
    val examenParcial: Double,
    val notaT2: Double,
    val examenFinal: Double,
    val promedioFinal: Double
)
