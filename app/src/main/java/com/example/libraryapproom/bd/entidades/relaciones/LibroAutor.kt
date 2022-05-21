package com.example.libraryapproom.bd.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.LibrosModels


data class LibroAutor(
    @Embedded val autor : AuthorsEntity,
    @Relation(
        parentColumn = "autor_Id",
        entityColumn = "authorID"
    )
    val libros: List<LibrosModels>

)

