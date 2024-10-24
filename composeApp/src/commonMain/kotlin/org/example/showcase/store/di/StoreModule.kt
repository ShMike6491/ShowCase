package org.example.showcase.store.di

import org.example.showcase.store.data.StoreHttpKtorClient
import org.example.showcase.store.data.StoreRepositoryImpl
import org.example.showcase.store.domain.StoreProductsInteractor
import org.example.showcase.store.domain.repository.IStoreRepository
import org.koin.dsl.module

val storeFeatureModule = module {
    single { StoreHttpKtorClient(get()) }

    single<IStoreRepository> { StoreRepositoryImpl(get()) }

    single { StoreProductsInteractor(get()) }
}