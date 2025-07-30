package brawijaya.putradewan.bigio.ui.screens.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import brawijaya.putradewan.bigio.data.models.Character
import brawijaya.putradewan.bigio.data.repository.CharacterRepository
import brawijaya.putradewan.bigio.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Character>>(UiState.Loading)
    val uiState: StateFlow<UiState<Character>> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            _isFavorite.value = repository.isFavorite(id)

            repository.getCharacter(id).fold(
                onSuccess = { character ->
                    _uiState.value = UiState.Success(character)
                },
                onFailure = { exception ->
                    _uiState.value = UiState.Error(exception)
                }
            )
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                repository.removeFromFavorites(character.id)
                _isFavorite.value = false
            } else {
                repository.addToFavorites(character)
                _isFavorite.value = true
            }
        }
    }
}