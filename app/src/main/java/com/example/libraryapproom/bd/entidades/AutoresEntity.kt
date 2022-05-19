package com.example.libraryapproom.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TblAutores")
data class AuthorsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "autorId")
    val autor_Id:Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val suname:String,
): Parcelable