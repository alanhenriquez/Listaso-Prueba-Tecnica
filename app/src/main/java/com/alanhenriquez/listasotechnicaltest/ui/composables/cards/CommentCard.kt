package com.alanhenriquez.listasotechnicaltest.ui.composables.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



//-------------------------------------------------------------------------------------------------
//MAIN COMPOSABLE ---------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



@Composable
fun CommentCard(comment: Comment, index: Int) {
    val alignment = if (index % 2 == 0) Alignment.BottomStart else Alignment.BottomEnd

    Box(contentAlignment = alignment, modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(vertical = 4.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = comment.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = comment.body, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

    Box(contentAlignment = alignment, modifier = Modifier.fillMaxWidth()) {
        Text(text = comment.email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}



//-------------------------------------------------------------------------------------------------
//DATA CLASS MODEL --------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)