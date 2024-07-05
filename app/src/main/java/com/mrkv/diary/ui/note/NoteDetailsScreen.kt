package com.mrkv.diary.ui.note

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrkv.diary.NotesTopAppBar
import com.mrkv.diary.R
import com.mrkv.diary.data.Note
import com.mrkv.diary.ui.AppViewModelProvider
import com.mrkv.diary.ui.navigation.NavigationDestination
import com.mrkv.diary.ui.theme.DiaryTheme
import kotlinx.coroutines.launch

object NoteDetailsDestination : NavigationDestination {
    override val route: String = "note_details"
    override val titleRes: Int = R.string.note_details_title
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    navigateToEditNote: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            NotesTopAppBar(
                title = stringResource(NoteDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditNote(uiState.value.noteDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_note_title)
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        NoteDetailsBody(
            noteDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteNote()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun NoteDetailsBody(
    noteDetailsUiState: NoteDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable {
            mutableStateOf(false)
        }
        NoteDetails(
            note = noteDetailsUiState.noteDetails.toNote(), modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun NoteDetails(
    note: Note, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .width(350.dp)
                    .height(350.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
                    .clickable {

                    },
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
            )

            NoteItemDetailsRow(
                labelResId = R.string.item_title_req,
                noteDetail = note.title.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )

            NoteItemDetailsRow(
                labelResId = R.string.item_textNote_req,
                noteDetail = note.textNote.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
private fun NoteItemDetailsRow(
    @StringRes labelResId: Int, noteDetail: String, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = stringResource(labelResId))
        //Spacer(modifier = Modifier.weight(1f))
        Text(text = noteDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /*Do nothing*/ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailsScreenPreview() {
    DiaryTheme {
        NoteDetailsBody(
            NoteDetailsUiState(
                noteDetails = NoteDetails(
                    1,
                    "null",
                    "Hello",
                    "Lorem Ipsum is simply dummy text of the printing " +
                            "and typesetting industry. Lorem Ipsum has been " +
                            "the industry's standard dummy text ever since the 1500s, " +
                            "when an unknown printer took a galley of type and " +
                            "scrambled it to make a type specimen book. " +
                            "It has survived not only five centuries, " +
                            "but also the leap into electronic typesetting, " +
                            "remaining essentially unchanged. It was popularised in the 1960s " +
                            "with the release of Letraset sheets containing Lorem Ipsum " +
                            "passages, and more recently with desktop publishing software " +
                            "like Aldus PageMaker including versions of Lorem Ipsum."
                )
            ), onDelete = { })
    }
}