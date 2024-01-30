package com.breno.evernotekt

import com.breno.evernotekt.model.Note
import org.junit.Assert
import org.junit.Test

/**
 *
 * Setembro, 25 2019
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
class NoteTest {

    @Test
    fun `test should format date pattern to month and year`() {
        val note = Note(title = "NotaA", body = "NotaA Conteudo", date = "20/02/2019")

        Assert.assertEquals("Fev. 2019", note.createdDate)
    }


    @Test
    fun `test should format date case empty`() {
        val note = Note(title = "NotaA", body = "NotaA Conteudo", date = "")

        Assert.assertEquals("", note.createdDate)
    }

    @Test
    fun `test should format date case null`() {
        val note = Note(title = "NotaA", body = "NotaA Conteudo")

        Assert.assertEquals("", note.createdDate)
    }

}