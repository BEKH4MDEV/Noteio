package com.bekhamdev.noteio.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi
import com.bekhamdev.noteio.ui.theme.surfaceBrightDark
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: NoteUi,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {
    val delete = SwipeAction(
        icon = rememberVectorPainter(
            image = Icons.Default.Delete
        ),
        background = Color.Red.copy(
            alpha = .7f
        ),
        onSwipe = onDeleteClick
    )

    SwipeableActionsBox(
        endActions = listOf(
            delete
        ),
        swipeThreshold = 150.dp,
        backgroundUntilSwipeThreshold = Color.Transparent
    ) {
        Box(
            modifier = modifier
        ) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val clipPath = Path().apply {
                    lineTo(
                        x = size.width - cutCornerSize.toPx(),
                        y = 0f
                    )
                    lineTo(
                        x = size.width,
                        y = cutCornerSize.toPx()
                    )
                    lineTo(
                        x = size.width,
                        y = size.height
                    )
                    lineTo(
                        x = 0f,
                        y = size.height
                    )
                    close()
                }

                clipPath(clipPath) {
                    drawRoundRect(
                        color = note.color,
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                    drawRoundRect(
                        color = Color(
                            ColorUtils.blendARGB(note.color.toArgb(), 0x000000, 0.2f)
                        ),
                        topLeft = Offset(
                            x = size.width - cutCornerSize.toPx(),
                            y = -100f
                        ),
                        size = Size(
                            width = cutCornerSize.toPx() + 100f,
                            height = cutCornerSize.toPx() + 100f
                        ),
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(end = 32.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = surfaceBrightDark.copy(
                        alpha = .9f
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.titleMedium,
                    color = surfaceBrightDark.copy(
                        alpha = .9f
                    ),
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}