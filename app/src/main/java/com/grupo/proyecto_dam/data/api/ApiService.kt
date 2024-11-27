package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.UserRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/usuarios/login/{correo}/{contrasena}")
    fun login(
        @Path("correo") email: String,
        @Path("contrasena") password: String
    ): Call<String>
}

interface ApiServiceId{
    @GET("api/usuarios/{correo}")
    fun obtenerUsuarioPorCorreo(@Path("correo") correo: String): Call<UserRequest>
}