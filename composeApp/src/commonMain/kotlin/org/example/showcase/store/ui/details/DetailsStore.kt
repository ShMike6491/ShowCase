package org.example.showcase.store.ui.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.showcase.common.domain.model.IError
import org.example.showcase.common.domain.model.Result
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.StoreProductsInteractor
import org.example.showcase.store.domain.model.IProduct
import org.example.showcase.store.ui.model.ProductDetailState
import org.example.showcase.store.ui.model.asDetailState
import org.koin.core.component.KoinComponent

const val DETAILS_STORE_TAG = "DetailsStore"

interface DetailsStore : Store<UiIntent, ProductDetailState, Nothing>

object DetailsReducerImpl : Reducer<ProductDetailState, Result<IProduct, IError>> {

    override fun ProductDetailState.reduce(msg: Result<IProduct, IError>): ProductDetailState {
        return when (msg) {
            is Result.Error -> copy(
                isLoading = false,
                // todo: extract hardcoded
                errorMessage = "Something went wrong!".asUiString()
            ).also {
                println("STORE_DETAILS: ${it.errorMessage}")
            }
            is Result.Success -> msg.data
                .asDetailState()
                .copy(
                    id = id,
                    isLoading = false,
                    errorMessage = null
                )
            is Result.Loading -> copy(
                isLoading = true,
                errorMessage = null
            )
        }
    }
}

class DetailsExecutorImpl(
    private val itemId: String,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CoroutineExecutor<UiIntent, Unit, ProductDetailState, Result<IProduct, IError>, Nothing>(), KoinComponent {

    private val interactor: StoreProductsInteractor = getKoin().get()

    override fun executeIntent(intent: UiIntent) {
        if (intent is UiIntent.ToggleChange) {
            /* todo: preserve liked state */
        }
    }

    override fun executeAction(action: Unit) {
        scope.launch(ioDispatcher) {
            val result = interactor.getProductById(itemId)
            withContext(mainDispatcher) { dispatch(result) }
        }
    }
}