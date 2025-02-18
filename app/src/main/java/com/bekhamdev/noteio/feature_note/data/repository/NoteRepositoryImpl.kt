package com.bekhamdev.noteio.feature_note.data.repository

import com.bekhamdev.noteio.feature_note.data.local.NoteDatabase
import com.bekhamdev.noteio.feature_note.data.mapper.toNote
import com.bekhamdev.noteio.feature_note.domain.mapper.toNoteEntity
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val database: NoteDatabase
): NoteRepository {
    private val dao = database.noteDao

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map {
            it.map { entity ->
                entity.toNote()
            }
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return dao.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toNoteEntity())
    }
}