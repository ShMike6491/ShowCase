package org.example.showcase.common.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {

    @Serializable
    data object FeedRoute: NavRoute()

    @Serializable
    data class DetailRoute(val itemId: String): NavRoute()
}