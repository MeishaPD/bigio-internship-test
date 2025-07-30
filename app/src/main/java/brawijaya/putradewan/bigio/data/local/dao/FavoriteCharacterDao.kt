package brawijaya.putradewan.bigio.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import brawijaya.putradewan.bigio.data.local.entity.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Query("SELECT * FROM favorite_characters")
    fun getAllFavorites(): Flow<List<FavoriteCharacter>>

    @Query("SELECT * FROM favorite_characters WHERE id = :id")
    suspend fun getFavoriteById(id: Int): FavoriteCharacter?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(character: FavoriteCharacter)

    @Delete
    suspend fun deleteFavorite(character: FavoriteCharacter)

    @Query("DELETE FROM favorite_characters WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)
}