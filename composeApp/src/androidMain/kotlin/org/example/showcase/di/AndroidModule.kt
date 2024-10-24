package org.example.showcase.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val androidModule = module {
    single { OkHttp.create() }
}