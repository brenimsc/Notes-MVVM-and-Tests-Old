package com.breno.evernotekt

import com.breno.br.add.Add
import com.breno.evernotekt.add.presentation.AddPresenter
import com.breno.evernotekt.model.Note
import com.breno.evernotekt.model.RemoteDataSource
import com.nhaarman.mockito_kotlin.anyOrNull
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddPresenterTests {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Add.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    lateinit var addPresenter: AddPresenter

    @Before
    fun setup() {
        addPresenter = AddPresenter(mockView, mockDataSource)
    }

    @Test
    fun `test must not add note with empty body`() {
        // When
        addPresenter.createNote("", "")

        // Then
        Mockito.verify(mockView).displayError("Título e corpo da nota deve ser informado")
    }

    @Test
    fun `test must add note`() {
        // Given
        val note = Note(title = "NotaA", body = "Corpo NotaA")
        Mockito.doReturn(Observable.just(note)).`when`(mockDataSource).createNote(note)

        // When
        addPresenter.createNote(note.title.orEmpty(), note.body.orEmpty())

        // Then
        Mockito.verify(mockDataSource).createNote(note)

        Mockito.verify(mockView).returnToHome()
    }

    @Test
    fun `test must show error message when creation failure`() {
        // Given
        val note = Note(title = "NotaA", body = "Corpo NotaA")
        Mockito.doReturn(Observable.error<Throwable>(Throwable("server error"))).`when`(
            mockDataSource
        ).createNote(anyOrNull())

        // When
        addPresenter.createNote(note.title.orEmpty(), note.body.orEmpty())

        // Then
        Mockito.verify(mockDataSource).createNote(note)

        Mockito.verify(mockView).displayError("Erro ao criar a nota")
    }

}