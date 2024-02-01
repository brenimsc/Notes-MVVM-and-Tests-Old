package com.breno.evernotekt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.breno.evernotekt.data.NoteRepository
import com.breno.evernotekt.data.model.Note

class AddViewModel(private val repository: NoteRepository) : ViewModel() {

    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun createNote() {
        if (title.value.isNullOrEmpty() || body.value.isNullOrEmpty()) {
            _saved.value = false
            return
        }

        val note = Note(title = title.value, body = body.value)
        repository.createNote(note)
        _saved.value = true
    }

    fun getNote(noteId: Int): LiveData<Note?> = repository.getNote(noteId)
}