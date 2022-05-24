package com.example.libraryapproom.bd.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.libraryapproom.bd.entidades.*
import com.example.libraryapproom.bd.entidades.vistas.view_books
import com.example.libraryapproom.bd.entidades.vistas.view_borrows

interface MainDataBaseProvider {
    fun librosDao(): LibrosDao
    fun prestamosDao(): PrestamosDao
    fun autoresDao(): AutoresDao
    fun typesDao(): TypesDao
    fun estudiantesDao(): EstudiantesDao
}

@Database(

    entities = [LibrosModels::class, PrestamosEntity::class, AuthorsEntity::class, TypesEntity::class, EstudiantesEntity::class],
    views = [view_books::class, view_borrows::class],
    version = 32

)
abstract class MainBaseDatos : RoomDatabase(),
    MainDataBaseProvider {
    abstract override fun librosDao(): LibrosDao
    abstract override fun prestamosDao(): PrestamosDao
    abstract override fun autoresDao(): AutoresDao
    abstract override fun typesDao(): TypesDao
    abstract override fun estudiantesDao(): EstudiantesDao
    companion object {
        @Volatile
        private var INSTANCE: MainBaseDatos? = null

        fun getDataBase(context: Context): MainBaseDatos {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainBaseDatos::class.java,
                        "main_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}