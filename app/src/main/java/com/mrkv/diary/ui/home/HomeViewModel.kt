package com.mrkv.diary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkv.diary.data.Note
import com.mrkv.diary.data.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(notesRepository: NotesRepository) : ViewModel() {

    // Holds Home ui state. The list of items are retrieved from NotesRepository and mapped to HomeUiState
    val homeUiState: StateFlow<HomeUiState> =
        notesRepository.getAllNotesStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

// Ui state for Home screen
data class HomeUiState(val notesList: List<Note> = listOf())