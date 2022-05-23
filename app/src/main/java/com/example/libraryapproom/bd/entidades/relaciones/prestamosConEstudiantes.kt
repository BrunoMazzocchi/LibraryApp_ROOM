package com.example.libraryapproom.bd.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.libraryapproom.bd.entidades.EstudiantesEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.PrestamosEntity

data class prestamosConEstudiantes (
    @Embedded val estudiante: EstudiantesEntity,
    @Relation(
        parentColumn = "student_id",
        entityColumn = "student_id"
    )
    val prestamo: List<PrestamosEntity>
)