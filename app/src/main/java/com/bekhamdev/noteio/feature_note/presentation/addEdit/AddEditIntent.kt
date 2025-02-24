package com.bekhamdev.noteio.feature_note.presentation.addEdit

import androidx.compose.ui.graphics.Color
import com.bekhamdev.noteio.core.feature_note.presentation.util.ExitType

sealed interface AddEditIntent {
    data class SaveNote(
        val id: Long?,
        val title: String,
        val content: String,
        val timestamp: Long? = null,
        val color: Color,
        val exitType: ExitType = ExitType.SAVE
    ): AddEditIntent

    data class ChangeColor(val color: Color): AddEditIntent
}