package com.bekhamdev.noteio.feature_note.domain.useCase

import com.bekhamdev.noteio.feature_note.domain.useCase.note.AddNoteUseCase
import com.bekhamdev.noteio.feature_note.domain.useCase.note.DeleteNoteUseCase
import com.bekhamdev.noteio.feature_note.domain.useCase.note.GetNoteUseCase
import com.bekhamdev.noteio.feature_note.domain.useCase.note.GetNotesUseCase
import javax.inject.Inject

data class NoteUseCases @Inject constructor(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
    val getNote: GetNoteUseCase
)
