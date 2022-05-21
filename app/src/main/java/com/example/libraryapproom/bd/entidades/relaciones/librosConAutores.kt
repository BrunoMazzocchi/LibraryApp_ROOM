package com.example.libraryapproom.bd.entidades.relaciones

import androidx.room.Query
import androidx.room.Transaction


@Transaction
@Query("Select * from tblautores")
fun obtenerLibrosYAutores(): List<LibroAutor> {
    return obtenerLibrosYAutores()
}
