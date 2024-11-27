package com.grupo.proyecto_dam.data.model

data class CursoRequest(
    val id: Long?,
    val nombre: String?,
    val descripcion: String?,
    val profesor: UserRequest?,
    val dia: String?,
    val horaInicio: String?,
    val horaFin: String?
)
