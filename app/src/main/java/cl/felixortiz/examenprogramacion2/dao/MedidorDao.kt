package cl.felixortiz.examenprogramacion2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.felixortiz.examenprogramacion2.entities.Medidor

@Dao
interface MedidorDao {

    @Query("SELECT * FROM medidor ORDER BY id")
    suspend fun getAll(): List<Medidor>

    @Query("SELECT * FROM medidor WHERE id = :id")
    suspend fun findById(id:Int):Medidor?

    @Query("SELECT COUNT(*) FROM medidor")
    suspend fun count():Int

    @Update
    suspend fun update(medidor:Medidor)

    @Delete
    suspend fun delete(medidor: Medidor)

    @Insert
    suspend fun insertAll(vararg medidores: Medidor)
}

