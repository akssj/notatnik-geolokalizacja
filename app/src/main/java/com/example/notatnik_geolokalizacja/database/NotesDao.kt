package com.example.notatnik_geolokalizacja.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notatnik_geolokalizacja.database.model.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes")
    fun getNotes():LiveData<List<Notes>>

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    fun deleteNotes(id:Int)
}