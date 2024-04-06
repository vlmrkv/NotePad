package com.mrkv.diary.ui.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkv.diary.data.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


// Viewmodel to retrieve, update and delete a note from notesRepository's data source
class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val noteId: Int = checkNotNull(savedStateHandle[NoteDetailsDestination.noteIdArg])

    // Holds the note details ui state. The data is retrieved from notesRepository and mapped to the UI State
    val uiState: StateFlow<NoteDetailsUiState> = notesRepository.getNoteStream(noteId)
        .filterNotNull()
        .map {
            NoteDetailsUiState(isEmpty = it.id == 0, noteDetails = it.toNoteDetails())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = NoteDetailsUiState()
        )

    // Deletes the note from the NotesRepository's data source
    suspend fun deleteNote() {
        notesRepository.deleteNote(uiState.value.noteDetails.toNote())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5000L
    }
}

// Ui state for NoteDetailsScreen
data class NoteDetailsUiState(
    val isEmpty: Boolean = true,
    val noteDetails: NoteDetails = NoteDetails()
)