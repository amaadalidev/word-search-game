package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ThemeIcon

@Composable
fun ThemeCategoryIcon(
    icon: ThemeIcon,
    tint: Color,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        when (icon) {
            ThemeIcon.VOCABULARY -> RedBookAZIcon(size = size)
            ThemeIcon.ANIMALS -> AnimalsPawIcon(size = size)
            ThemeIcon.COLORS -> ColorPaletteIcon(size = size)
            ThemeIcon.CITIES -> CitySkylineIcon(size = size)
            ThemeIcon.NATURE -> NatureMountainIcon(size = size)
            ThemeIcon.HOUSE -> HouseCottageIcon(size = size)
            ThemeIcon.ADJECTIVES -> AlphabetBlocksIcon(size = size)
            ThemeIcon.TV_SHOWS -> RetroTvIcon(size = size)
            ThemeIcon.COUNTRIES -> EarthGlobeIcon(size = size)
            ThemeIcon.MONUMENTS -> MonumentColosseumIcon(size = size)
            ThemeIcon.ACTORS -> ActorTheaterIcon(size = size)
            ThemeIcon.WRITERS -> WritersQuillIcon(size = size)
            ThemeIcon.HISTORY -> HistoryHourglassIcon(size = size)
            ThemeIcon.SPACE -> SpaceRocketIcon(size = size)
            ThemeIcon.FOOD -> FoodBurgerIcon(size = size)
            ThemeIcon.SPORTS -> SportsBallIcon(size = size)
            ThemeIcon.SCIENCE -> ScienceFlaskIcon(size = size)
        }
    }
}

/** 1. Red 3D A-Z Book (Oxford English Dictionary) */
@Composable
fun RedBookAZIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.9f)
            .rotate(-6f)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp, topEnd = 10.dp, bottomEnd = 10.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFFB71C1C), Color(0xFFE53935), Color(0xFFFF5252))
                )
            )
            .border(1.dp, Color(0xFFFF8A80), RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp, topEnd = 10.dp, bottomEnd = 10.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Spine accent
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(5.dp)
                .fillMaxSize()
                .background(Color(0xFF880E4F))
        )
        // White page edge on right
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(4.dp)
                .fillMaxSize()
                .background(Color(0xFFFFF9C4))
        )
        // A-Z Text
        Text(
            text = "A-Z",
            fontSize = (size.value * 0.32f).sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            letterSpacing = 0.5.sp
        )
    }
}

/** 2. 3D Golden/Orange Paw (Animals) */
@Composable
fun AnimalsPawIcon(size: Dp) {
    Column(
        modifier = Modifier.size(size * 0.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 4 Toes
        Row(
            horizontalArrangement = Arrangement.spacedBy(size * 0.05f),
            verticalAlignment = Alignment.Bottom
        ) {
            val toeSize = size * 0.18f
            listOf(-4.dp, 0.dp, 0.dp, -4.dp).forEach { yOffset ->
                Box(
                    modifier = Modifier
                        .offset(y = yOffset)
                        .size(toeSize)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFFFFD54F), Color(0xFFFF9800), Color(0xFFF57C00))
                            )
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(size * 0.04f))
        // Palm Pad
        Box(
            modifier = Modifier
                .size(width = size * 0.48f, height = size * 0.40f)
                .shadow(3.dp, RoundedCornerShape(50))
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFFFE082), Color(0xFFFFB300), Color(0xFFE65100))
                    )
                )
        )
    }
}

/** 3. Artist Palette & Brush (Colors) */
@Composable
fun ColorPaletteIcon(size: Dp) {
    Box(
        modifier = Modifier.size(size * 0.9f),
        contentAlignment = Alignment.Center
    ) {
        // Wooden Palette
        Box(
            modifier = Modifier
                .size(size * 0.82f)
                .shadow(3.dp, RoundedCornerShape(45))
                .clip(RoundedCornerShape(45))
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFFFFE082), Color(0xFFFFB74D), Color(0xFFFFA726))
                    )
                )
                .border(1.5.dp, Color(0xFFFFD54F), RoundedCornerShape(45))
        ) {
            // Paint dots
            Box(modifier = Modifier.size(size * 0.16f).offset(x = size * 0.18f, y = size * 0.12f).clip(CircleShape).background(Color(0xFFE53935)))
            Box(modifier = Modifier.size(size * 0.16f).offset(x = size * 0.45f, y = size * 0.10f).clip(CircleShape).background(Color(0xFF1E88E5)))
            Box(modifier = Modifier.size(size * 0.16f).offset(x = size * 0.12f, y = size * 0.38f).clip(CircleShape).background(Color(0xFF43A047)))
            Box(modifier = Modifier.size(size * 0.16f).offset(x = size * 0.28f, y = size * 0.52f).clip(CircleShape).background(Color(0xFFFFD600)))
            // Palette thumb hole
            Box(modifier = Modifier.size(size * 0.18f).offset(x = size * 0.52f, y = size * 0.48f).clip(CircleShape).background(Color(0xFFFFF9C4)))
        }
        // Paintbrush leaning
        Box(
            modifier = Modifier
                .size(width = size * 0.16f, height = size * 0.80f)
                .rotate(42f)
                .offset(x = size * 0.12f, y = -size * 0.05f)
                .shadow(3.dp, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0D47A1), Color(0xFF1976D2), Color(0xFFB0BEC5), Color(0xFF263238))
                    )
                )
        )
    }
}

/** 4. City Skyline (Cities) */
@Composable
fun CitySkylineIcon(size: Dp) {
    Box(
        modifier = Modifier.size(size * 0.9f),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Base ground
        Box(
            modifier = Modifier
                .size(width = size * 0.82f, height = size * 0.16f)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFFFE082))
        )
        // Skyline buildings
        Row(
            horizontalArrangement = Arrangement.spacedBy(size * 0.02f),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.offset(y = -size * 0.04f)
        ) {
            // Building 1 (Cyan)
            Box(
                modifier = Modifier
                    .size(width = size * 0.18f, height = size * 0.40f)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFF4FC3F7), Color(0xFF0288D1))))
            )
            // Building 2 (Tall Blue)
            Box(
                modifier = Modifier
                    .size(width = size * 0.22f, height = size * 0.72f)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFF64B5F6), Color(0xFF1565C0))))
            ) {
                // Spire
                Box(
                    modifier = Modifier
                        .size(width = 2.dp, height = size * 0.16f)
                        .align(Alignment.TopCenter)
                        .background(Color(0xFFFFD54F))
                )
            }
            // Building 3 (Yellow/Orange)
            Box(
                modifier = Modifier
                    .size(width = size * 0.20f, height = size * 0.55f)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFFFFD54F), Color(0xFFFF9800))))
            )
            // Building 4 (Blue)
            Box(
                modifier = Modifier
                    .size(width = size * 0.16f, height = size * 0.35f)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFF29B6F6), Color(0xFF0277BD))))
            )
        }
    }
}

/** 5. Snowy Mountain & River (Nature) */
@Composable
fun NatureMountainIcon(size: Dp) {
    Box(
        modifier = Modifier.size(size * 0.9f),
        contentAlignment = Alignment.Center
    ) {
        // Mountain Backdrop
        Box(
            modifier = Modifier
                .size(size * 0.75f)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFE1F5FE), Color(0xFF81D4FA), Color(0xFF29B6F6), Color(0xFF0288D1))
                    )
                )
        ) {
            // Snowy peak
            Box(
                modifier = Modifier
                    .size(width = size * 0.45f, height = size * 0.26f)
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 6.dp, bottomEnd = 6.dp))
                    .background(Color.White)
            )
        }
        // Trees and River
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = size * 0.85f, height = size * 0.35f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            // Pine tree left
            Box(modifier = Modifier.size(size * 0.26f).clip(CircleShape).background(Color(0xFF2E7D32)))
            // River center
            Box(
                modifier = Modifier
                    .size(width = size * 0.28f, height = size * 0.24f)
                    .clip(RoundedCornerShape(50))
                    .background(Brush.verticalGradient(listOf(Color(0xFF80D8FF), Color(0xFF0091EA))))
            )
            // Pine tree right
            Box(modifier = Modifier.size(size * 0.30f).clip(CircleShape).background(Color(0xFF388E3C)))
        }
    }
}

/** 6. Cozy Cottage / House (House) */
@Composable
fun HouseCottageIcon(size: Dp) {
    Box(
        modifier = Modifier.size(size * 0.9f),
        contentAlignment = Alignment.Center
    ) {
        // Red Roof
        Box(
            modifier = Modifier
                .size(width = size * 0.75f, height = size * 0.38f)
                .offset(y = -size * 0.16f)
                .shadow(3.dp, RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = 4.dp, bottomEnd = 4.dp))
                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = 4.dp, bottomEnd = 4.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFFFF5252), Color(0xFFD32F2F), Color(0xFFB71C1C))))
        ) {
            // Chimney
            Box(
                modifier = Modifier
                    .size(width = size * 0.12f, height = size * 0.18f)
                    .offset(x = size * 0.50f, y = -size * 0.05f)
                    .background(Color(0xFF795548))
            )
        }
        // Cream Wall Body
        Box(
            modifier = Modifier
                .size(width = size * 0.56f, height = size * 0.44f)
                .offset(y = size * 0.12f)
                .shadow(2.dp, RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .background(Color(0xFFFFF8E1))
                .border(1.dp, Color(0xFFFFECB3), RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
        ) {
            // Door
            Box(
                modifier = Modifier
                    .size(width = size * 0.16f, height = size * 0.28f)
                    .align(Alignment.BottomStart)
                    .offset(x = size * 0.08f)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(Color(0xFF6D4C41))
            )
            // Window
            Box(
                modifier = Modifier
                    .size(size * 0.18f)
                    .align(Alignment.TopEnd)
                    .offset(x = -size * 0.06f, y = size * 0.06f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF4FC3F7))
                    .border(1.dp, Color(0xFFFFD54F), RoundedCornerShape(4.dp))
            )
        }
    }
}

/** 7. 3D Alphabet Blocks (Adjectives) */
@Composable
fun AlphabetBlocksIcon(size: Dp) {
    Box(
        modifier = Modifier.size(size * 0.9f),
        contentAlignment = Alignment.Center
    ) {
        // Block A (Top Purple)
        Box(
            modifier = Modifier
                .size(size * 0.38f)
                .offset(y = -size * 0.22f)
                .shadow(3.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFFBA68C8), Color(0xFF7B1FA2), Color(0xFF4A148C))))
                .border(1.dp, Color(0xFFE1BEE7), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("A", fontSize = (size.value * 0.22f).sp, fontWeight = FontWeight.Black, color = Color.White)
        }
        // Block B (Bottom Left Orange)
        Box(
            modifier = Modifier
                .size(size * 0.38f)
                .offset(x = -size * 0.20f, y = size * 0.18f)
                .shadow(3.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFFFFD54F), Color(0xFFFF9800), Color(0xFFE65100))))
                .border(1.dp, Color(0xFFFFF176), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("B", fontSize = (size.value * 0.22f).sp, fontWeight = FontWeight.Black, color = Color.White)
        }
        // Block C (Bottom Right Blue)
        Box(
            modifier = Modifier
                .size(size * 0.38f)
                .offset(x = size * 0.20f, y = size * 0.18f)
                .shadow(3.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFF42A5F5), Color(0xFF1976D2), Color(0xFF0D47A1))))
                .border(1.dp, Color(0xFF90CAF9), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("C", fontSize = (size.value * 0.22f).sp, fontWeight = FontWeight.Black, color = Color.White)
        }
    }
}

/** 8. Retro TV (TV Shows) */
@Composable
fun RetroTvIcon(size: Dp) {
    Box(
        modifier = Modifier.size(size * 0.9f),
        contentAlignment = Alignment.Center
    ) {
        // Antenna
        Row(
            modifier = Modifier.offset(y = -size * 0.34f),
            horizontalArrangement = Arrangement.spacedBy(size * 0.10f)
        ) {
            Box(modifier = Modifier.size(width = 2.dp, height = size * 0.20f).rotate(-25f).background(Color(0xFF424242)))
            Box(modifier = Modifier.size(width = 2.dp, height = size * 0.20f).rotate(25f).background(Color(0xFF424242)))
        }
        // Red TV Frame
        Box(
            modifier = Modifier
                .size(width = size * 0.74f, height = size * 0.58f)
                .shadow(4.dp, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFFFF5252), Color(0xFFE53935), Color(0xFFC62828))))
                .border(1.5.dp, Color(0xFFFF8A80), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Blue Screen with Play button
            Box(
                modifier = Modifier
                    .size(width = size * 0.50f, height = size * 0.40f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFF42A5F5), Color(0xFF1565C0), Color(0xFF0D47A1)))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(size * 0.26f)
                )
            }
        }
    }
}

/** 9. 3D Earth Globe (Countries) */
@Composable
fun EarthGlobeIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(Color(0xFF64B5F6), Color(0xFF1E88E5), Color(0xFF0D47A1))
                )
            )
            .border(1.5.dp, Color(0xFF90CAF9), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Continents (Green shapes)
        Box(
            modifier = Modifier
                .size(size * 0.42f)
                .offset(x = -size * 0.12f, y = -size * 0.10f)
                .clip(RoundedCornerShape(40))
                .background(Color(0xFF43A047))
        )
        Box(
            modifier = Modifier
                .size(size * 0.35f)
                .offset(x = size * 0.15f, y = size * 0.14f)
                .clip(RoundedCornerShape(40))
                .background(Color(0xFF2E7D32))
        )
    }
}

/** 10. Monument / Colosseum (Monuments) */
@Composable
fun MonumentColosseumIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFFFFD54F), Color(0xFFFFB300), Color(0xFFE65100)))),
        contentAlignment = Alignment.Center
    ) {
        Text("🏛️", fontSize = (size.value * 0.45f).sp)
    }
}

/** 11. Actor & Film (Actors) */
@Composable
fun ActorTheaterIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFFFF80AB), Color(0xFFF50057), Color(0xFFC51162)))),
        contentAlignment = Alignment.Center
    ) {
        Text("🎭", fontSize = (size.value * 0.45f).sp)
    }
}

/** 12. Writers Quill (Writers) */
@Composable
fun WritersQuillIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFFFFCC80), Color(0xFFFB8C00), Color(0xFFE65100)))),
        contentAlignment = Alignment.Center
    ) {
        Text("✒️", fontSize = (size.value * 0.45f).sp)
    }
}

/** 13. History (History) */
@Composable
fun HistoryHourglassIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFFCE93D8), Color(0xFF8E24AA), Color(0xFF4A148C)))),
        contentAlignment = Alignment.Center
    ) {
        Text("📜", fontSize = (size.value * 0.45f).sp)
    }
}

/** 14. Space Rocket (Space) */
@Composable
fun SpaceRocketIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF80DEEA), Color(0xFF00ACC1), Color(0xFF006064)))),
        contentAlignment = Alignment.Center
    ) {
        Text("🚀", fontSize = (size.value * 0.45f).sp)
    }
}

/** 15. Food (Food) */
@Composable
fun FoodBurgerIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFFA5D6A7), Color(0xFF43A047), Color(0xFF1B5E20)))),
        contentAlignment = Alignment.Center
    ) {
        Text("🍔", fontSize = (size.value * 0.45f).sp)
    }
}

/** 16. Sports (Sports) */
@Composable
fun SportsBallIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF90CAF9), Color(0xFF1E88E5), Color(0xFF0D47A1)))),
        contentAlignment = Alignment.Center
    ) {
        Text("⚽", fontSize = (size.value * 0.45f).sp)
    }
}

/** 17. Science Flask (Science) */
@Composable
fun ScienceFlaskIcon(size: Dp) {
    Box(
        modifier = Modifier
            .size(size * 0.82f)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFFB39DDB), Color(0xFF673AB7), Color(0xFF311B92)))),
        contentAlignment = Alignment.Center
    ) {
        Text("🧪", fontSize = (size.value * 0.45f).sp)
    }
}
