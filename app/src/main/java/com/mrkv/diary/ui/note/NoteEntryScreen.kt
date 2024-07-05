package com.mrkv.diary.ui.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrkv.diary.NotesTopAppBar
import com.mrkv.diary.R
import com.mrkv.diary.ui.AppViewModelProvider
import com.mrkv.diary.ui.navigation.NavigationDestination
import com.mrkv.diary.ui.theme.DiaryTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object NoteEntryDestination : NavigationDestination {
    override val route: String = "note_entry"
    override val titleRes: Int = R.string.note_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            NotesTopAppBar(
                title = stringResource(NoteEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        NoteEntryBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                // Note: If the user rotates the screen very fast, the operation may get cancelled
                // and the item may not be saved in the Database. This is because when config
                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                // be cancelled - since the scope is bound to composition.
                coroutineScope.launch {
                    viewModel.saveNote()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun NoteEntryBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            ItemInputForm(
                noteDetails = noteUiState.noteDetails,
                onValueChange = onNoteValueChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(
                onClick = onSaveClick,
                enabled = noteUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.save_action))
            }
            Button(
                onClick = { TODO() },
                enabled = noteUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
            ) {
                Text(text = stringResource(R.string.cancel_action))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemInputForm(
    noteDetails: NoteDetails,
    modifier: Modifier = Modifier,
    onValueChange: (NoteDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Image(
            modifier = Modifier
                .width(350.dp)
                .height(350.dp)
                .align(Alignment.CenterHorizontally)
                .padding(dimensionResource(id = R.dimen.padding_large))
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
                .clickable {

                },
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
        )
        OutlinedTextField(
            value = noteDetails.title,
            onValueChange = { onValueChange(noteDetails.copy(title = it)) },
            label = { Text(stringResource(R.string.item_title_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = noteDetails.textNote,
            onValueChange = { onValueChange(noteDetails.copy(textNote = it)) },
            label = { Text(stringResource(R.string.item_textNote_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            enabled = enabled,
            maxLines = 5
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteEntryScreenPreview() {
    DiaryTheme {
        NoteEntryBody(
            noteUiState = NoteUiState(NoteDetails()),
            onNoteValueChange = {},
            onSaveClick = { })
    }
}