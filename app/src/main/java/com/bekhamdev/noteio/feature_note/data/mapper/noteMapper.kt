package com.bekhamdev.noteio.feature_note.data.mapper

import com.bekhamdev.noteio.feature_note.data.local.entity.NoteEntity
import com.bekhamdev.noteio.feature_note.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        colorIndex = colorIndex
    )
}