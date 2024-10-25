package org.example.showcase.store.ui.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.showcase.common.domain.model.IError
import org.example.showcase.common.domain.model.Result
import org.example.showcase.common.domain.model.onError
import org.example.showcase.common.domain.model.onSuccess
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.StoreProductsInteractor
import org.example.showcase.store.domain.model.IProduct
import org.example.showcase.store.ui.model.ProductDetailState
import org.example.showcase.store.ui.model.asDetailState

const val DETAILS_STORE_TAG = "DetailsStore"

interface DetailsStore : Store<UiIntent, ProductDetailState, Nothing>

object DetailsReducerImpl : Reducer<ProductDetailState, Result<IProduct, IError>> {

    override fun ProductDetailState.reduce(msg: Result<IProduct, IError>): ProductDetailState {
        return when (msg) {
            is Result.Error -> copy(
                isLoading = false,
                // todo: extract hardcoded
                errorMessage = "Something went wrong!".asUiString()
            )
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

class DetailsExecutorImpl : CoroutineExecutor<UiIntent, Result<IProduct, IError>, ProductDetailState, Result<IProduct, IError>, Nothing>() {

    override fun executeIntent(intent: UiIntent) { /* todo: handle navigation on user interactions */ }

    override fun executeAction(action: Result<IProduct, IError>) { dispatch(action) }
}

class DetailsBootstrapperImpl(
    private val interactor: StoreProductsInteractor
) : CoroutineBootstrapper<Result<IProduct, IError>>() {

    override fun invoke() {
        scope.launch(Dispatchers.IO) {
            // todo: change hardcoded id with the provided value
            val result = interactor.getProductById("1")
            withContext(Dispatchers.Main) { dispatch(result) }
        }
    }
}

