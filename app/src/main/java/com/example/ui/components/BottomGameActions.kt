package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundManager

@Composable
fun BottomGameActions(
    themeColor: Color,
    onStarClick: () -> Unit,
    onHintClick: () -> Unit,
    onRevealClick: () -> Unit,
    onRestartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonGradient = listOf(
        themeColor,
        themeColor.copy(alpha = 0.85f),
        Color(0xFF880E4F).takeIf { themeColor.red > 0.6f && themeColor.blue > 0.4f } ?: themeColor
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Star Button (Themes / Progress)
        CircularActionButton(
            icon = Icons.Default.Star,
            contentDescription = "Themes",
            gradient = buttonGradient,
            onClick = onStarClick,
            testTag = "action_star"
        )

        // Magic Wand (Hint - First letter)
        CircularActionButton(
            icon = Icons.Default.AutoAwesome,
            contentDescription = "Magic Wand Hint",
            gradient = buttonGradient,
            costBadge = "20",
            onClick = onHintClick,
            testTag = "action_hint"
        )

        // Magnifier (Reveal word)
        CircularActionButton(
            icon = Icons.Default.Search,
            contentDescription = "Magnifier Reveal",
            gradient = buttonGradient,
            costBadge = "50",
            onClick = onRevealClick,
            testTag = "action_reveal"
        )

        // Refresh / Restart
        CircularActionButton(
            icon = Icons.Default.Refresh,
            contentDescription = "Restart",
            gradient = buttonGradient,
            onClick = onRestartClick,
            testTag = "action_restart"
        )
    }
}

@Composable
private fun CircularActionButton(
    icon: ImageVector,
    contentDescription: String,
    gradient: List<Color>,
    costBadge: String? = null,
    onClick: () -> Unit,
    testTag: String
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.testTag(testTag),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(8.dp, CircleShape)
                .clip(CircleShape)
                .background(Brush.verticalGradient(gradient))
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0.8f), Color.White.copy(alpha = 0.2f))
                    ),
                    shape = CircleShape
                )
                .clickable {
                    SoundManager.getInstance(context).playClick()
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        if (costBadge != null) {
            Box(
                modifier = Modifier
                    .offset(x = 18.dp, y = (-4).dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFB300))
                    .border(1.dp, Color.White, CircleShape)
                    .padding(horizontal = 5.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$costBadge🪙",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF3E2723)
                )
            }
        }
    }
}
