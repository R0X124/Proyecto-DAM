package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.UserRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CursoAlumnoGet {
    @GET("api/cursos/{cursoId}/alumnos")
    fun alumnosPorCurso(
        @Path("cursoId") cursoId: Long
    ): Call<List<UserRequest>>
}