package com.grupo.proyecto_dam.data.model

data class UserRequest(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String,
    val rol: String
)
