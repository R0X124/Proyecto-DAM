package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.EvaluacionRequest
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface CursoNotasPut {

    @POST("api/cursos/{cursoId}/calificar/{alumnoId}/{notaT1}/{examenParcial}/{notaT2}/{examenFinal}")
    fun calificarNotas(
        @Path("cursoId") cursoId: Long,
        @Path("alumnoId") alumnoId: Long,
        @Path("notaT1") notaT1: Double,
        @Path("examenParcial") examenParcial: Double,
        @Path("notaT2") notaT2: Double,
        @Path("examenFinal") examenFinal: Double
    ): Call<EvaluacionRequest>
}