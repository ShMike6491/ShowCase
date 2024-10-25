package org.example.showcase.common.ui.model

/**
 * Common interface for user interactions handling
 */
sealed interface UiIntent {

    data class OnButtonClick(val id: String, val data: String? = null) : UiIntent

    data object Refresh : UiIntent
}