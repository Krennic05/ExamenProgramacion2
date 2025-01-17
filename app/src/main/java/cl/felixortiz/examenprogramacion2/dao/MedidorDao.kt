package cl.felixortiz.examenprogramacion2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cl.felixortiz.examenprogramacion2.entities.Medidor

@Dao
interface MedidorDao {

    @Query("SELECT * FROM medidor ORDER BY id")
    fun getAll(): List<Medidor>

    @Insert
    fun insert(medidor: Medidor )

    @Delete
    fun delete(medidor: Medidor)

}

