package com.alanhenriquez.listasotechnicaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.alanhenriquez.listasotechnicaltest.ui.screens.CommentsScreen
import com.alanhenriquez.listasotechnicaltest.ui.screens.PhotosScreen
import com.alanhenriquez.listasotechnicaltest.ui.screens.PostsScreen
import com.alanhenriquez.listasotechnicaltest.ui.theme.ListasoTechnicalTestTheme



//-------------------------------------------------------------------------------------------------
//MAIN ACTIVITY -----------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListasoTechnicalTestTheme {
                Navigation()
            }
        }
    }
}


//-------------------------------------------------------------------------------------------------
//NAVIGATOR ---------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "posts") {
        composable("posts") {
            PostsScreen(onNavigateToComments = { postId ->
                navController.navigate("comments/$postId")
            }, onNavigateToPhotos = { postId ->
                navController.navigate("photos/$postId")
            })
        }
        composable("comments/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toInt() ?: 0
            CommentsScreen(postId = postId, onBack = {
                navController.popBackStack()
            })
        }
        composable("photos/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toInt() ?: 0
            PhotosScreen(postId = postId, onBack = {
                navController.popBackStack()
            })
        }
    }
}

