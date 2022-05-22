package com.example.libraryapproom.bd.entidades.relaciones

import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.TypesEntity

data class librosConType(
    @Embedded val type: TypesEntity,
    @Relation(
        parentColumn = "type_id",
        entityColumn = "type_id"
    )
    val book: List<LibrosModels>
)