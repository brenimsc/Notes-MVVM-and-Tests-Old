package com.breno.evernotekt.home

import com.breno.evernotekt.model.Note

interface Home {

    interface Presenter {
        fun getAllNotes()
        fun stop()
    }

    interface View {
        fun displayNotes(notes: List<Note>)
        fun displayEmptyNotes()
        fun displayError(message: String)
    }

}