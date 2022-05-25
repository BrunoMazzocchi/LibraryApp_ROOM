package com.example.libraryapproom.bd.entidades.vistas

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseView("Select " +
        "p.id," +
        "p.brought_date," +
        "p.taken_date," +
        "p.book_id," +
        "p.student_id," +
        "l.nombreLibro as book_name," +
        "e.name as student_name " +
        "from TblPrestamos as p " +
        "inner join LibrosModels as l on l.ID = p.book_id " +
        "inner join TblEstudiantes as e on e.student_id = p.student_id")
data class view_borrows (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "student_name")
    val student_name: String?,

    @ColumnInfo(name = "student_id")
    val student_id: Int?,

    @ColumnInfo(name = "book_name")
    val book_name: String?,

    @ColumnInfo(name = "book_id")
    val book_id: Int?,

    @ColumnInfo(name = "taken_date")
    val taken_date: String,

    @ColumnInfo(name = "brought_date")
    val brought_date: String
    ): Parcelable