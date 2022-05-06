package com.example.libraryapproom.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

data class Prestamos (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "student_id")
    val student_id: Int,
    @ColumnInfo(name = "book_id")
    val book_id: Int,
    @ColumnInfo(name = "taken_date")
    val taken_date: Date,
    @ColumnInfo(name = "brought_date")
    val brought_date: Date,

    )