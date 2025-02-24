package com.bekhamdev.noteio.feature_note.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bekhamdev.noteio.feature_note.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity?

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Upsert
    suspend fun insertNote(note: NoteEntity): Long
}