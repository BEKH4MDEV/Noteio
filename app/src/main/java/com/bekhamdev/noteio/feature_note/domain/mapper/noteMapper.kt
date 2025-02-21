package com.bekhamdev.noteio.feature_note.domain.mapper

import com.bekhamdev.noteio.core.feature_note.domain.util.noteColors
import com.bekhamdev.noteio.feature_note.data.local.entity.NoteEntity
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        colorIndex = colorIndex
    )
}

fun Note.toNoteUi(): NoteUi {
    return NoteUi(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = noteColors.getOrElse(colorIndex) { noteColors.first() }
    )
}