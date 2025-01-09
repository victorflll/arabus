import androidx.room.*
import com.example.arabus.repository.internal.entities.Bus

@Dao
interface BusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bus: Bus)

    @Query("SELECT * FROM bus WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<Bus>

    @Query("SELECT * FROM bus WHERE id = :busId")
    suspend fun getById(busId: Int): Bus?

    @Delete
    suspend fun delete(bus: Bus)
}
