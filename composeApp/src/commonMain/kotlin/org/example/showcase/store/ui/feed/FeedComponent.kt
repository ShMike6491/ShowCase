package org.example.showcase.store.ui.feed

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import org.example.showcase.common.navigation.INavigator
import org.example.showcase.common.navigation.NavRoute
import org.example.showcase.common.ui.asValue
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.store.ui.model.ProductFeedState
import org.koin.core.component.KoinComponent

class FeedComponent(
    private val componentContext: ComponentContext,
    private val navigator: INavigator,
): KoinComponent, ComponentContext by componentContext {

    private val store: FeedStore = instanceKeeper.getStore { getKoin().get() }

    val model: Value<ProductFeedState> = store.asValue()

    fun onIntent(intent: UiIntent) {
        if (intent is UiIntent.ButtonClick && intent.id != null) {
            val route = NavRoute.DetailRoute(intent.id)
            navigator.navigateTo(route)
        }

        if (intent is UiIntent.ToggleChange) {
            store.accept(intent)
        }
    }
}