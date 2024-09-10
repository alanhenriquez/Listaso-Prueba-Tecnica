package com.alanhenriquez.listasotechnicaltest.ui.composables.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alanhenriquez.listasotechnicaltest.R



//-------------------------------------------------------------------------------------------------
//MAIN COMPOSABLE ---------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



@Composable
fun PostCard(title: String, description: String, onCommentsClick: () -> Unit, onImagesClick: () -> Unit ) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = description,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCommentsClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.comment),
                        contentDescription = "Comentarios",
                        tint = Color.Gray
                    )
                }

                IconButton(onClick = onImagesClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_image), // Otro ícono personalizado si lo tienes
                        contentDescription = "Imágenes",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}



//-------------------------------------------------------------------------------------------------
//DATA CLASS MODEL --------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------



data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)