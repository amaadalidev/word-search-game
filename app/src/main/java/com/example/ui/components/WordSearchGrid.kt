package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine.GeneratedBoard
import com.example.model.ActiveSelection
import com.example.model.CellCoordinate
import com.example.model.WordSearchPalette

@Composable
fun WordSearchGrid(
    board: GeneratedBoard,
    activeSelection: ActiveSelection?,
    hintLetter: CellCoordinate?,
    onSelectionStart: (CellCoordinate) -> Unit,
    onSelectionMove: (CellCoordinate) -> Unit,
    onSelectionEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gridSize = board.size

    val infiniteTransition = rememberInfiniteTransition(label = "hintPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .shadow(12.dp, RoundedCornerShape(22.dp))
            .testTag("word_search_grid_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(22.dp))
                .background(Color(0xFFFCFCFD))
        ) {
            val boardPixelWidth = constraints.maxWidth.toFloat()
            val cellSizePx = boardPixelWidth / gridSize
            val cellWidthDp = maxWidth / gridSize
            val cellHeightDp = maxHeight / gridSize

            // Gesture detection layer
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(gridSize) {
                        awaitEachGesture {
                            val down = awaitFirstDown(requireUnconsumed = false)
                            val startCol = (down.position.x / cellSizePx).toInt().coerceIn(0, gridSize - 1)
                            val startRow = (down.position.y / cellSizePx).toInt().coerceIn(0, gridSize - 1)
                            onSelectionStart(CellCoordinate(startRow, startCol))

                            while (true) {
                                val event = awaitPointerEvent()
                                val change = event.changes.firstOrNull() ?: break
                                if (!change.pressed) {
                                    break
                                }
                                val currCol = (change.position.x / cellSizePx).toInt().coerceIn(0, gridSize - 1)
                                val currRow = (change.position.y / cellSizePx).toInt().coerceIn(0, gridSize - 1)
                                onSelectionMove(CellCoordinate(currRow, currCol))
                            }
                            onSelectionEnd()
                        }
                    }
            ) {
                // Canvas layer for found words, active drag capsule, and hints
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val capsuleWidth = cellSizePx * 0.76f

                    // 1. Draw Found Words Capsules
                    for (placed in board.placedWords) {
                        if (placed.isFound) {
                            val capsuleColor = WordSearchPalette.capsules[placed.colorIndex % WordSearchPalette.capsules.size]
                            val startOffset = Offset(
                                (placed.startCol + 0.5f) * cellSizePx,
                                (placed.startRow + 0.5f) * cellSizePx
                            )
                            val endOffset = Offset(
                                (placed.endCol + 0.5f) * cellSizePx,
                                (placed.endRow + 0.5f) * cellSizePx
                            )

                            // Soft pill fill capsule behind the letters
                            drawLine(
                                color = capsuleColor.fill,
                                start = startOffset,
                                end = endOffset,
                                strokeWidth = capsuleWidth,
                                cap = StrokeCap.Round
                            )
                        }
                    }

                    // 2. Draw Active Drag Selection Capsule
                    if (activeSelection != null && activeSelection.cells.isNotEmpty()) {
                        val activeColor = Color(0x6600B0FF)

                        val startOffset = Offset(
                            (activeSelection.start.col + 0.5f) * cellSizePx,
                            (activeSelection.start.row + 0.5f) * cellSizePx
                        )
                        val lastCell = activeSelection.cells.last()
                        val currentOffset = Offset(
                            (lastCell.col + 0.5f) * cellSizePx,
                            (lastCell.row + 0.5f) * cellSizePx
                        )

                        drawLine(
                            color = activeColor,
                            start = startOffset,
                            end = currentOffset,
                            strokeWidth = capsuleWidth,
                            cap = StrokeCap.Round
                        )
                    }

                    // 3. Draw Hint Pulsing Aura if active
                    if (hintLetter != null) {
                        val hintCenter = Offset(
                            (hintLetter.col + 0.5f) * cellSizePx,
                            (hintLetter.row + 0.5f) * cellSizePx
                        )
                        drawCircle(
                            color = Color(0xFFFFD600).copy(alpha = 0.45f),
                            radius = (cellSizePx * 0.45f) * pulseScale,
                            center = hintCenter
                        )
                        drawCircle(
                            color = Color(0xFFFFAB00),
                            radius = cellSizePx * 0.42f,
                            center = hintCenter,
                            style = Stroke(width = 3.dp.toPx())
                        )
                    }
                }

                // Letters Grid overlay
                for (row in 0 until gridSize) {
                    for (col in 0 until gridSize) {
                        val char = board.grid[row][col]

                        val isCellInFoundWord = board.placedWords.any { it.isFound && it.getCells().contains(CellCoordinate(row, col)) }
                        val isCellActive = activeSelection?.cells?.contains(CellCoordinate(row, col)) == true

                        val textColor = when {
                            isCellActive -> Color.White
                            isCellInFoundWord -> Color(0xFF111111)
                            else -> Color(0xFF1E2022)
                        }

                        val cellMinDp = minOf(cellWidthDp, cellHeightDp)
                        val fontSize = when {
                            gridSize <= 5 -> (cellMinDp.value * 0.48f).coerceIn(18f, 28f).sp
                            gridSize <= 6 -> (cellMinDp.value * 0.45f).coerceIn(16f, 24f).sp
                            gridSize == 7 -> (cellMinDp.value * 0.42f).coerceIn(14f, 21f).sp
                            gridSize == 8 -> (cellMinDp.value * 0.40f).coerceIn(13f, 19f).sp
                            else -> (cellMinDp.value * 0.38f).coerceIn(11f, 17f).sp
                        }

                        Box(
                            modifier = Modifier
                                .size(cellWidthDp, cellHeightDp)
                                .offset(x = cellWidthDp * col, y = cellHeightDp * row),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = char.toString(),
                                fontSize = fontSize,
                                fontWeight = FontWeight.ExtraBold,
                                color = textColor,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                    }
                }
            }
        }
    }
}
