package com.mrkv.diary.model

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.mrkv.diary.NotesDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class NotesViewModel(application: Application) : ViewModel() {

    private val db = Room.databaseBuilder(
        application,
        NotesDatabase::class.java,
        "db-notes"
    ).build()

    private var notes by mutableStateOf(listOf<Note>())

    // load initial data from Room asynchronously
    init {
        GlobalScope.launch {
            val items = db.notes().getAll()
            viewModelScope.launch { notes = items }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addNote(
        image: String,
        title: String,
        text: String,
        audio: String,
        date: String
        ) {
        val noteObj = Note(
            (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),// Generate ID from timestamp
            image,
            title,
            text,
            audio,
            date)
        notes += listOf(noteObj)
        GlobalScope.launch { db.notes().insert(noteObj) }
    }

    fun removeNote(note: Note) {
        notes = notes.filter { it !in listOf(note) }
        GlobalScope.launch { db.notes().delete(note) }
    }
}