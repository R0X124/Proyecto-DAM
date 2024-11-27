package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.CursoRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CursoAlumnoPut {
    @POST("/api/cursos/{cursoId}/inscripciones")
    fun inscribirAlumnosEnCurso(
        @Path("cursoId") cursoId: Long,
        @Body alumnosIds: List<Long>
    ): Call<CursoRequest>
}