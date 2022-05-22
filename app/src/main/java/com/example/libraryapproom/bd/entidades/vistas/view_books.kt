package com.example.libraryapproom.bd.entidades.vistas


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseView("Select " +
        "b.ID," +
        "b.nombreLibro," +
        "b.Paginas," +
        "b.authorID," +
        "b.TypeID," +
        "b.point," +
        "t.name as genero," +
        "a.name as autor " +
        "from LibrosModels as b " +
        "inner join TblType as t on t.type_id=b.TypeID " +
        "inner join TblAutores as a on a.autorId=b.authorID")
data class view_books(
    val ID: Int,
    @ColumnInfo(name = "nombreLibro")
    val nombreLibro: String?,
    @ColumnInfo(name = "Paginas")
    val Paginas: String?,
    @ColumnInfo(name = "authorID")
    val author_ID: Int,
    @ColumnInfo(name = "autor")
    val autorName: String?,
    @ColumnInfo(name = "TypeID")
    val Type_ID: Int,
    @ColumnInfo(name = "genero")
    val typeName: String?,
    @ColumnInfo(name = "point")
    val point: Int?

): Parcelable