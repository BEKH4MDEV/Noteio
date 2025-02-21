package com.bekhamdev.noteio.feature_note.presentation.addEdit

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.bekhamdev.noteio.core.feature_note.domain.util.noteColors
import com.bekhamdev.noteio.core.feature_note.presentation.util.getInsetsData
import com.bekhamdev.noteio.feature_note.presentation.addEdit.components.TransparentHintTextField
import com.bekhamdev.noteio.feature_note.presentation.model.NoteUi
import com.bekhamdev.noteio.ui.theme.surfaceBrightDark
import kotlinx.coroutines.launch

@Composable
fun AddEditScreen(
    note: NoteUi?,
    onIntent: (AddEditIntent) -> Unit,
    noteColor: Color,
    snackbarHostState: SnackbarHostState,
) {
    val scope = rememberCoroutineScope()
    var title by rememberSaveable { mutableStateOf(note?.title ?: "") }
    var content by rememberSaveable { mutableStateOf(note?.content ?: "") }
    val insets = getInsetsData()
    val noteBackgroundAnimatable = remember {
        Animatable(
            initialValue = noteColor
        )
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onIntent(AddEditIntent.SaveNote(
                        id = note?.id,
                        title = title,
                        content = content,
                        color = noteBackgroundAnimatable.value
                    ))
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
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(noteBackgroundAnimatable.value)
                .padding(
                    top = insets.paddingValues.calculateTopPadding(),
                    start = insets.paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = insets.paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                )
                .padding(bottom = innerPadding.calculateBottomPadding())
                .fillMaxSize()
                .padding(
                    start = if (insets.paddingValues.calculateLeftPadding(LayoutDirection.Ltr) >
                        0.dp) 0.dp else 16.dp,
                    end = if (insets.paddingValues.calculateRightPadding(LayoutDirection.Ltr) >
                        0.dp) 0.dp else 16.dp,
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                noteColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (noteColor == color) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                onIntent(AddEditIntent.ChangeColor(color))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = title,
                hint = "Choose a title",
                onTextChange = {
                    title = it
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = surfaceBrightDark.copy(
                        alpha = .9f
                    )
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = content,
                hint = "Enter some content...",
                onTextChange = {
                    content = it
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = surfaceBrightDark.copy(
                        alpha = .9f
                    )
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}