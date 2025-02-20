package com.bekhamdev.noteio.core.feature_note.domain.util

sealed interface NoteOrder {
    data class Title(val orderType: OrderType): NoteOrder
    data class Date(val orderType: OrderType): NoteOrder
    data class Color(val orderType: OrderType): NoteOrder

    fun currentOrderType(): OrderType {
        return when (this) {
            is Title -> orderType
            is Date -> orderType
            is Color -> orderType
        }
    }

    fun copyWithOrderType(orderType: OrderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}