package cl.felixortiz.examenprogramacion2.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
//import java.sql.Date
import java.time.LocalDate

@Entity
data class Medidor (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val tipo:String,
    val valor:Int,
    val fecha:LocalDate
)