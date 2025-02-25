package com.bekhamdev.noteio.feature_note.presentation.addEdit.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bekhamdev.noteio.R
import com.bekhamdev.noteio.core.feature_note.domain.util.noteColors
import com.bekhamdev.noteio.ui.theme.surfaceBrightDark

@Composable
fun PhoneDesign(
    modifier: Modifier = Modifier,
    onColorClick: (Color) -> Unit,
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    content: TextFieldValue,
    onContentChange: (TextFieldValue) -> Unit,
    noteColor: Color,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .padding(
                    top = if (!isLandscape) 16.dp else 0.dp,
                    bottom = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
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
                            onColorClick(color)
                        }
                )
            }
        }
        val scrollState = rememberScrollState()
        var parentYInRoot by remember { mutableFloatStateOf(0f) }
        Column(
            modifier = Modifier
                .onGloballyPositioned {
                    parentYInRoot = it.positionInRoot().y
                }
                .fillMaxSize()
                .verticalScroll(scrollState)
                .imePadding()
        ) {
            val focusContentRequester = remember { FocusRequester() }
            val keyboardController = LocalSoftwareKeyboardController.current
            TransparentHintTextField(
                text = title,
                hint = stringResource(id = R.string.title_placeholder),
                onTextChange = onTitleChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = surfaceBrightDark.copy(
                        alpha = .9f
                    )
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                scrollState = scrollState,
                parentYInRoot = parentYInRoot,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = content,
                hint = stringResource(id = R.string.content_placeholder) + "...",
                onTextChange = onContentChange,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = surfaceBrightDark.copy(
                        alpha = .9f
                    )
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default
                ),
                focusRequester = focusContentRequester,
                scrollState = scrollState,
                parentYInRoot = parentYInRoot,
            )
            Spacer(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusContentRequester.requestFocus()
                                keyboardController?.show()
                            }
                        )
                    }
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}