package com.mrkv.diary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mrkv.diary.ui.home.HomeDestination
import com.mrkv.diary.ui.home.HomeScreen
import com.mrkv.diary.ui.note.NoteDetailScreen
import com.mrkv.diary.ui.note.NoteDetailsDestination
import com.mrkv.diary.ui.note.NoteEditDestination
import com.mrkv.diary.ui.note.NoteEditScreen
import com.mrkv.diary.ui.note.NoteEntryDestination
import com.mrkv.diary.ui.note.NoteEntryScreen

// Provides Navigation graph for the application
@Composable
fun NotesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToNoteEntry = { navController.navigate(NoteEntryDestination.route) },
                navigateToNoteUpdate = {
                    navController.navigate("${NoteDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = NoteEntryDestination.route) {
            NoteEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = NoteDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteDetailsDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteDetailScreen(
                navigateToEditNote = { navController.navigate("${NoteEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() })
        }
        composable(
            route = NoteEditDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteEditDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}