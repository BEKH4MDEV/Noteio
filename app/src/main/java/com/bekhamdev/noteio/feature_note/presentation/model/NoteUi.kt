package com.bekhamdev.noteio.feature_note.presentation.model

import com.bekhamdev.noteio.feature_note.domain.model.NoteColor

data class NoteUi(
    val id: Long,
    val title: String,
    val content: String,
    val color: NoteColor
)
