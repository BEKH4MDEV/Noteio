package com.bekhamdev.noteio.feature_note.domain.useCase.note

import com.bekhamdev.noteio.core.feature_note.domain.util.NoteOrder
import com.bekhamdev.noteio.core.feature_note.domain.util.OrderType
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.bekhamdev.noteio.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            withContext(Dispatchers.Default) {
                when (noteOrder) {
                    is NoteOrder.Title -> {
                        if (noteOrder.orderType == OrderType.Ascending) {
                            notes.sortedBy { it.title.lowercase() }
                        } else {
                            notes.sortedByDescending { it.title.lowercase() }
                        }
                    }

                    is NoteOrder.Date -> {
                        if (noteOrder.orderType == OrderType.Ascending) {
                            notes.sortedBy { it.timestamp }
                        } else {
                            notes.sortedByDescending { it.timestamp }
                        }
                    }

                    is NoteOrder.Color -> {
                        if (noteOrder.orderType == OrderType.Ascending) {
                            notes.sortedBy { it.colorIndex }
                        } else {
                            notes.sortedByDescending { it.colorIndex }
                        }
                    }
                }
            }
        }
    }
}