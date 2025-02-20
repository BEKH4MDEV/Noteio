package com.bekhamdev.noteio.feature_note.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bekhamdev.noteio.ui.theme.BabyBlue
import com.bekhamdev.noteio.ui.theme.LightGreen
import com.bekhamdev.noteio.ui.theme.RedOrange
import com.bekhamdev.noteio.ui.theme.RedPink
import com.bekhamdev.noteio.ui.theme.Violet

@Entity(
    tableName = "note"
)
data class NoteEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
    val colorIndex: Int,
)
