package com.bekhamdev.noteio.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object NotesScreen: Route
    @Serializable
    data object AddEditScreen: Route
}