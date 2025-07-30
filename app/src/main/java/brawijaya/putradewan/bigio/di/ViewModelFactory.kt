package brawijaya.putradewan.bigio.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import brawijaya.putradewan.bigio.data.local.database.AppDatabase
import brawijaya.putradewan.bigio.data.repository.CharacterRepositoryImpl
import brawijaya.putradewan.bigio.ui.screens.detail.viewmodel.DetailViewModel
import brawijaya.putradewan.bigio.ui.screens.favorites.viewmodel.FavoritesViewModel
import brawijaya.putradewan.bigio.ui.screens.home.viewmodel.HomeViewModel
import brawijaya.putradewan.bigio.ui.screens.search.viewmodel.SearchViewModel
import kotlin.getValue
import kotlin.jvm.java

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val database by lazy { AppDatabase.getDatabase(context) }
    private val repository by lazy {
        CharacterRepositoryImpl(
            NetworkModule.apiService,
            database.favoriteCharacterDao()
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repository) as T
            DetailViewModel::class.java -> DetailViewModel(repository) as T
            SearchViewModel::class.java -> SearchViewModel(repository) as T
            FavoritesViewModel::class.java -> FavoritesViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}