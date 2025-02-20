package com.bekhamdev.noteio.feature_note.domain.model

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val timestamp: Long,
    val colorIndex: Int,
)