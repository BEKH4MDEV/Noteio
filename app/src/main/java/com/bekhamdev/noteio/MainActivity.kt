package com.bekhamdev.noteio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bekhamdev.noteio.ui.theme.NoteioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteioTheme(
                dynamicColor = false,
                darkTheme = true
            ) {

            }
        }
    }
}