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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Difficulty

@Composable
fun DifficultyTabs(
    selectedDifficulty: Difficulty,
    onDifficultySelected: (Difficulty) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Difficulty.values().forEach { diff ->
            val isSelected = diff == selectedDifficulty

            val label = when (diff) {
                Difficulty.EASY -> "EASY"
                Difficulty.MEDIUM -> "MEDIUM"
                Difficulty.HARD -> "HARD"
                Difficulty.PRO -> "👑 PRO"
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(if (isSelected) 6.dp else 2.dp, RoundedCornerShape(22.dp))
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        if (isSelected) Color(0xFFFFD54F)
                        else Color(0xFFFFF8E1).copy(alpha = 0.95f)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFFFFA000) else Color(0xFFFFECB3),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .clickable { onDifficultySelected(diff) }
                    .padding(vertical = 8.dp)
                    .testTag("tab_difficulty_${diff.name.lowercase()}"),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = label,
                        fontSize = if (diff == Difficulty.PRO) 11.sp else 12.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color(0xFF2E1C00) else Color(0xFF5D4037),
                        letterSpacing = 0.5.sp
                    )

                    if (isSelected) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(
                            modifier = Modifier
                                .width(18.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFFE65100))
                        )
                    }
                }
            }
        }
    }
}
