package com.breno.evernotekt.data

import androidx.lifecycle.LiveData
import com.breno.evernotekt.data.model.Note

interface NoteRepository {
    fun getAllNotes(): LiveData<List<Note>?>
    fun getNote(noteId: Int): LiveData<Note?>
    fun createNote(note: Note): LiveData<Note>
}