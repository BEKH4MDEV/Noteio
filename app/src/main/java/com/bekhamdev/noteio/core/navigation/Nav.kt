package com.bekhamdev.noteio.core.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bekhamdev.noteio.core.feature_note.presentation.util.ObserveAsEvents
import com.bekhamdev.noteio.feature_note.presentation.addEdit.AddEditEvent
import com.bekhamdev.noteio.feature_note.presentation.addEdit.AddEditScreen
import com.bekhamdev.noteio.feature_note.presentation.addEdit.AddEditViewModel
import com.bekhamdev.noteio.feature_note.presentation.notes.NotesIntent
import com.bekhamdev.noteio.feature_note.presentation.notes.NotesScreen
import com.bekhamdev.noteio.feature_note.presentation.notes.NotesViewModel
import kotlinx.coroutines.launch

@Composable
fun Nav(
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state by notesViewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Route.NotesScreen
    ) {
        composable<Route.NotesScreen> {
            NotesScreen(
                state = state,
                onIntent = {
                    notesViewModel.onIntent(it)
                    when(it) {
                        is NotesIntent.ClickNote -> {
                            navController.navigate(Route.AddEditScreen) {
                                launchSingleTop = true
                            }
                        }
                        else -> {}
                    }
                },
                goToAddEditScreen = {
                    navController.navigate(Route.AddEditScreen) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Route.AddEditScreen> {
            val addEditViewModel: AddEditViewModel = hiltViewModel()
            val addEditState by addEditViewModel.state.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            ObserveAsEvents(
                events = addEditViewModel.event,
            ) {
                when(it) {
                    is AddEditEvent.Error -> {
                        if (snackbarHostState.currentSnackbarData == null) {
                            scope.launch {
                                snackbarHostState.showSnackbar(context.getString(it.message))
                            }
                        }
                    }
                    AddEditEvent.Success -> {
                        navController.popBackStack()
                    }
                }
            }

            val noteColor = if (addEditState.changed) {
                addEditState.color
            } else state.selectedNote?.color ?: addEditState.color

            AddEditScreen(
                note = state.selectedNote,
                onIntent = addEditViewModel::onIntent,
                noteColor = noteColor,
                snackbarHostState = snackbarHostState
            )
        }
    }
}