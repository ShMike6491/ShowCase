package org.example.showcase.app.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module

val appModule = module {

    single { createHttpClient(engine = get()) }

    single<StoreFactory> { DefaultStoreFactory() }
}