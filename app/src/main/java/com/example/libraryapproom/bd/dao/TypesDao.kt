package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.TypesEntity

@Dao
interface TypesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg types: TypesEntity)

    @Query("SELECT * FROM TblType")
    suspend fun getAllTypes(): List<TypesEntity>

    @Query("SELECT * FROM TblType")
    fun getAllRealData(): LiveData<List<TypesEntity>>
}