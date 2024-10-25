package org.example.showcase.common.ui.model

/**
 * Common interface for user interactions handling
 */
sealed interface UiIntent {

    data class ButtonClick(val id: String? = null) : UiIntent

    data class ToggleChange(val state: Boolean, val id: String? = null): UiIntent

    data class TextChange(val text: String? = null, val id: String? = null): UiIntent

    data object BackPress : UiIntent

    data object Refresh : UiIntent
}