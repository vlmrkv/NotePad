package com.mrkv.diary.view

import android.app.Application
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrkv.diary.R
import com.mrkv.diary.model.NotesViewModel

class CreateNote {
    @Composable
    fun CreateNotePage(notesViewModel: NotesViewModel = viewModel(factory = NotesViewModel.factory)) {
        val notesList = notesViewModel.notesList
        val titleValue = remember { mutableStateOf(TextFieldValue()) }
        val textValue = remember { mutableStateOf(TextFieldValue()) }
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

            TextField(
                value = titleValue.value,
                onValueChange = { titleValue.value = it },
                placeholder = { Text(text = "Заголовок") },
                modifier = Modifier
                    .padding(16.dp, 2.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                maxLines = 1
            )

            TextField(
                value = textValue.value,
                onValueChange = { textValue.value = it },
                placeholder = { Text(text = "Запиши свои мысли") },
                modifier = Modifier
                    .padding(16.dp, 2.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                maxLines = 10
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(80.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(.2f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "00:00")
                    }
                    Column(
                        modifier = Modifier
                            .weight(.5f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinearProgressIndicator(progress = .7f)
                    }
                    Column(
                        modifier = Modifier
                            .weight(.3f)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .size(70.dp, 70.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.record_icon),
                                contentDescription = "record button"
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(.3f)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .size(70.dp, 70.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.play_icon),
                                contentDescription = "play button"
                            )
                        }
                    }
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp),
                    onClick = {
                        notesList.forEach {
                            it.title = titleValue.value.toString()
                            it.textNote = textValue.value.toString()
                        }
                    }
                ) {
                    Text(text = "Save")
                }

                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp),
                    onClick = { TODO() }
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}