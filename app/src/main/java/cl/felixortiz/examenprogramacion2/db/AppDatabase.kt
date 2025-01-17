package cl.felixortiz.examenprogramacion2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.felixortiz.examenprogramacion2.dao.MedidorDao
import cl.felixortiz.examenprogramacion2.entities.Medidor

@Database(entities = [Medidor::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MedidorDao(): MedidorDao
}