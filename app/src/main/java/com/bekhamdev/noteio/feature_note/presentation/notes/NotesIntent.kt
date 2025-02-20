package com.bekhamdev.noteio.feature_note.presentation.notes

import com.bekhamdev.noteio.core.feature_note.domain.util.NoteOrder
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi

sealed interface NotesIntent {
    data class Order(val noteOrder: NoteOrder): NotesIntent
    data class DeleteNote(val note: NoteUi): NotesIntent
    data object RestoreNote: NotesIntent
    data object ToggleOrderSection: NotesIntent
    data class ClickNote(val note: NoteUi?): NotesIntent
}