package brawijaya.putradewan.bigio.ui.screens.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import brawijaya.putradewan.bigio.data.models.Character
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CharacterDetailContent(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(200.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Character image of ${character.name}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        StatusBadge(status = character.status)

        Spacer(modifier = Modifier.height(24.dp))

        CharacterInfoCard(
            title = "Basic Information",
            details = listOf(
                "Species" to character.species,
                "Gender" to character.gender,
                "Status" to character.status
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        CharacterInfoCard(
            title = "Location Information",
            details = listOf(
                "Origin" to character.origin.name,
                "Current Location" to character.location.name
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        CharacterInfoCard(
            title = "Additional Information",
            details = listOf(
                "Character ID" to character.id.toString(),
                "Episodes Count" to character.episode.size.toString()
            )
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}