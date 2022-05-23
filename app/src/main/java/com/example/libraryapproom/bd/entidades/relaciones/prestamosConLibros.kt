package com.example.libraryapproom.bd.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.PrestamosEntity

data class prestamosConLibros (
    @Embedded val libro: LibrosModels,
    @Relation(
        parentColumn = "ID",
        entityColumn = "book_id"
    )
    val prestamo: List<PrestamosEntity>
)