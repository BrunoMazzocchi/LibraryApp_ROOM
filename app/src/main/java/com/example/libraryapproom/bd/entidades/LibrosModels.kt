package com.example.libraryapproom.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class todosLibros(
    val books: List<LibrosModels>
)
@Parcelize
@Entity(tableName = "LibrosModels")
data class LibrosModels(
    @PrimaryKey(autoGenerate = true)
    val ID:Int = 0,
    @ColumnInfo(name = "nombreLibro")
    val nombreLibro: String,
    @ColumnInfo(name = "Paginas")
    val Paginas: String,
    @ColumnInfo(name = "authorID")
    val authorID: Int?,
    @ColumnInfo(name = "TypeID")
    val typeID: Int?,
    @ColumnInfo(name = "point")
    val point: Int?,



    ): Parcelable
