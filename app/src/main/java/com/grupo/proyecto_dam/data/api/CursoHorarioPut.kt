package com.grupo.proyecto_dam.data.api

import com.grupo.proyecto_dam.data.model.CursoRequest
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface CursoHorarioPut {
    @POST("api/cursos/{cursoId}/agregarHorario/{dia}/{horaInicio}/{horaFin}")
    fun agregarHorario(
        @Path("cursoId") cursoId: Long?,
        @Path("dia") dia: String?,
        @Path("horaInicio") hora_inicio: String?,
        @Path("horaFin") hora_final:String?
    ): Call<CursoRequest>
}