package com.mrkv.diary.ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkv.diary.data.NotesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {
    // Holds current note ui state
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    private val noteId: Int = checkNotNull(savedStateHandle[NoteEditDestination.noteIdArg])

    init {
        viewModelScope.launch {
            noteUiState = notesRepository.getNoteStream(noteId)
                .filterNotNull()
                .first()
                .toNoteUiState(true)
        }
    }

    // Update the note in the NotesRepository's data source
    suspend fun updateNote() {
        if (validateInput(noteUiState.noteDetails)) {
            notesRepository.updateNote(noteUiState.noteDetails.toNote())
        }
    }

    // Updates the noteUiState with the value provided in the argument. This method also triggers
    // a validation for input values.
    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState = NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && textNote.isNotBlank() && audioNote.isNotBlank()
        }
    }
}