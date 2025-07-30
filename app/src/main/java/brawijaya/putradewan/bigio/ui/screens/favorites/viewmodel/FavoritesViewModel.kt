package brawijaya.putradewan.bigio.ui.screens.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import brawijaya.putradewan.bigio.data.local.entity.FavoriteCharacter
import brawijaya.putradewan.bigio.data.repository.CharacterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    val favorites: StateFlow<List<FavoriteCharacter>> = repository.getAllFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeFromFavorites(characterId: Int) {
        viewModelScope.launch {
            repository.removeFromFavorites(characterId)
        }
    }
}