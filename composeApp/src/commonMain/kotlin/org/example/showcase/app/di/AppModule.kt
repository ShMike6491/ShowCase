package org.example.showcase.app.di

import org.koin.dsl.module

val appModule = module {

    single { createHttpClient(engine = get()) }
}