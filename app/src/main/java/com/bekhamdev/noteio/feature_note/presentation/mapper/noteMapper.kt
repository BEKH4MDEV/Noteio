package com.bekhamdev.noteio.feature_note.presentation.mapper

import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi

fun NoteUi.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = System.currentTimeMillis(),
        color = color
    )
}