package com.bekhamdev.noteio.feature_note.domain.useCase.note

import com.bekhamdev.noteio.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Long) = repository.getNoteById(id)
}