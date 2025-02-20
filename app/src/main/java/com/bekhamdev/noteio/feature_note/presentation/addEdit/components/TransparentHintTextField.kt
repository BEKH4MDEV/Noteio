package com.bekhamdev.noteio.feature_note.presentation.addEdit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onTextChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = singleLine,
        textStyle = textStyle,
        cursorBrush = SolidColor(textStyle.color),
        modifier = modifier
            .fillMaxWidth(),
        decorationBox = {
            if (text.isEmpty()) {
                it()
                Text(
                    text = hint,
                    style = textStyle,
                    color = Color.DarkGray.copy(
                        alpha = 0.8f
                    )
                )
            } else {
                it()
            }
        }
    )

}