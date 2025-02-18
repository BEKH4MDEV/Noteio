package com.bekhamdev.noteio.feature_note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bekhamdev.noteio.feature_note.data.local.dao.NoteDao
import com.bekhamdev.noteio.feature_note.data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
    companion object {
        const val DATABASE_NAME = "noteio"
    }
}