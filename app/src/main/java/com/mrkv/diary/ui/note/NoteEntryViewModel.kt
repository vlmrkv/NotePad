package com.mrkv.diary.ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mrkv.diary.data.Note
import com.mrkv.diary.data.NotesRepository
import okhttp3.internal.notify

class NoteEntryViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    // holds current note ui state
    var noteUiState: NoteUiState by mutableStateOf(NoteUiState())
        private set

    // Update noteUiState with the value provided in the argument. This method also triggers
    // a validation for input values.
    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    // Inserts a note in the Room database
    suspend fun saveNote() {
        if (validateInput()) {
            notesRepository.insertNote(noteUiState.noteDetails.toNote())
        }
    }

    fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && textNote.isNotBlank()
        }
    }
}

// Represents Ui state for a note
data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val image: String = "",
    val title: String = "",
    val textNote: String = "",
    val audioNote: String = "",
    val date: String = ""
)

// Extension function to convert NoteUiState to Note.
fun NoteDetails.toNote(): Note = Note(
    id = id,
    image = image,
    title = title,
    textNote = textNote,
    audioNote = audioNote,
    date = date
)

// Extension function to convert note to noteUiState
fun Note.toNoteUiState(isEntryValid: Boolean = false): NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isEntryValid = isEntryValid
)

// Extension function to convert note to noteDetails
fun Note.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    title = title.toString(),
    textNote = textNote.toString(),
    audioNote = audioNote.toString()
)