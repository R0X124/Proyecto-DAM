package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.UserRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface UserService {
    @POST("api/usuarios/crear")
    fun agregarUsuario(@Body usuario: UserRequest): Call<ResponseBody> // `Void` porque no esperamos respuesta en el cuerpo
}

