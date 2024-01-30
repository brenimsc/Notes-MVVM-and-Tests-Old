package com.breno.evernotekt

import com.breno.evernotekt.home.Home
import com.breno.evernotekt.home.presentation.HomePresenter
import com.breno.evernotekt.model.Note
import com.breno.evernotekt.model.RemoteDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTests {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Home.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    lateinit var homePresenter: HomePresenter

    private val fakeAllNotes: List<Note>
        get() = arrayListOf(
            Note(1, "NotaB", "NotaA desc", "01/10/2012", "Seja bem vindo a nota A"),
            Note(2, "NotaB", "NotaB desc", "02/10/2012", "Seja bem vindo a nota"),
            Note(3, "NotaC", "NotaC desc", "01/11/2013", "Seja bem vindo a nota C")
        )

    @Before
    fun setup() {
        homePresenter = HomePresenter(mockView, mockDataSource)
    }

    @Test
    fun `test must get all notes`() {
        // Given
        Mockito.doReturn(Observable.just(fakeAllNotes)).`when`(mockDataSource).listNotes()

        // When
        homePresenter.getAllNotes()

        // Then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).displayNotes(fakeAllNotes)
    }

    @Test
    fun `test must show empty notes`() {
        // Given
        Mockito.doReturn(Observable.just(arrayListOf<Note>())).`when`(mockDataSource).listNotes()

        // When
        homePresenter.getAllNotes()

        // Then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).displayEmptyNotes()
    }

}