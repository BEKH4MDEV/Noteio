package com.bekhamdev.noteio.feature_note.domain.useCase.note

import com.bekhamdev.noteio.core.feature_note.domain.util.NoteOrder
import com.bekhamdev.noteio.core.feature_note.domain.util.OrderType
import com.bekhamdev.noteio.feature_note.data.repository.FakeNoteRepository
import com.bekhamdev.noteio.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNotesUseCase = GetNotesUseCase(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    id = index.toLong(),
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    colorIndex = index
                )
            )
        }

        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeRepository.insertNote(it) }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title <= notes[i + 1].title)
        }

    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title >= notes[i + 1].title)
        }

    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp <= notes[i + 1].timestamp)
        }

    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp >= notes[i + 1].timestamp)
        }

    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].colorIndex <= notes[i + 1].colorIndex)
        }

    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].colorIndex >= notes[i + 1].colorIndex)
        }

    }
}