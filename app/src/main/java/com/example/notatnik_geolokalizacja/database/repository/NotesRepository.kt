package com.example.notatnik_geolokalizacja.database.repository

import androidx.lifecycle.LiveData
import com.example.notatnik_geolokalizacja.database.NotesDao
import com.example.notatnik_geolokalizacja.database.model.Notes

class NotesRepository(val dao: NotesDao) {
     fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

    fun insertNotes(notes: Notes) {
        dao.insertNotes(notes)
    }

    fun deleteNotes(id: Int) {
        dao.deleteNotes(id)
    }
}