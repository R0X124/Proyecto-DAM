package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.UserRequest
import retrofit2.Call
import retrofit2.http.GET

interface UserGet {
    @GET("api/usuarios/todos")
    fun obtenerUsuarios(): Call<List<UserRequest>>
}