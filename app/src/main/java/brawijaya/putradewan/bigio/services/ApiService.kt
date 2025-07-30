package brawijaya.putradewan.bigio.services

import brawijaya.putradewan.bigio.data.models.ApiResponse
import brawijaya.putradewan.bigio.data.models.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1
    ): ApiResponse<Character>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Character

    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null,
        @Query("page") page: Int = 1
    ): ApiResponse<Character>
}