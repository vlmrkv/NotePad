package com.mrkv.diary.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mrkv.diary.App
import com.mrkv.diary.NotesDatabase
import kotlinx.coroutines.launch

class NotesViewModel(val database: NotesDatabase) : ViewModel() {

    var notesList = database.notes().getAll()

    fun addNote(note: Note) = viewModelScope.launch {
        database.notes().insert(note)
    }

    fun removeNote(note: Note) = viewModelScope.launch {
        database.notes().delete(note)
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return NotesViewModel(database) as T
            }
        }
    }
}