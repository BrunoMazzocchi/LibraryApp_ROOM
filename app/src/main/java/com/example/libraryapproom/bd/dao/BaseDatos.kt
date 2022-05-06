package com.example.libraryapproom.bd.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.libraryapproom.bd.entidades.LibrosModels

interface MainDataBaseProvider {
    fun librosDao(): LibrosDao

}
@Database(
    entities = [LibrosModels::class],
    version = 1
)
abstract class MainBaseDatos : RoomDatabase(),
    MainDataBaseProvider {
    abstract override fun librosDao(): LibrosDao
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