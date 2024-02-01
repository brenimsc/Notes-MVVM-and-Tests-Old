package com.breno.evernotekt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.breno.evernotekt.data.NoteRepository
import com.breno.evernotekt.data.model.Note

class HomeViewModel(private val repository: NoteRepository) : ViewModel() {

    fun getAllNotes(): LiveData<List<Note>?> {
        return repository.getAllNotes()
    }
}