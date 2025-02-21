package com.bekhamdev.noteio.feature_note.presentation.mapper

import com.bekhamdev.noteio.core.feature_note.domain.util.noteColors
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi

fun NoteUi.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp ?: System.currentTimeMillis(),
        colorIndex = noteColors.indexOf(color)
    )
}