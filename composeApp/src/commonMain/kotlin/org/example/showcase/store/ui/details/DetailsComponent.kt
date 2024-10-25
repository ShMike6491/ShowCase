package org.example.showcase.store.ui.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import org.example.showcase.common.navigation.INavigator
import org.example.showcase.common.ui.asValue
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.store.ui.model.ProductDetailState
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class DetailComponent(
    private val itemId: String,
    private val componentContext: ComponentContext,
    private val navigator: INavigator
): KoinComponent, ComponentContext by componentContext {

    private val store: DetailsStore = instanceKeeper.getStore {
        getKoin().get { parametersOf(itemId) }
    }

    val model: Value<ProductDetailState> = store.asValue()

    fun onIntent(intent: UiIntent) {
        if (intent is UiIntent.ButtonClick) {
            navigator.navigateUp()
        }
        if (intent is UiIntent.ToggleChange) {
            store.accept(intent)
        }
    }
}