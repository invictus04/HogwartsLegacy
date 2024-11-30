package com.example.hogwartslegacy.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.hogwartslegacy.data.HogwartsDataItem
import com.example.hogwartslegacy.viewModel.CharacterDetails
import com.example.hogwartslegacy.viewModel.HogwartsViewModel

@Composable
fun ActorName(hogwartsViewModel: HogwartsViewModel = viewModel()) {
    val nameState by hogwartsViewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when{
            nameState.loading -> CircularProgressIndicator()
            nameState.error != null -> Text(text = "${nameState.error}", textAlign = TextAlign.Center)
            else -> ActorsList(characters = nameState.characters,hogwartsViewModel=hogwartsViewModel)
        }
    }
}

@Composable
fun ActorsList(characters: List<HogwartsDataItem>, hogwartsViewModel: HogwartsViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       items(characters.size) { ind ->
          hogwartsViewModel.getCharactersDetails(ind)?.let { characterDetails ->
              ItemCard(characterDetails)
          }
       }

    }
}

@Composable
fun ActorsData(allName: HogwartsDataItem) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = allName.name)
    }
}


@Composable
fun ItemCard(characterDetails: CharacterDetails) {
    Card(
        modifier = Modifier.fillMaxHeight().padding(10.dp)
        ) {
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AsyncImage(model = characterDetails.image, contentDescription = null,
                    modifier = Modifier.size(450.dp).padding(top = 20.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = characterDetails.actor,
                    fontWeight = FontWeight.Bold)
                Text(text = characterDetails.name)
            }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemCardPreview() {
    ItemCard(
        characterDetails = CharacterDetails(
            name = "Harry",
            image = "https://example.com/harry.jpg",
            actor = "Daniel"
        )

    )
}
