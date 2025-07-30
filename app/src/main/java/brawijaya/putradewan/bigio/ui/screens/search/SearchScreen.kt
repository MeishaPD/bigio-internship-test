package brawijaya.putradewan.bigio.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import brawijaya.putradewan.bigio.data.models.Character
import brawijaya.putradewan.bigio.di.ViewModelFactory
import brawijaya.putradewan.bigio.ui.UiState
import brawijaya.putradewan.bigio.ui.navigation.Screen
import brawijaya.putradewan.bigio.ui.screens.home.components.CharacterItem
import brawijaya.putradewan.bigio.ui.screens.search.components.FiltersSection
import brawijaya.putradewan.bigio.ui.screens.search.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchFilters by viewModel.searchFilters.collectAsState()

    var showFilters by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.navigate(Screen.Home.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Home,
                            contentDescription = "Home"
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate(Screen.Search.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate(Screen.Favorites.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = "Favorite"
                        )
                    }
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Search Characters")
                },
                actions = {
                    IconButton(
                        onClick = { showFilters = !showFilters }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = "Toggle Filters"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                placeholder = { Text("Search characters...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateSearchQuery("") }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            if (showFilters) {
                FiltersSection(
                    searchFilters = searchFilters,
                    onFiltersChanged = { viewModel.updateSearchFilters(it) },
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (searchFilters.hasActiveFilters()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    searchFilters.status?.let { status ->
                        item {
                            FilterChip(
                                onClick = { viewModel.updateSearchFilters(searchFilters.copy(status = null)) },
                                label = { Text("Status: $status") },
                                selected = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = "Remove filter"
                                    )
                                }
                            )
                        }
                    }
                    searchFilters.species?.let { species ->
                        item {
                            FilterChip(
                                onClick = { viewModel.updateSearchFilters(searchFilters.copy(species = null)) },
                                label = { Text("Species: $species") },
                                selected = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = "Remove filter"
                                    )
                                }
                            )
                        }
                    }
                    searchFilters.type?.let { type ->
                        item {
                            FilterChip(
                                onClick = { viewModel.updateSearchFilters(searchFilters.copy(type = null)) },
                                label = { Text("Type: $type") },
                                selected = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = "Remove filter"
                                    )
                                }
                            )
                        }
                    }
                    searchFilters.gender?.let { gender ->
                        item {
                            FilterChip(
                                onClick = { viewModel.updateSearchFilters(searchFilters.copy(gender = null)) },
                                label = { Text("Gender: $gender") },
                                selected = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = "Remove filter"
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (uiState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is UiState.Success -> {
                        val characters = (uiState as UiState.Success<List<Character>>).data

                        if (searchQuery.isEmpty() && !searchFilters.hasActiveFilters()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = null,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                                Text(
                                    text = "Search for Rick and Morty characters",
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else if (characters.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "No characters found with current search criteria",
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(characters) { character ->
                                    CharacterItem(
                                        character = character,
                                        onClick = {
                                            navController.navigate(Screen.Detail.createRoute(character.id))
                                        }
                                    )
                                }
                                item {
                                    Button(
                                        onClick = { viewModel.loadMoreSearchResults() },
                                        modifier = Modifier
                                            .padding(vertical = 16.dp)
                                    ) {
                                        Text("Load More")
                                    }
                                }
                            }
                        }
                    }

                    is UiState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Error: ${(uiState as UiState.Error).exception.message}")
                            Button(
                                onClick = { viewModel.retrySearch() }
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

