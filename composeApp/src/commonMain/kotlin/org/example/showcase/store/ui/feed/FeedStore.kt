package org.example.showcase.store.ui.feed

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
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.StoreProductsInteractor
import org.example.showcase.store.domain.model.IProduct
import org.example.showcase.store.ui.model.ProductFeedState
import org.example.showcase.store.ui.model.asProductItemState

const val FEED_STORE_TAG = "FeedStore"

interface FeedStore : Store<UiIntent, ProductFeedState, Nothing>

object FeedReducerImpl : Reducer<ProductFeedState, Result<List<IProduct>, IError>> {

    override fun ProductFeedState.reduce(msg: Result<List<IProduct>, IError>): ProductFeedState {
        return when (msg) {
            is Result.Error -> copy(
                isLoading = false,
                // todo: extract hardcoded
                errorMessage = "Something went wrong!".asUiString()
            )
            is Result.Success -> copy(
                isLoading = false,
                errorMessage = null,
                items = msg.data.map {
                    it.asProductItemState()
                }
            )
            is Result.Loading -> copy(
                isLoading = true,
                errorMessage = null
            )
        }
    }
}

class FeedExecutorImpl : CoroutineExecutor<UiIntent, Result<List<IProduct>, IError>, ProductFeedState, Result<List<IProduct>, IError>, Nothing>() {

    override fun executeIntent(intent: UiIntent) { /* todo: handle navigation on user interactions */ }

    override fun executeAction(action: Result<List<IProduct>, IError>) { dispatch(action) }
}

class FeedBootstrapperImpl(
    private val interactor: StoreProductsInteractor
) : CoroutineBootstrapper<Result<List<IProduct>, IError>>() {

    override fun invoke() {
        scope.launch(Dispatchers.IO) {
            val result = interactor.getAllProducts()
            withContext(Dispatchers.Main) { dispatch(result) }
        }
    }
}