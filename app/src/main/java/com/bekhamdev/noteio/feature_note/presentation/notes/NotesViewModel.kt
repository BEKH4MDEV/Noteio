package com.bekhamdev.noteio.feature_note.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekhamdev.noteio.core.feature_note.domain.util.NoteOrder
import com.bekhamdev.noteio.core.feature_note.domain.util.onSuccess
import com.bekhamdev.noteio.feature_note.domain.mapper.toNoteUi
import com.bekhamdev.noteio.feature_note.domain.useCase.NoteUseCases
import com.bekhamdev.noteio.feature_note.presentation.mapper.toNote
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state
        .onStart {
            getNotes(_state.value.noteOrder)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    private var recentlyDeletedNote: NoteUi? = null
    private var getNotesJob: Job? = null

    fun onIntent(intent: NotesIntent) {
        when(intent) {
            is NotesIntent.Order -> {
                if (_state.value.noteOrder::class != intent.noteOrder::class ||
                    _state.value.noteOrder.currentOrderType() != intent.noteOrder.currentOrderType()) {
                    getNotes(intent.noteOrder)
                }
            }
            is NotesIntent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(intent.note.toNote())
                    recentlyDeletedNote = intent.note
                }
            }
            NotesIntent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases
                        .addNote(recentlyDeletedNote?.toNote() ?: return@launch)
                        .onSuccess {
                            recentlyDeletedNote = null
                        }
                }
            }
            NotesIntent.ToggleOrderSection -> {
                _state.update {
                    it.copy(
                        isOrderSectionVisible = !it.isOrderSectionVisible
                    )
                }
            }

            is NotesIntent.ClickNote -> {
                _state.update {
                    it.copy(
                        selectedNote = intent.note
                    )
                }
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .map { notes ->
                notes.map { it.toNoteUi() }
            }
            .onEach { notes ->
                _state.update {
                    it.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}