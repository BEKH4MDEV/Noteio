package com.bekhamdev.noteio.feature_note.domain.mapper

import com.bekhamdev.noteio.feature_note.data.local.entity.NoteEntity
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color.index
    )
}

fun Note.toNoteUi(): NoteUi {
    return NoteUi(
        id = id,
        title = title,
        content = content,
        color = color
    )
}