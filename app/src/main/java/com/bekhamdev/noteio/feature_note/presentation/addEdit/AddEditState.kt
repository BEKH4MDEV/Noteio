package com.bekhamdev.noteio.feature_note.presentation.addEdit

import androidx.compose.ui.graphics.Color
import com.bekhamdev.noteio.core.feature_note.domain.util.noteColors

data class AddEditState(
    val color: Color = noteColors.first(),
    val changed: Boolean = false
)
