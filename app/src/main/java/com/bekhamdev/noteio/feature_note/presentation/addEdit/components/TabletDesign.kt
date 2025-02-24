package com.bekhamdev.noteio.feature_note.presentation.addEdit.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TabletDesign(
    modifier: Modifier = Modifier,
    onColorClick: (Color) -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    noteColor: Color
) {
   //Todo: Probably not because it looks good on Phone Design
}