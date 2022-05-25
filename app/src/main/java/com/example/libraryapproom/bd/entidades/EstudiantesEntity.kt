package com.example.libraryapproom.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "TblEstudiantes")
data class EstudiantesEntity(

    @PrimaryKey(autoGenerate = true)
    val student_id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "surname")
    val surname: String?,

    @ColumnInfo(name = "date_of_birth")
    val date_of_birth: String?,

    @ColumnInfo(name = "gender")
    val gender: String?,

    @ColumnInfo(name = "classroom")
    val classroom: String?,

    @ColumnInfo(name = "point")
    val point: Int?
): Parcelable
