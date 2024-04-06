package com.mrkv.diary.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrkv.diary.NotesTopAppBar
import com.mrkv.diary.R
import com.mrkv.diary.data.Note
import com.mrkv.diary.ui.AppViewModelProvider
import com.mrkv.diary.ui.navigation.NavigationDestination
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

// Entry route for Home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToNoteEntry: () -> Unit,
    navigateToNoteUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToNoteEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.padding_large)
                )
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->
        HomeBody(
            notesList = homeUiState.notesList,
            onNoteClick = navigateToNoteUpdate,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun HomeBody(
    notesList: List<Note>, onNoteClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (notesList.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_list_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            NotesList(
                notesList = notesList,
                onNoteClick = { onNoteClick(it.id) },
                modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun NotesList(
    notesList: List<Note>, onNoteClick: (Note) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = notesList, key = { it.id }) { item ->
            NoteItem(note = item, modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .clickable { onNoteClick(item) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteItem(
    note: Note, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(red = 98, green = 32, blue = 24, alpha = 60)),
            Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoilImage(
                    imageModel = { note.image },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(70.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )
                Column(
                    modifier = Modifier
                        .weight(2.0f)
                ) {
                    Text(
                        text = note.title.toString(),
                        style = TextStyle(
                            fontSize = 24.sp,
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = note.textNote.toString(),
                        style = TextStyle(
                            textAlign = TextAlign.Justify
                        )
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.0f)
                        .background(color = Color.Cyan),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = note.date.toString(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteItemPreview() {
    NoteItem(
        note = Note(
            id = 1,
            title = "Note",
            textNote = "Hello",
            audioNote = null,
            date = "06-04-2024",
            image = null
        )
    )
}

val list = listOf(
    Note(
        id = 1,
        title = "Note",
        textNote = "Hello",
        audioNote = null,
        date = "06-04-2024",
        image = null
    ),
    Note(
        id = 1,
        title = "Note",
        textNote = "Hello",
        audioNote = null,
        date = "06-04-2024",
        image = null
    ),
    Note(
        id = 1,
        title = "Note",
        textNote = "Hello",
        audioNote = null,
        date = "06-04-2024",
        image = null
    )
)

@Preview(showBackground = true)
@Composable
private fun NotesListPreview() {
    NotesList(notesList = list, onNoteClick = { /* Do nothing */ })
}