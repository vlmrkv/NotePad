package com.mrkv.diary.view

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mrkv.diary.model.Note
import com.mrkv.diary.model.NotesViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

class DiaryList {

    @Composable
    fun NotesList(
        navController: NavController,
        notesViewModel: NotesViewModel = viewModel(factory = NotesViewModel.factory)
    ) {
        val notesList = notesViewModel.notesList
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(notesList.size) {
                NoteListItem(
                    navController, notesList[it]
                )
            }
        }
        FloatingActionButton(navController)
    }

    @Composable
    fun FloatingActionButton(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                FloatingActionButton(onClick = { navController.navigate("createNotePage") }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Button")
                }
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteListItem(
        navController: NavController,
        note: Note
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            onClick = { navController.navigate("createNotePage") }
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
                        note.title?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Justify,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        note.textNote?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1.0f)
                            .background(color = Color.Cyan),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        note.date?.let {
                            Text(
                                text = it,
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
    }
}