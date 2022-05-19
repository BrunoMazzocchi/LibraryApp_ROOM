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
    ):Parcelable