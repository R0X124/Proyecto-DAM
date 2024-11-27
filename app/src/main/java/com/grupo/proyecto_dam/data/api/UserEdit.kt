package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.UserRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserEdit {
    @PUT("api/usuarios/editar/{id}")
    fun actualizarUsuario(
        @Path("id") id: Long,
        @Body usuario: UserRequest
    ): Call<ResponseBody>
}