package com.mrkv.diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrkv.diary.view.CreateNote
import com.mrkv.diary.view.DiaryList


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavGraph()
        }
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "notesList"
    ) {
        composable("notesList") {
            DiaryList().NotesList(navController)
        }
        composable("createNotePage") {
            CreateNote().CreateNotePage()
        }
    }
}
