package com.example.notes.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes.data.models.NoteModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_list ORDER BY id DESC")
    fun getAllNotes(): List<NoteModel>

    @Upsert
    fun saveNote(noteMode: NoteModel)

    @Delete
    fun deleteNote(noteModel: NoteModel)
}