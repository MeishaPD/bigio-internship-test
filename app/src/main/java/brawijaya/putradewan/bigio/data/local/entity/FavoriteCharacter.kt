package brawijaya.putradewan.bigio.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_characters")
data class FavoriteCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String
)