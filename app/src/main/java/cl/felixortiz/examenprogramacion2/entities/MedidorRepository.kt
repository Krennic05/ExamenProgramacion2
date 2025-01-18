package cl.felixortiz.examenprogramacion2.entities

import android.content.Context
import androidx.room.Room
import cl.felixortiz.examenprogramacion2.dao.MedidorDao
import cl.felixortiz.examenprogramacion2.db.AppDatabase
import cl.felixortiz.examenprogramacion2.db.MedidorDaoDbHelperImp

class MedidorRepository(
    private val medidorDao: MedidorDao
) {
    suspend fun obtenerTodos():List<Medidor> = medidorDao.getAll()

    suspend fun agregar(medidor:Medidor) = medidorDao.insertAll(medidor)

    suspend fun contarRegistros():Int = medidorDao.count()

    companion object {
        @Volatile
        private var instance: MedidorRepository? = null

        fun getInstance(contexto:Context):MedidorRepository {
            return getInstanceDSDbHelper(contexto)
        }

        fun getInstanceDSDbHelper(contexto:Context):MedidorRepository {
            return instance ?: synchronized(this) {
                MedidorRepository(
                    MedidorDaoDbHelperImp(contexto)
                )
            }
        }

        fun getInstanceDSRoom(contexto: Context):MedidorRepository {
            return instance ?: synchronized(this) {
                MedidorRepository(
                    Room.databaseBuilder(
                        contexto.applicationContext,
                        AppDatabase::class.java,
                        "medidor.db"
                    ).build().MedidorDao()
                ).also {
                    instance = it
                }
            }
        }
    }
}