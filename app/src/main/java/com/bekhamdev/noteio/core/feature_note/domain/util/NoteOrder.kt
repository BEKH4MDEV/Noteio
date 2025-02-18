package com.bekhamdev.noteio.core.feature_note.domain.util

sealed interface NoteOrder {
    data class Title(val orderType: OrderType): NoteOrder
    data class Date(val orderType: OrderType): NoteOrder
    data class Color(val orderType: OrderType): NoteOrder
}