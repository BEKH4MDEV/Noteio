package com.bekhamdev.noteio.feature_note.data.repository

import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository: NoteRepository {
    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note): Long {
        notes.add(note)
        return note.id
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}