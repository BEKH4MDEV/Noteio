package com.bekhamdev.noteio.feature_note.presentation.addEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekhamdev.noteio.core.feature_note.domain.util.onError
import com.bekhamdev.noteio.core.feature_note.domain.util.onSuccess
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

    fun onIntent(intent: AddEditIntent) {
        when(intent) {
            is AddEditIntent.SaveNote -> {
                val note = NoteUi(
                    id = intent.id ?: 0,
                    title = intent.title,
                    content = intent.content,
                    color = intent.color
                )
                insertNote(note)
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
}