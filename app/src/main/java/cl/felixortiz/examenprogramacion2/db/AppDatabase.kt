package cl.felixortiz.examenprogramacion2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.felixortiz.examenprogramacion2.dao.MedidorDao
import cl.felixortiz.examenprogramacion2.entities.Medidor

import androidx.room.TypeConverters

@Database(entities = [Medidor::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MedidorDao(): MedidorDao
}