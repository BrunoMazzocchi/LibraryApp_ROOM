package com.example.libraryapproom.bd.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.TypesEntity

data class LibroType (
    @Embedded val type : TypesEntity,
    @Relation(
        parentColumn = "type_id",
        entityColumn = "typeID"
    )
    val libros: List<LibrosModels>
)
