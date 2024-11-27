package com.grupo.proyecto_dam.data.api

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface UserDelete {
    @DELETE("api/usuarios/eliminar/{id}")
    fun eliminarUsuario(@Path("id") id: Long): Call<Void>
}