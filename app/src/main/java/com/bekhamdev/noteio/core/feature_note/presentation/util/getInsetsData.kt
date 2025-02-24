package com.bekhamdev.noteio.core.feature_note.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class InsetsData(
    val paddingValues: PaddingValues,
    val windowInsets: WindowInsets
)

@Composable
fun getInsetsData(): InsetsData {
    val insets = WindowInsets.statusBars
        .union(WindowInsets.navigationBars)
        .union(WindowInsets.ime)
        .union(WindowInsets.displayCutout)

    val padding = insets.asPaddingValues()

    return remember { InsetsData(padding , insets) }
}
