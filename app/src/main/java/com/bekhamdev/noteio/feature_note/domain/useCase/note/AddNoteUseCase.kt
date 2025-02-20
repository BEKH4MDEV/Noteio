package com.bekhamdev.noteio.feature_note.domain.useCase.note

import com.bekhamdev.noteio.core.feature_note.domain.util.Result
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Result<Unit, String> {
        if (note.title.isBlank() || note.content.isBlank()) {
            return Result.Error("Empty fields are not allowed")
        }
        return Result.Success(repository.insertNote(note))
    }
}