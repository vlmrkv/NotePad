package com.mrkv.diary.ui.note

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrkv.diary.NotesTopAppBar
import com.mrkv.diary.R
import com.mrkv.diary.ui.AppViewModelProvider
import com.mrkv.diary.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object NoteEditDestination : NavigationDestination {
    override val route: String = "note_edit"
    override val titleRes: Int = R.string.edit_note_title
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            NotesTopAppBar(
                title = stringResource(NoteEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NoteEntryBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateNote()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}