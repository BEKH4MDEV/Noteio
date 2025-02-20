package com.bekhamdev.noteio.feature_note.presentation.addEdit

sealed interface AddEditEvent {
    data class Error(val message: String): AddEditEvent
    data object Success: AddEditEvent
}