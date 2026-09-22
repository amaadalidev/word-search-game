package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlacedWord
import com.example.model.ThemeCategory
import com.example.model.WordSearchPalette

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordBankView(
    theme: ThemeCategory,
    placedWords: List<PlacedWord>,
    onWordClick: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val foundCount = placedWords.count { it.isFound }
    val totalCount = placedWords.size

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Category Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                theme.accentColor,
                                theme.accentColor.copy(alpha = 0.85f),
                                theme.accentColor
                            )
                        )
                    )
                    .padding(vertical = 7.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = theme.title.uppercase(),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.22f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "$foundCount/$totalCount FOUND",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }

            // Word bank chips
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                placedWords.forEach { pw ->
                    val isFound = pw.isFound
                    val capsuleColor = WordSearchPalette.capsules[pw.colorIndex % WordSearchPalette.capsules.size]

                    val bgColor by animateColorAsState(
                        targetValue = if (isFound) capsuleColor.fill else Color.Transparent,
                        animationSpec = tween(400),
                        label = "pillBg"
                    )

                    val textColor by animateColorAsState(
                        targetValue = when {
                            isFound -> Color(0xFF111111)
                            else -> Color(0xFF555555)
                        },
                        animationSpec = tween(300),
                        label = "pillText"
                    )

                    val chipBg = if (isFound) bgColor else Color(0xFFF4F6F9)
                    val borderModifier = if (!isFound) {
                        Modifier.border(
                            width = 1.dp,
                            color = Color(0xFFE2E8F0),
                            shape = RoundedCornerShape(16.dp)
                        )
                    } else Modifier

                    val chipFontSize = if (pw.word.length >= 9) 11.sp else if (pw.word.length >= 7) 12.sp else 13.sp
                    val iconSize = if (pw.word.length >= 8) 11.dp else 12.dp

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .then(borderModifier)
                            .background(chipBg)
                            .clickable { onWordClick?.invoke(pw.word) }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("word_chip_${pw.word}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Listen to ${pw.word}",
                                tint = if (isFound) textColor.copy(alpha = 0.85f) else Color(0xFF78909C),
                                modifier = Modifier.size(iconSize)
                            )
                            Text(
                                text = pw.word,
                                fontSize = chipFontSize,
                                fontWeight = if (isFound) FontWeight.ExtraBold else FontWeight.Bold,
                                color = textColor,
                                textDecoration = if (isFound) TextDecoration.None else null,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
