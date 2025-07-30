package brawijaya.putradewan.bigio.ui.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SearchFilters(
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null
) {
    fun hasActiveFilters(): Boolean {
        return status != null || species != null || type != null || gender != null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersSection(
    searchFilters: SearchFilters,
    onFiltersChanged: (SearchFilters) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Filters")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterDropdown(
                label = "Status",
                value = searchFilters.status,
                options = listOf("alive", "dead", "unknown"),
                onValueChange = { onFiltersChanged(searchFilters.copy(status = it)) },
                modifier = Modifier.weight(1f)
            )

            FilterDropdown(
                label = "Gender",
                value = searchFilters.gender,
                options = listOf("female", "male", "genderless", "unknown"),
                onValueChange = { onFiltersChanged(searchFilters.copy(gender = it)) },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchFilters.species ?: "",
                onValueChange = {
                    onFiltersChanged(searchFilters.copy(species = it.takeIf { it.isNotBlank() }))
                },
                label = { Text("Species") },
                placeholder = { Text("e.g., Human") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = searchFilters.type ?: "",
                onValueChange = {
                    onFiltersChanged(searchFilters.copy(type = it.takeIf { it.isNotBlank() }))
                },
                label = { Text("Type") },
                placeholder = { Text("e.g., Genetic experiment") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Button(
            onClick = { onFiltersChanged(SearchFilters()) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear All Filters")
        }
    }
}