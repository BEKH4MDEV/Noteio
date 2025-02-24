package com.bekhamdev.noteio.core.feature_note.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun tintedIconPainter(
    imageVector: ImageVector,
    tintColor: Color
): Painter {
    val basePainter = rememberVectorPainter(
        image = imageVector
    )
    return object : Painter() {
        override val intrinsicSize: Size
            get() = basePainter.intrinsicSize

        override fun DrawScope.onDraw() {
            with(basePainter) {
                draw(
                    size = size,
                    colorFilter = ColorFilter.tint(tintColor, BlendMode.SrcIn)
                )
            }
        }
    }
}