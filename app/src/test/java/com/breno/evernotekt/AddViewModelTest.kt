package com.breno.evernotekt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.breno.evernotekt.data.NoteRepository
import com.breno.evernotekt.data.model.Note
import com.breno.evernotekt.viewmodel.AddViewModel
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddViewModel

    @Mock
    lateinit var repository: NoteRepository

    @Before
    fun setup() {
        viewModel = AddViewModel(repository)
    }

    @Test
    fun `test must not save note without title`() {
        viewModel.title.value = ""
        viewModel.body.value = ""

        viewModel.createNote()

        Assert.assertEquals(false, viewModel.saved.value)
    }

    @Test
    fun `test must save note`() {
        val note = Note(title = "Nota 1", body = "Nota 1 corpo")
        viewModel.title.value = note.title
        viewModel.body.value = note.body

        viewModel.createNote()

        Assert.assertEquals(true, viewModel.saved.value)

        verify(repository).createNote(note)
    }

    @Test
    fun `test must get a note`() {
        val noteA = Note(id = 1, title = "Nota A", body = "Nota A corpo")

        val liveData = MutableLiveData<Note>()
        liveData.value = noteA

        doReturn(liveData).`when`(repository).getNote(noteA.id)

        viewModel.getNote(noteA.id)

        Assert.assertEquals(noteA, viewModel.getNote(noteA.id).value)
    }
}