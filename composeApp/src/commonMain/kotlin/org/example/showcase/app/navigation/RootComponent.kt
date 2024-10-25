package org.example.showcase.app.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import org.example.showcase.common.navigation.NavRoute
import org.example.showcase.store.ui.details.DetailComponent
import org.example.showcase.store.ui.feed.FeedComponent

class RootComponent(
    contextComponent: ComponentContext
): ComponentContext by contextComponent {

    private val navigation = StackNavigation<NavRoute>()
    private val navigator = DecomposeNavigatorImpl(navigation)

    val childStack = childStack(
        source = navigation,
        serializer = NavRoute.serializer(),
        initialConfiguration = NavRoute.FeedRoute,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: NavRoute,
        context: ComponentContext
    ): Child {
        return when (config) {
            is NavRoute.FeedRoute -> Child.Feed(
                FeedComponent(
                    componentContext = context,
                    navigator = navigator
                )
            )
            is NavRoute.DetailRoute -> Child.Details(
                DetailComponent(
                    itemId = config.itemId,
                    componentContext = context,
                    navigator = navigator
                )
            )
        }
    }

    sealed class Child {
        data class Feed(val component: FeedComponent) : Child()
        data class Details(val component: DetailComponent) : Child()
    }
}