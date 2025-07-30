package brawijaya.putradewan.bigio.ui.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import brawijaya.putradewan.bigio.data.models.Character
import brawijaya.putradewan.bigio.data.repository.CharacterRepository
import brawijaya.putradewan.bigio.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Character>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Character>>> = _uiState.asStateFlow()

    private val _characters = mutableListOf<Character>()
    private var currentPage = 1
    private var hasNextPage = true

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getCharacters(currentPage).fold(
                onSuccess = { response ->
                    _characters.clear()
                    _characters.addAll(response.results)
                    hasNextPage = response.info.next != null
                    _uiState.value = UiState.Success(_characters.toList())
                },
                onFailure = { exception ->
                    _uiState.value = UiState.Error(exception)
                }
            )
        }
    }

    fun loadMoreCharacters() {
        if (!hasNextPage) return

        viewModelScope.launch {
            currentPage++
            repository.getCharacters(currentPage).fold(
                onSuccess = { response ->
                    _characters.addAll(response.results)
                    hasNextPage = response.info.next != null
                    _uiState.value = UiState.Success(_characters.toList())
                },
                onFailure = { exception ->
                    currentPage--
                }
            )
        }
    }

    fun refresh() {
        currentPage = 1
        hasNextPage = true
        loadCharacters()
    }
}