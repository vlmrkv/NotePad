package com.mrkv.diary.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrkv.diary.NotesApplication
import com.mrkv.diary.ui.home.HomeViewModel
import com.mrkv.diary.ui.note.NoteDetailsViewModel
import com.mrkv.diary.ui.note.NoteEditViewModel
import com.mrkv.diary.ui.note.NoteEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for NoteEditViewModel
        initializer {
            NoteEditViewModel(
                this.createSavedStateHandle(),
                notesApplication().container.notesRepository
            )
        }

        // Initializer for NoteEntryViewModel
        initializer {
            NoteEntryViewModel(
                notesApplication().container.notesRepository
            )
        }

        // Initializer for NoteDetailsViewModel
        initializer {
            NoteDetailsViewModel(
                this.createSavedStateHandle(),
                notesApplication().container.notesRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(notesApplication().container.notesRepository)
        }
    }
}

// Extension function to queries for Application object and returns an instance of NotesApplication
fun CreationExtras.notesApplication(): NotesApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)