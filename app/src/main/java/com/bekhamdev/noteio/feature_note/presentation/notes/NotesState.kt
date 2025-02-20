package com.bekhamdev.noteio.feature_note.presentation.notes

import com.bekhamdev.noteio.core.feature_note.domain.util.NoteOrder
import com.bekhamdev.noteio.core.feature_note.domain.util.OrderType
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi

data class NotesState(
    val notes: List<NoteUi> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val selectedNote: NoteUi? = null
)
