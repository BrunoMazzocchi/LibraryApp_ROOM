package com.example.libraryapproom.bd.entidades.vistas
import androidx.room.DatabaseView


@DatabaseView("SELECT LibrosModels.nombreLibro, LibrosModels.genero, LibrosModels.ID, " +
        "LibrosModels.Paginas, LibrosModels.authorID, LibrosModels.TypeID ," +
        "TblAutores.name AS autorName FROM LibrosModels " +
        //"TblType.name AS typeName FROM LibrosModels " +
        "INNER JOIN TblAutores ON LibrosModels.authorID = TblAutores.autorId  ")//+
        //"JOIN TblType ON LibrosModels.TypeID = TblType.type_id")
data class libroView(
    val ID: Long,
    val nombreLibro: String?,
    val genero: String?,
    val Paginas: String?,
    val authorID: Long,
    val autorName: String?,
    val TypeID: Long
    //val typeName: String?
)
