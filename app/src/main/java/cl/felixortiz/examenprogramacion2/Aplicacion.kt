package cl.felixortiz.examenprogramacion2

import android.app.Application
import androidx.room.Room
import cl.felixortiz.examenprogramacion2.dao.MedidorDao
import cl.felixortiz.examenprogramacion2.db.AppDatabase

class Aplicacion : Application() {

    val db by lazy { Room.databaseBuilder(
        this, AppDatabase::class.java,"AppDatabase"
    ).build()}
    val medidorDao by lazy { db.MedidorDao()}

}
