package brawijaya.putradewan.bigio.ui.screens.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import brawijaya.putradewan.bigio.data.models.Character
import brawijaya.putradewan.bigio.data.repository.CharacterRepository
import brawijaya.putradewan.bigio.ui.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Character>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<Character>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = mutableListOf<Character>()
    private var currentPage = 1
    private var hasNextPage = true

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _uiState.value = UiState.Success(emptyList())
            return
        }

        viewModelScope.launch {
            delay(500)
            if (_searchQuery.value == query) {
                searchCharacters(query)
            }
        }
    }

    private fun searchCharacters(query: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            currentPage = 1
            hasNextPage = true

            repository.searchCharacters(query, currentPage).fold(
                onSuccess = { response ->
                    _searchResults.clear()
                    _searchResults.addAll(response.results)
                    hasNextPage = response.info.next != null
                    _uiState.value = UiState.Success(_searchResults.toList())
                },
                onFailure = { exception ->
                    _uiState.value = UiState.Error(exception)
                }
            )
        }
    }

    fun loadMoreSearchResults() {
        if (!hasNextPage || _searchQuery.value.isBlank()) return

        viewModelScope.launch {
            currentPage++
            repository.searchCharacters(_searchQuery.value, currentPage).fold(
                onSuccess = { response ->
                    _searchResults.addAll(response.results)
                    hasNextPage = response.info.next != null
                    _uiState.value = UiState.Success(_searchResults.toList())
                },
                onFailure = { exception ->
                    currentPage--
                }
            )
        }
    }
}