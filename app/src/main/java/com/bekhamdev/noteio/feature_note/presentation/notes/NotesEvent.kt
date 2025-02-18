package com.bekhamdev.noteio.feature_note.presentation.notes

sealed interface NotesEvent {
    data class Error(val message: String): NotesEvent
}