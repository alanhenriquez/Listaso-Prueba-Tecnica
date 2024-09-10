package com.alanhenriquez.listasotechnicaltest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.alanhenriquez.listasotechnicaltest.helpers.FetchClient
import com.alanhenriquez.listasotechnicaltest.ui.composables.cards.Comment
import com.alanhenriquez.listasotechnicaltest.ui.composables.cards.CommentCard
import com.alanhenriquez.listasotechnicaltest.ui.theme.ListasoTechnicalTestTheme
import com.google.gson.Gson


//-------------------------------------------------------------------------------------------------
//MAIN COMPOSABLE SCREEN --------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



@Composable
fun CommentsScreen(postId: Int, onBack: () -> Unit) {
    var comments by remember { mutableStateOf<List<Comment>?>(null) }
    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) } // Estado para rastrear la carga
    val fetchClient = FetchClient()

    LaunchedEffect(postId) {
        fetchClient.getRequest("https://jsonplaceholder.typicode.com/posts/$postId/comments") { response ->
            response?.let {
                val commentList = parseComments(it)
                comments = commentList
                isLoading = false // Establecemos isLoading en false una vez que los datos estén cargados
            }
        }
    }

    val filteredComments = comments?.filter {
        it.name.contains(searchText, ignoreCase = true) || it.email.contains(searchText, ignoreCase = true) || it.body.contains(searchText, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
                .imePadding()
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                placeholder = { Text("Buscar...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
            )

            Spacer(modifier = Modifier.height(26.dp))

            if (isLoading) {
                // Muestra un indicador de carga mientras se obtienen los datos
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Muestra la lista de comentarios una vez que los datos están cargados
                LazyColumn {
                    items(filteredComments?.size ?: 0) { index ->
                        filteredComments?.get(index)?.let { comment ->
                            CommentCard(
                                comment,
                                index = index
                            )
                            Spacer(modifier = Modifier.height(26.dp))
                        }
                    }
                }
            }
        }

        // Floating action button
        FloatingActionButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}



//-------------------------------------------------------------------------------------------------
//UTILS -------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



fun parseComments(jsonString: String): List<Comment> {
    return Gson().fromJson(jsonString, Array<Comment>::class.java).toList()
}



//-------------------------------------------------------------------------------------------------
//TEMPORAL PREVIEW --------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



@Preview(showBackground = true)
@Composable
fun CommentsScreenPreview() {
    ListasoTechnicalTestTheme {
        CommentsScreen(postId = 1, onBack = {})
    }
}
