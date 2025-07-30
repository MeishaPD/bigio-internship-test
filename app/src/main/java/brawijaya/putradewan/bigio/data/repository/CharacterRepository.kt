package brawijaya.putradewan.bigio.data.repository

import brawijaya.putradewan.bigio.data.local.dao.FavoriteCharacterDao
import brawijaya.putradewan.bigio.data.local.entity.FavoriteCharacter
import brawijaya.putradewan.bigio.data.models.ApiResponse
import brawijaya.putradewan.bigio.data.models.Character
import brawijaya.putradewan.bigio.services.ApiService
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(page: Int): Result<ApiResponse<Character>>
    suspend fun getCharacter(id: Int): Result<Character>
    suspend fun searchCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null,
        page: Int = 1
    ): Result<ApiResponse<Character>>
    fun getAllFavorites(): Flow<List<FavoriteCharacter>>
    suspend fun addToFavorites(character: Character)
    suspend fun removeFromFavorites(characterId: Int)
    suspend fun isFavorite(characterId: Int): Boolean
}

class CharacterRepositoryImpl(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteCharacterDao
): CharacterRepository {

    override suspend fun getCharacters(page: Int): Result<ApiResponse<Character>> {
        return try {
            val response = apiService.getCharacters(page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharacter(id: Int): Result<Character> {
        return try {
            val character = apiService.getCharacter(id)
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?,
        page: Int
    ): Result<ApiResponse<Character>> {
        return try {
            val response = apiService.searchCharacters(
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender,
                page = page
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllFavorites(): Flow<List<FavoriteCharacter>> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun addToFavorites(character: Character) {
        val favoriteCharacter = FavoriteCharacter(
            id = character.id,
            name = character.name,
            species = character.species,
            gender = character.gender,
            origin = character.origin.name,
            location = character.location.name,
            image = character.image
        )
        favoriteDao.insertFavorite(favoriteCharacter)
    }

    override suspend fun removeFromFavorites(characterId: Int) {
        favoriteDao.deleteFavoriteById(characterId)
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return favoriteDao.getFavoriteById(characterId) != null
    }
}