package com.bekhamdev.noteio.feature_note.presentation.addEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekhamdev.noteio.core.feature_note.domain.util.onError
import com.bekhamdev.noteio.core.feature_note.domain.util.onSuccess
import com.bekhamdev.noteio.core.feature_note.presentation.util.ExitType
import com.bekhamdev.noteio.feature_note.domain.useCase.NoteUseCases
import com.bekhamdev.noteio.feature_note.presentation.mapper.toNote
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {
    private val _state = MutableStateFlow(AddEditState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<AddEditEvent>()
    val event = _event.asSharedFlow()

    private var savedId: Long? = null

    fun onIntent(intent: AddEditIntent) {
        when(intent) {
            is AddEditIntent.SaveNote -> {
                val note = NoteUi(
                    id = intent.id ?: savedId ?: 0,
                    title = intent.title,
                    content = intent.content,
                    timestamp = intent.timestamp,
                    color = intent.color
                )

                if (intent.id != null && (intent.exitType == ExitType.SAVE || intent.exitType == ExitType.BACK)) {
                    if (intent.title.isBlank() && intent.content.isBlank()) {
                        viewModelScope.launch {
                            deleteNote(note)
                        }
                    } else {
                        insertNote(note)
                    }
                } else if (intent.exitType == ExitType.BACKGROUND) {
                    if (intent.title.isBlank() && intent.content.isBlank()) {
                        viewModelScope.launch {
                            deleteNote(note)
                        }
                    } else {
                        viewModelScope.launch {
                            noteUseCases
                                .addNote(note.toNote())
                                .onSuccess { id ->
                                    savedId = id
                                }
                        }
                    }
                } else if  (intent.exitType == ExitType.SAVE || intent.title.isNotBlank() || intent.content.isNotBlank()) {
                    insertNote(note)
                } else if (intent.exitType == ExitType.BACK) {
                    viewModelScope.launch {
                        _event.emit(AddEditEvent.Success)
                    }
                }
            }

            is AddEditIntent.ChangeColor -> {
                _state.update {
                    it.copy(
                        color = intent.color,
                        changed = true
                    )
                }
            }
        }
    }

    private fun insertNote(note: NoteUi) {
        viewModelScope.launch {
            noteUseCases
                .addNote(note.toNote())
                .onSuccess {
                    _event.emit(AddEditEvent.Success)
                }
                .onError {
                    _event.emit(AddEditEvent.Error(it))
                }
        }
    }

    private fun deleteNote(note: NoteUi) {
        viewModelScope.launch {
            noteUseCases.deleteNote(
                note.toNote()
            )
            _event.emit(AddEditEvent.Success)
        }
    }
}