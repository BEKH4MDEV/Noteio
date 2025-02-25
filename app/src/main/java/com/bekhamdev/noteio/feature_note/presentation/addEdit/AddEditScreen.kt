package com.bekhamdev.noteio.feature_note.presentation.addEdit

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.bekhamdev.noteio.core.feature_note.presentation.util.ExitType
import com.bekhamdev.noteio.core.feature_note.presentation.util.getInsetsData
import com.bekhamdev.noteio.feature_note.presentation.addEdit.components.PhoneDesign
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi
import kotlinx.coroutines.launch

@Composable
fun AddEditScreen(
    note: NoteUi?,
    onIntent: (AddEditIntent) -> Unit,
    noteColor: Color,
    snackbarHostState: SnackbarHostState,
) {
    val textFieldValueSaver = remember {
        mapSaver(
            save = { textFieldValue ->
                mapOf(
                    "text" to textFieldValue.text,
                )
            },
            restore = { savedMap ->
                TextFieldValue(
                    text = savedMap["text"] as String
                )
            }
        )
    }

    val scope = rememberCoroutineScope()
    var title by rememberSaveable(stateSaver = textFieldValueSaver) {
        mutableStateOf(TextFieldValue(note?.title ?: ""))
    }
    var content by rememberSaveable(stateSaver = textFieldValueSaver) {
        mutableStateOf(TextFieldValue(note?.content ?: ""))
    }
    val insets = getInsetsData()
    val noteBackgroundAnimatable = remember {
        Animatable(
            initialValue = noteColor
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onIntent(
                        AddEditIntent.SaveNote(
                            id = note?.id,
                            title = title.text,
                            content = content.text,
                            timestamp = if (note?.title != title.text || note.content != content.text || note.color != noteBackgroundAnimatable.value) null else note.timestamp,
                            color = noteBackgroundAnimatable.value
                        )
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(
                    end = insets.paddingValues.calculateRightPadding(LayoutDirection.Ltr)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save note"
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(noteBackgroundAnimatable.value)
                .fillMaxSize()
                .padding(
                    top = insets.paddingValues.calculateTopPadding(),
                    start = insets.paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = insets.paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                )
                .padding(bottom = innerPadding.calculateBottomPadding())
                .padding(horizontal = 16.dp)
        ) {
            PhoneDesign(
                onColorClick = { color ->
                    scope.launch {
                        noteBackgroundAnimatable.animateTo(
                            targetValue = color,
                            animationSpec = tween(
                                durationMillis = 500
                            )
                        )
                    }
                    onIntent(AddEditIntent.ChangeColor(color))
                },
                title = title,
                onTitleChange = {
                    title = it
                },
                content = content,
                onContentChange = {
                    content = it
                },
                noteColor = noteColor
            )
        }
    }

    val activityLifecycleOwner = LocalOnBackPressedDispatcherOwner.current
    DisposableEffect(activityLifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                onIntent(
                    AddEditIntent.SaveNote(
                        id = note?.id,
                        title = title.text,
                        content = content.text,
                        color = noteBackgroundAnimatable.value,
                        timestamp = if (note?.title != title.text || note.content != content.text || note.color != noteBackgroundAnimatable.value) null else note.timestamp,
                        exitType = ExitType.BACKGROUND
                    )
                )
            }
        }
        activityLifecycleOwner?.lifecycle?.addObserver(observer)
        onDispose {
            activityLifecycleOwner?.lifecycle?.removeObserver(observer)
        }
    }

    BackHandler {
        onIntent(
            AddEditIntent.SaveNote(
                id = note?.id,
                title = title.text,
                content = content.text,
                color = noteBackgroundAnimatable.value,
                timestamp = if (note?.title != title.text || note.content != content.text || note.color != noteBackgroundAnimatable.value) null else note.timestamp,
                exitType = ExitType.BACK
            )
        )
    }
}