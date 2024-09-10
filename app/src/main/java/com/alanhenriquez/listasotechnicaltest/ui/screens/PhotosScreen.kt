package com.alanhenriquez.listasotechnicaltest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alanhenriquez.listasotechnicaltest.helpers.FetchClient
import com.alanhenriquez.listasotechnicaltest.ui.composables.cards.Photo
import com.alanhenriquez.listasotechnicaltest.ui.composables.cards.PhotoCard
import com.alanhenriquez.listasotechnicaltest.ui.theme.ListasoTechnicalTestTheme
import com.google.gson.Gson



//-------------------------------------------------------------------------------------------------
//MAIN COMPOSABLE SCREEN --------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



@Composable
fun PhotosScreen(postId: Int, onBack: () -> Unit) {
    var photos by remember { mutableStateOf<List<Photo>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val fetchClient = FetchClient()

    LaunchedEffect(postId) {
        fetchClient.getRequest("https://jsonplaceholder.typicode.com/posts/$postId/photos") { response ->
            response?.let {
                val photoList = parsePhotos(it)
                photos = photoList
                isLoading = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(photos ?: emptyList()) { photo ->
                        PhotoCard(photo)
                    }
                }
            }
        }

        // Floating Action Button (FAB) para regresar
        FloatingActionButton(
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Alinea el FAB en la esquina inferior derecha
                .padding(16.dp), // Añade padding para separar el FAB del borde
            containerColor = MaterialTheme.colorScheme.primary // Color del FAB
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack, // Icono de flecha hacia atrás
                contentDescription = "Back",
                tint = Color.White // Color del ícono
            )
        }
    }
}



//-------------------------------------------------------------------------------------------------
//UTILS -------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



fun parsePhotos(jsonString: String): List<Photo> {
    return Gson().fromJson(jsonString, Array<Photo>::class.java).toList()
}



//-------------------------------------------------------------------------------------------------
//TEMPORAL PREVIEW --------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------


@Preview(showBackground = true)
@Composable
fun PhotosScreenPreview() {
    ListasoTechnicalTestTheme {
        PhotosScreen(postId = 1, onBack = {})
    }
}