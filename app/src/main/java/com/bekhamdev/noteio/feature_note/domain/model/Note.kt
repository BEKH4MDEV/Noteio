package com.bekhamdev.noteio.feature_note.domain.model

import androidx.compose.ui.graphics.Color

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: NoteColor,
)

data class NoteColor(
    val index: Int,
    val color: Color,
)