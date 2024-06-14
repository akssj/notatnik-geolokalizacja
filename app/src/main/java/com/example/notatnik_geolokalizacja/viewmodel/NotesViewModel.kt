package com.example.notatnik_geolokalizacja.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notatnik_geolokalizacja.database.NotesDatabase
import com.example.notatnik_geolokalizacja.database.model.Notes
import com.example.notatnik_geolokalizacja.database.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository
    private val allNotes: LiveData<List<Notes>>

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
        allNotes = repository.getAllNotes()
    }

    fun getNotes(): LiveData<List<Notes>> {
        return allNotes
    }
    fun addNotes(notes: Notes) {
        viewModelScope.launch {
            repository.insertNotes(notes)
        }
    }

    fun deleteNotes(id: Int) {
        viewModelScope.launch {
            repository.deleteNotes(id)
        }
    }

}