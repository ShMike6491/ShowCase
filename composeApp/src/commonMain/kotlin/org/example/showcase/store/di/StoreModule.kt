package org.example.showcase.store.di

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.store.data.StoreHttpKtorClient
import org.example.showcase.store.data.StoreRepositoryImpl
import org.example.showcase.store.domain.StoreProductsInteractor
import org.example.showcase.store.domain.repository.IStoreRepository
import org.example.showcase.store.ui.details.DETAILS_STORE_TAG
import org.example.showcase.store.ui.details.DetailsExecutorImpl
import org.example.showcase.store.ui.details.DetailsReducerImpl
import org.example.showcase.store.ui.details.DetailsStore
import org.example.showcase.store.ui.feed.FEED_STORE_TAG
import org.example.showcase.store.ui.feed.FeedExecutorImpl
import org.example.showcase.store.ui.feed.FeedReducerImpl
import org.example.showcase.store.ui.feed.FeedStore
import org.example.showcase.store.ui.model.ProductDetailState
import org.example.showcase.store.ui.model.ProductFeedState
import org.koin.dsl.module

val storeFeatureModule = module {
    single { StoreHttpKtorClient(get()) }

    single<IStoreRepository> { StoreRepositoryImpl(get()) }

    single { StoreProductsInteractor(get()) }

    single<FeedStore> {
        val storeFactory: StoreFactory = get()
        object : FeedStore, Store<UiIntent, ProductFeedState, Nothing> by storeFactory.create(
            name = FEED_STORE_TAG,
            initialState = ProductFeedState(),
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = FeedReducerImpl,
            executorFactory = { FeedExecutorImpl() }
        ) {}
    }

    factory<DetailsStore> { params ->
        val itemId: String = params.get()
        val storeFactory: StoreFactory = get()
        object : DetailsStore, Store<UiIntent, ProductDetailState, Nothing> by storeFactory.create(
            name = DETAILS_STORE_TAG,
            initialState = ProductDetailState(),
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = DetailsReducerImpl,
            executorFactory = { DetailsExecutorImpl(itemId) }
        ) {}
    }
}