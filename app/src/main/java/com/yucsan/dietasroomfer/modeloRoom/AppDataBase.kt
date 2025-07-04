package com.yucsan.dietasroomfer.modeloRoom


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [
        ComponenteDieta::class,
        ComponenteDietaRelacion::class // Agregamos la relación entre componentes
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alimentoDao(): ComponenteDietaDao // Nombre más descriptivo del DAO

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "alimentos2_db"
                )
                    .fallbackToDestructiveMigration() // Permite actualizar la base sin problemas si hay cambios en la estructura
                    .build().also { instancia = it }
            }
        }
    }
}
