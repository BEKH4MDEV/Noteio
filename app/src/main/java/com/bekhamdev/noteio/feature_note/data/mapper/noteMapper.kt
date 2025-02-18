package com.bekhamdev.noteio.feature_note.data.mapper

import com.bekhamdev.noteio.core.feature_note.domain.util.noteColors
import com.bekhamdev.noteio.feature_note.data.local.entity.NoteEntity
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.domain.model.NoteColor

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = NoteColor(
            index = color,
            color = noteColors[color]
        )
    )
}