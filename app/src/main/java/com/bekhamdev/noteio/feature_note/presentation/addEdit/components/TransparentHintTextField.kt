package com.bekhamdev.noteio.feature_note.presentation.addEdit.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun TransparentHintTextField(
    modifier: Modifier = Modifier,
    text: TextFieldValue,
    hint: String,
    onTextChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester = FocusRequester(),
    scrollState: ScrollState,
    parentYInRoot: Float
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val heightScreen = remember { configuration.screenHeightDp * density.density }
    var textFieldYInRoot by remember { mutableFloatStateOf(0f) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var isFocused by remember { mutableStateOf(false) }
    val currentKeyboardHeight = WindowInsets.ime.getBottom(density)
    var lastCursorLine by remember { mutableStateOf<Int?>(null) }
    var lastScrollValue by remember { mutableStateOf<Int?>(null) }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = singleLine,
        textStyle = textStyle,
        cursorBrush = SolidColor(textStyle.color),
        onTextLayout = {
            textLayoutResult = it
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .onGloballyPositioned {
                textFieldYInRoot = it.positionInWindow().y
            }
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .focusRequester(focusRequester),
        decorationBox = { innerTextField ->
            if (text.text.isEmpty()) {
                innerTextField()
                Text(
                    text = hint,
                    style = textStyle,
                    color = textStyle.color.copy(alpha = .7f)
                )
            } else {
                innerTextField()
            }
        }
    )

    LaunchedEffect(
        listOf(
            isFocused,
            text.selection,
            currentKeyboardHeight
        )
    ) {
        if (
            !isFocused ||
            parentYInRoot == 0F ||
            textFieldYInRoot == 0F ||
            currentKeyboardHeight == 0 ||
            textLayoutResult == null
        ) return@LaunchedEffect

        delay(200L)

        val cursorIndex = text.selection.start
        val currentCursorLine = textLayoutResult!!.getLineForOffset(cursorIndex)

        if (lastCursorLine != currentCursorLine || scrollState.value != lastScrollValue) {
            withContext(Dispatchers.Default) {
                val keyboardYInRoot = heightScreen - currentKeyboardHeight
                val cursorRect = textLayoutResult!!.getCursorRect(cursorIndex)

                val cursorTopInRoot = textFieldYInRoot + cursorRect.top
                val cursorBottomInRoot = textFieldYInRoot + cursorRect.bottom

                val margin = with(density) { 8.dp.toPx() }
                val visibleAreaTop = parentYInRoot + margin
                val visibleAreaBottom = keyboardYInRoot - margin

                val isCursorAbove = cursorTopInRoot < visibleAreaTop
                val isCursorBelow = cursorBottomInRoot > visibleAreaBottom

                val adjustment = when {
                    isCursorAbove -> visibleAreaTop - cursorTopInRoot
                    isCursorBelow -> cursorBottomInRoot - visibleAreaBottom
                    else -> 0f
                }

                if (adjustment != 0f) {
                    val newScroll = if (isCursorAbove) {
                        scrollState.value - adjustment
                    } else {
                        scrollState.value + adjustment
                    }

                    val clampedScroll = newScroll.coerceIn(0f, scrollState.maxValue.toFloat())
                    scrollState.animateScrollTo(clampedScroll.toInt())
                }

                lastCursorLine = currentCursorLine
                lastScrollValue = scrollState.value
            }
        }
    }
}