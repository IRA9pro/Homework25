package com.example.notes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_list")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val text: String,
    val time: String,
    val color: NoteColor
) : Serializable
