package com.bekhamdev.noteio.feature_note.presentation.addEdit

import androidx.compose.ui.graphics.Color

sealed interface AddEditIntent {
    data class SaveNote(
        val id: Long?,
        val title: String,
        val content: String,
        val color: Color,
    ): AddEditIntent

    data class ChangeColor(val color: Color): AddEditIntent
}