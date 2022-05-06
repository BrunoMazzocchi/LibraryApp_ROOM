package com.example.libraryapproom.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity (tableName = "TblPrestamos")
data class PrestamosEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "estudiante")
    val student_id: String,

    @ColumnInfo(name = "libro")
    val book_id: String,

    @ColumnInfo(name = "fechaRetiro")
    val taken_date: String,

    @ColumnInfo(name = "fechaEntrega")
    val brought_date: String
    ):Parcelable