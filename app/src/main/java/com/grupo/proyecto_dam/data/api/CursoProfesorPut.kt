package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.CursoRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CursoProfesorPut {
    @POST("api/cursos/{cursoId}/asignarProfesor/{profesorId}")
    suspend fun asignarProfesor(
        @Path("cursoId") cursoId: Long,
        @Path("profesorId") profesorId: Long
    ): Response<CursoRequest>
}

interface CursoSinProfesor{
    @GET("api/cursos/sinProfesor")
    fun obtenercursoSinProfesor(): Call<List<CursoRequest>>
}