package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.CursoRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CursoProfesorGet {
    @GET("api/cursos/profesor/{profesorid}")
    fun obtenerCursoGet(
        @Path("profesorid") profesorId: Long
    ): Call<List<CursoRequest>>
}