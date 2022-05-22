package com.example.libraryapproom.bd.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.LibrosModels


data class librosConAutores(
    @Embedded val autores: AuthorsEntity,
    @Relation(
        parentColumn = "autorId",
        entityColumn = "autorId"
    )
    val book: List<LibrosModels>
)