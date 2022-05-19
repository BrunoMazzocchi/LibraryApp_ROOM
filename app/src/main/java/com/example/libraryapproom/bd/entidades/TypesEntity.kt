package com.example.libraryapproom.bd.entidades

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TblType")
data class TypesEntity(
    @PrimaryKey(autoGenerate = true)
    val type_id:Int,

    @ColumnInfo(name = "name")
    val name: String
): Parcelable
