package org.example.showcase.common.ui.model

import androidx.compose.runtime.Composable

/**
 * wrapper class to handle string resources inside any viewModel
 * [DynamicString] serves to wrap any string that comes from backend
 * todo: add resources and localizations
 */
sealed class UiString {

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
        }
    }

    data class DynamicString(val value: String): UiString()
}

fun String.asUiString(): UiString {
    return UiString.DynamicString(this)
}