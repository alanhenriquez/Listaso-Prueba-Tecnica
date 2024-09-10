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
import androidx.compose.ui.Alignment
import com.alanhenriquez.listasotechnicaltest.helpers.FetchClient
import com.alanhenriquez.listasotechnicaltest.ui.composables.cards.Post
import com.alanhenriquez.listasotechnicaltest.ui.composables.cards.PostCard
import com.google.gson.Gson



//-------------------------------------------------------------------------------------------------
//MAIN COMPOSABLE SCREEN --------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



@Composable
fun PostsScreen(onNavigateToComments: (Int) -> Unit, onNavigateToPhotos: (Int) -> Unit) {
    var posts by remember { mutableStateOf<List<Post>?>(null) }
    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) } // Nuevo estado para rastrear la carga
    val fetchClient = FetchClient()

    LaunchedEffect(Unit) {
        fetchClient.getRequest("https://jsonplaceholder.typicode.com/posts") { response ->
            response?.let {
                val postList = parsePosts(it)
                posts = postList
                isLoading = false // Establece isLoading en false una vez que los datos estén cargados
            }
        }
    }

    val filteredPosts = posts?.filter {
        it.title.contains(searchText, ignoreCase = true) || it.body.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .imePadding()
    ) {
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
            // Muestra la lista de publicaciones una vez que los datos están cargados
            LazyColumn {
                items(filteredPosts?.size ?: 0) { index ->
                    filteredPosts?.get(index)?.let { post ->
                        PostCard(
                            title = post.title,
                            description = post.body,
                            onCommentsClick = { onNavigateToComments(post.id) },
                            onImagesClick = { onNavigateToPhotos(post.id) }
                        )
                    }
                }
            }
        }
    }
}



//-------------------------------------------------------------------------------------------------
//UTILS -------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



fun parsePosts(jsonString: String): List<Post> {
    return Gson().fromJson(jsonString, Array<Post>::class.java).toList()
}