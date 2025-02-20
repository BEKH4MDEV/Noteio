package com.bekhamdev.noteio.feature_note.presentation.model

import androidx.compose.ui.graphics.Color

data class NoteUi(
    val id: Long,
    val title: String,
    val content: String,
    val color: Color
)
