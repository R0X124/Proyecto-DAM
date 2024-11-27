package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.CursoRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CursoService  {
    @GET("api/cursos")
    fun obtenerCursos(): Call<List<CursoRequest>>
}
interface CursoServiceSend{
    @POST("api/cursos")
    fun agregarCursos(@Body curso: CursoRequest): Call<ResponseBody>
}




